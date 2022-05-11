package no.fintlabs.fravarsoversikt;

//
//import com.google.common.collect.ImmutableMap;
//import io.swagger.annotations.Api;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//
//import no.fint.audit.FintAuditService;
//
//import no.fint.cache.exceptions.*;
//import no.fint.consumer.config.Constants;
//import no.fint.consumer.config.ConsumerProps;
//import no.fint.consumer.event.ConsumerEventUtil;
//import no.fint.consumer.event.SynchronousEvents;
//import no.fint.consumer.exceptions.*;
//import no.fint.consumer.status.StatusCache;
//import no.fint.consumer.utils.EventResponses;
//import no.fint.consumer.utils.RestEndpoints;
//
//import no.fint.event.model.*;
//
//import no.fint.relations.FintRelationsMediaType;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.HeaderConstants;
import no.fint.model.resource.utdanning.vurdering.FravarsoversiktResource;
import no.fint.model.resource.utdanning.vurdering.FravarsoversiktResources;
import no.fintlabs.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
//@Api(tags = {"Fravar"})
//@CrossOrigin
@RestController
//@RequestMapping(name = "Fravar", value = RestEndpoints.FRAVAR, produces = {FintRelationsMediaType.APPLICATION_HAL_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
@RequestMapping("Fravarsoversikt")
public class FravarsoversiktController {

    private final FravarsoversiktService fravarsoversiktService;

    //    @Autowired
//    private FintAuditService fintAuditService;
//
//    @Autowired
    private final FravarsoversiktLinker linker;
//
//    @Autowired
//    private ConsumerProps props;
//
//    @Autowired
//    private StatusCache statusCache;
//
//    @Autowired
//    private ConsumerEventUtil consumerEventUtil;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private SynchronousEvents synchronousEvents;
//

    public FravarsoversiktController(FravarsoversiktService cacheRepository, FravarsoversiktLinker linker) {
        this.fravarsoversiktService = cacheRepository;
        this.linker = linker;
    }

    @GetMapping("/last-updated")
    public Map<String, String> getLastUpdated(@RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId) {
//        if (cacheService == null) {
//            throw new CacheDisabledException("Fravar cache is disabled.");
//        }
//        if (props.isOverrideOrgId() || orgId == null) {
//            orgId = props.getDefaultOrgId();
//        }
        String lastUpdated = Long.toString(fravarsoversiktService.getLastUpdated());
        return Map.of("lastUpdated", lastUpdated);
    }

    @GetMapping("/cache/size")
    public Map<String, Integer> getCacheSize(@RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId) {
//        if (cacheService == null) {
//            throw new CacheDisabledException("Fravar cache is disabled.");
//        }
//        if (props.isOverrideOrgId() || orgId == null) {
//            orgId = props.getDefaultOrgId();
//        }
        return Map.of("size", fravarsoversiktService.getCacheSize());
    }

    @GetMapping
    public FravarsoversiktResources getFravar(
            @RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId,
            @RequestHeader(name = HeaderConstants.CLIENT, required = false) String client,
            @RequestParam(defaultValue = "0") long sinceTimeStamp,
            @RequestParam(defaultValue = "0") int size,
            @RequestParam(defaultValue = "0") int offset) {
//        if (cacheService == null) {
//            throw new CacheDisabledException("Fravar cache is disabled.");
//        }
//        if (props.isOverrideOrgId() || orgId == null) {
//            orgId = props.getDefaultOrgId();
//        }
//        if (client == null) {
//            client = props.getDefaultClient();
//        }
        log.debug("OrgId: {}, Client: {}", orgId, client);

//        Event event = new Event(orgId, Constants.COMPONENT, VurderingActions.GET_ALL_FRAVAR, client);
//        event.setOperation(Operation.READ);
//        if (StringUtils.isNotBlank(request.getQueryString())) {
//            event.setQuery("?" + request.getQueryString());
//        }
//        fintAuditService.audit(event);
//        fintAuditService.audit(event, Status.CACHE);


        Stream<FravarsoversiktResource> resources;
        if (size > 0 && offset >= 0 && sinceTimeStamp > 0) {
            resources = fravarsoversiktService.streamSliceSince(sinceTimeStamp, offset, size);
        } else if (size > 0 && offset >= 0) {
            resources = fravarsoversiktService.streamSlice(offset, size);
        } else if (sinceTimeStamp > 0) {
            resources = fravarsoversiktService.streamSince(sinceTimeStamp);
        } else {
            resources = fravarsoversiktService.streamAll();
        }

        //fintAuditService.audit(event, Status.CACHE_RESPONSE, Status.SENT_TO_CLIENT);

        return linker.toResources(resources, offset, size, fravarsoversiktService.getCacheSize());
    }


