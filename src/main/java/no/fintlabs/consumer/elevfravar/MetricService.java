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

    public void register(String domain, String fintPackage, String resource, Cache<?> cache) {
        String metricName = "fint-core-" + domain + "-" + fintPackage + "-" + resource;
        Gauge.builder(metricName, cache, Cache::size).register(meterRegistry);
    }
}