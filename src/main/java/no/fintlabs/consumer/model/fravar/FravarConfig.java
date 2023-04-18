package no.fintlabs.consumer.model.fravar;

import no.fint.model.resource.utdanning.vurdering.FravarResource;
import no.fintlabs.core.consumer.shared.ConsumerProps;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import org.springframework.stereotype.Component;

@Component
public class FravarConfig extends ConsumerConfig<FravarResource> {

    public FravarConfig(ConsumerProps consumerProps) {
        super(consumerProps);
    }

    @Override
    protected String resourceName() {
        return "fravar";
    }
}
