package no.fintlabs.consumer.fravarsregistrering;

import no.fint.model.resource.utdanning.vurdering.FravarsregistreringResource;
import no.fintlabs.core.consumer.shared.config.ConsumerProps;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import org.springframework.stereotype.Component;

@Component
public class FravarsregistreringConfig extends ConsumerConfig<FravarsregistreringResource> {

    public FravarsregistreringConfig(ConsumerProps consumerProps) {
        super(consumerProps);
    }

    @Override
    protected String resourceName() {
        return "fravarsregistrering";
    }
}