    @GetMapping("/systemid/{id:.+}")
    public FravarsoversiktResource getFravarBySystemId(
            @PathVariable String id,
            @RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId,
            @RequestHeader(name = HeaderConstants.CLIENT, required = false) String client) {
//        if (props.isOverrideOrgId() || orgId == null) {
//            orgId = props.getDefaultOrgId();
//        }
//        if (client == null) {
//            client = props.getDefaultClient();
//        }
        log.debug("systemId: {}, OrgId: {}, Client: {}", id, orgId, client);

//        Event event = new Event(orgId, Constants.COMPONENT, VurderingActions.GET_FRAVAR, client);
//        event.setOperation(Operation.READ);
//        event.setQuery("systemId/" + id);
//
//        if (cacheService != null) {
//            fintAuditService.audit(event);
//            fintAuditService.audit(event, Status.CACHE);
//
        Optional<FravarsoversiktResource> fravar = fravarsoversiktService.getFravarsoversiktBySystemId(id);
//
//            fintAuditService.audit(event, Status.CACHE_RESPONSE, Status.SENT_TO_CLIENT);
//
        return fravar.map(linker::toResource).orElseThrow(() -> new EntityNotFoundException(id));
//
//        } else {
//            BlockingQueue<Event> queue = synchronousEvents.register(event);
//            consumerEventUtil.send(event);
//
//            Event response = EventResponses.handle(queue.poll(5, TimeUnit.MINUTES));
//
//            if (response.getData() == null ||
//                    response.getData().isEmpty()) throw new EntityNotFoundException(id);
//
//            FravarResource fravar = objectMapper.convertValue(response.getData().get(0), FravarResource.class);
//
//            fintAuditService.audit(response, Status.SENT_TO_CLIENT);
//
//            return linker.toResource(fravar);
//        }
//        return null;
    }


//    //
//    // Exception handlers
//    //
//    @ExceptionHandler(EventResponseException.class)
//    public ResponseEntity handleEventResponseException(EventResponseException e) {
//        return ResponseEntity.status(e.getStatus()).body(e.getResponse());
//    }
//
//    @ExceptionHandler(UpdateEntityMismatchException.class)
//    public ResponseEntity handleUpdateEntityMismatch(Exception e) {
//        return ResponseEntity.badRequest().body(ErrorResponse.of(e));
//    }
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity handleEntityNotFound(Exception e) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(e));
//    }
//
//    @ExceptionHandler(CreateEntityMismatchException.class)
//    public ResponseEntity handleCreateEntityMismatch(Exception e) {
//        return ResponseEntity.badRequest().body(ErrorResponse.of(e));
//    }
//
//    @ExceptionHandler(EntityFoundException.class)
//    public ResponseEntity handleEntityFound(Exception e) {
//        return ResponseEntity.status(HttpStatus.FOUND).body(ErrorResponse.of(e));
//    }
//
//    @ExceptionHandler(CacheDisabledException.class)
//    public ResponseEntity handleBadRequest(Exception e) {
//        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ErrorResponse.of(e));
//    }
//
//    @ExceptionHandler(UnknownHostException.class)
//    public ResponseEntity handleUnkownHost(Exception e) {
//        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ErrorResponse.of(e));
//    }
//
//    @ExceptionHandler(CacheNotFoundException.class)
//    public ResponseEntity handleCacheNotFound(Exception e) {
//        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ErrorResponse.of(e));
//    }

}

