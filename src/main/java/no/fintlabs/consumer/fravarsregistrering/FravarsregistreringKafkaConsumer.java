package no.fintlabs.consumer.fravarsregistrering;

import no.fint.model.resource.utdanning.vurdering.FravarsregistreringResource;
import no.fintlabs.core.consumer.shared.resource.kafka.EntityKafkaConsumer;
import no.fintlabs.kafka.common.ListenerBeanRegistrationService;
import no.fintlabs.kafka.entity.EntityConsumerFactoryService;
import no.fintlabs.kafka.entity.topic.EntityTopicService;
import org.springframework.stereotype.Service;

@Service
public class FravarsregistreringKafkaConsumer extends EntityKafkaConsumer<FravarsregistreringResource> {

    public FravarsregistreringKafkaConsumer(
            EntityConsumerFactoryService entityConsumerFactoryService,
            ListenerBeanRegistrationService listenerBeanRegistrationService,
            EntityTopicService entityTopicService,
            FravarsregistreringConfig fravarsregistreringConfig) {
        super(entityConsumerFactoryService, listenerBeanRegistrationService, entityTopicService, fravarsregistreringConfig);
    }
}
