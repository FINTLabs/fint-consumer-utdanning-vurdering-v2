package no.fintlabs.consumer.elevfravar;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import no.fintlabs.cache.Cache;
import org.springframework.stereotype.Service;

@Service
public class MetricService {

    private final MeterRegistry meterRegistry;

    public MetricService(SimpleMeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void register(String domain, String fintPackage, String resource, String orgId, Cache<?> cache) {
        String metricName = "fint-core-cache-size";
        Gauge
                .builder(metricName, cache, Cache::size)
                .tag("domain", domain)
                .tag("package", fintPackage)
                .tag("resource", resource)
                .tag("orgId", orgId)
                .register(meterRegistry);
    }
}