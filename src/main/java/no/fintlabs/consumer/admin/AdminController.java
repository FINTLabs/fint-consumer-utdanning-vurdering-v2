package no.fintlabs.consumer.admin;


import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.HeaderConstants;
import no.fint.event.model.health.Health;
import no.fintlabs.cache.CacheManager;
import no.fintlabs.consumer.config.ConsumerProps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "admin/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AdminController {
//
//    @Autowired
//    private ConsumerEventUtil consumerEventUtil;
//
//    @Autowired
//    private CacheManager<?> cacheManager;
//
//    @Autowired(required = false)
//    private Collection<CacheService<?>> cacheServices;

    private ConsumerProps consumerProps;

    private CacheManager cacheManager;

    public AdminController(ConsumerProps consumerProps, CacheManager cacheManager) {
        this.consumerProps = consumerProps;
        this.cacheManager = cacheManager;
    }

    @GetMapping("/health")
    public ResponseEntity<Event<Health>> healthCheck(@RequestHeader(HeaderConstants.ORG_ID) String orgId,
                                                     @RequestHeader(HeaderConstants.CLIENT) String client) {

        // TODO: 04/05/2022 Implement when status service is working
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
        // TODO: 04/05/2022 Need changes in core-cache
        //return cacheManager.getKeys();
        throw new UnsupportedOperationException();
    }

    @Deprecated()
    @GetMapping("/organisations/{orgId:.+}")
    public Collection<String> getOrganization(@PathVariable String orgId) {
        // TODO: 04/05/2022 Need changes in core-cache
        throw new UnsupportedOperationException();
        //return cacheManager.getKeys().stream().filter(key -> CacheUri.containsOrgId(key, orgId)).collect(Collectors.toList());
    }

    @GetMapping("/assets")
    public Collection<String> getAssets() {
        return Set.of(consumerProps.getOrgId(), "tullOgBall.no");
    }

    @GetMapping("/caches")
    public Map<String, Integer> getCaches() {
        // TODO: 04/05/2022 Need changes in core-cache
        throw new UnsupportedOperationException();
//        return cacheManager
//                .getKeys()
//                .stream()
//                .collect(Collectors
//                        .toMap(Function.identity(),
//                                k -> cacheManager.getCache(k).map(Cache::size).orElse(0)));
    }

    @GetMapping("/cache/status")
    //public Map<String, Map<String, CacheEntry>> getCacheStatus() {
    public Map<String, Map<String, String>> getCacheStatus() {
        // TODO: 04/05/2022 Need changes in core-cache
        throw new UnsupportedOperationException();
//        return cacheManager
//                .getKeys()
//                .stream()
//                .map(s -> StringUtils.split(s, ':'))
//                .collect(
//                        Collectors.groupingBy(s -> s[2],
//                                Collectors.toMap(s -> s[3],
//                                        s -> cacheManager.getCache(String.join(":", s))
//                                                .map(c -> new CacheEntry(new Date(c.getLastUpdated()), c.size()))
//                                                .orElse(new CacheEntry(null, null)))));
    }

    @PostMapping({"/cache/rebuild", "/cache/rebuild/{model}"})
    public void rebuildCache(
            @RequestHeader(name = HeaderConstants.ORG_ID) String orgid,
            @RequestHeader(name = HeaderConstants.CLIENT) String client,
            @PathVariable(required = false) String model
    ) {
        // TODO: 04/05/2022 Need changes in core-cache
        throw new UnsupportedOperationException();
//        log.info("Cache rebuild on {} requested by {}", orgid, client);
//        cacheServices.stream()
//                .filter(cacheService -> StringUtils.isBlank(model) || StringUtils.equalsIgnoreCase(cacheService.getModel(), model))
//                .forEach(cacheService -> cacheService.populateCache(orgid));
    }

}