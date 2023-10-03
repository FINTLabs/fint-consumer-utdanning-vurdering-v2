package no.fintlabs.consumer.elevfravar;

import no.fint.model.resource.utdanning.vurdering.ElevfravarResource;
import no.fintlabs.core.consumer.shared.ConsumerProps;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import org.springframework.stereotype.Component;

@Component
public class ElevfravarConfig extends ConsumerConfig<ElevfravarResource> {

    public ElevfravarConfig(ConsumerProps consumerProps) {
        super(consumerProps);
    }

    @Override
    protected String resourceName() {
        return "elevfravar";
    }
}
