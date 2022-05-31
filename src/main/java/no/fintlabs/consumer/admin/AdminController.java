package no.fintlabs.consumer.admin;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.HeaderConstants;
import no.fint.event.model.health.Health;
import no.fintlabs.cache.CacheManager;
import no.fintlabs.consumer.ConsumerService;
import no.fintlabs.consumer.config.ConsumerProps;
import no.fintlabs.consumer.config.RestEndpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = RestEndpoints.ADMIN, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AdminController {
    //
//    @Autowired
//    private ConsumerEventUtil consumerEventUtil;
//

    private MeterRegistry meterRegistry;

    @Autowired(required = false)
    private Collection<ConsumerService<?>> consumerServices;

    private final ConsumerProps consumerProps;

    private final CacheManager cacheManager;

    public AdminController(ConsumerProps consumerProps, CacheManager cacheManager) {
        this.consumerProps = consumerProps;
        this.cacheManager = cacheManager;
    }

    @GetMapping("/health")
    public ResponseEntity<Event<Health>> healthCheck(@RequestHeader(HeaderConstants.ORG_ID) String orgId,
                                                     @RequestHeader(HeaderConstants.CLIENT) String client) {

        // TODO: 04/05/2022 Implement when status service is working (Should 303 to status serivce)
        throw new UnsupportedOperationException();

//        log.debug("Health check on {} requested by {} ...", orgId, client);
//        Event<Health> event = new Event<>(orgId, Constants.COMPONENT, DefaultActions.HEALTH, client);
//        event.addData(new Health(Constants.COMPONENT_CONSUMER, HealthStatus.SENT_FROM_CONSUMER_TO_PROVIDER));
//
//        final Optional<Event<Health>> response = consumerEventUtil.healthCheck(event);
//
//        return response.map(health -> {
//            log.debug("Health check response: {}", health.getData());
//            health.addData(new Health(Constants.COMPONENT_CONSUMER, HealthStatus.RECEIVED_IN_CONSUMER_FROM_PROVIDER));
//            return ResponseEntity.ok(health);
//        }).orElseGet(() -> {
//            log.debug("No response to health event.");
//            event.setMessage("No response from adapter");
//            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(event);
//        });
    }

    @GetMapping("/organisations")
    public Collection<String> getOrganisations() {
        return consumerServices
                .stream()
                .map(consumerService -> consumerService.getCacheUrn())
                .collect(Collectors.toList());
    }

    @Deprecated()
    @GetMapping("/organisations/{orgId:.+}")
    public Collection<String> getOrganization(@PathVariable String orgId) {

        return !orgId.equals(consumerProps.getOrgId()) ? Collections.EMPTY_LIST : getOrganisations();
    }

    @GetMapping(value = "/assets", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<String> getAssets() {
        return Set.of(consumerProps.getOrgId());
    }

    @GetMapping("/caches")
    public Map<String, Integer> getCaches() {
        return consumerServices.stream()
                .collect(Collectors.toMap(
                        ConsumerService::getCacheUrn,
                        ConsumerService::getCacheSize)
                );
    }

    @GetMapping("/cache/status")
    public Map<String, Map<String, CacheEntry>> getCacheStatus() {

        if (consumerProps.getOrgId() == null || consumerProps.getOrgId().equals(""))
            throw new IllegalArgumentException("Config for OrgId can not be empty.");

        return consumerServices
                .stream()
                .collect(
                        Collectors.groupingBy(s -> consumerProps.getOrgId(),
                                Collectors.toMap(
                                        s -> s.getModelName(),
                                        s -> new CacheEntry(new Date(s.getLastUpdated()), s.getCacheSize())
                                )
                        )
                );
    }

    @Counted(value = "no_fintlabs_fint_core_consumer_utdanning_vurdering_admin_cache_rebuild")
    @PostMapping({"/cache/rebuild", "/cache/rebuild/{model}"})
    public void rebuildCache(
            @RequestHeader(name = HeaderConstants.ORG_ID) String orgid,
            @RequestHeader(name = HeaderConstants.CLIENT) String client,
            @PathVariable(required = false) String model
    ) {
        meterRegistry.counter("asdads").


        // TODO: 04/05/2022 Need changes in core-cache
        throw new UnsupportedOperationException();
//        log.info("Cache rebuild on {} requested by {}", orgid, client);
//        cacheServices.stream()
//                .filter(cacheService -> StringUtils.isBlank(model) || StringUtils.equalsIgnoreCase(cacheService.getModel(), model))
//                .forEach(cacheService -> cacheService.populateCache(orgid));
    }

}