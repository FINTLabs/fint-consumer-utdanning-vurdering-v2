package no.fintlabs.consumer.model.fravarsoversikt;

import no.fint.model.resource.utdanning.vurdering.FravarsoversiktResource;
import no.fintlabs.core.consumer.shared.ConsumerProps;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import org.springframework.stereotype.Component;

@Component
public class FravarsoversiktConfig extends ConsumerConfig<FravarsoversiktResource> {

    public FravarsoversiktConfig(ConsumerProps consumerProps) {
        super(consumerProps);
    }

    @Override
    protected String resourceName() {
        return "fravarsoversikt";
    }
}
