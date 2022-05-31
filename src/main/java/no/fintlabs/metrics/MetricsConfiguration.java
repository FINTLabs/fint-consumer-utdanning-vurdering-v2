package no.fintlabs.metrics;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfiguration {

    @Bean
    CountedAspect countedAspect() {
        return new CountedAspect();
    }

    @Bean
    TimedAspect timedAspect() {
        return new TimedAspect();
    }

}
