package no.fintlabs.consumer.fravar;

import no.fint.model.resource.utdanning.vurdering.FravarResource;
import no.fint.model.resource.utdanning.vurdering.FravarsoversiktResource;
import no.fintlabs.core.consumer.shared.ConsumerProps;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import org.springframework.stereotype.Component;

@Component
public class FravarConfig extends ConsumerConfig<FravarResource> {

    public FravarConfig(ConsumerProps consumerProps) {
        super(consumerProps);
    }

    @Override
    protected String domainName() {
        return "utdanning";
    }

    @Override
    protected String packageName() {
        return "vurdering";
    }

    @Override
    protected String resourceName() {
        return "fravar";
    }
}
