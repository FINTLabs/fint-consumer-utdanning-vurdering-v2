package no.fintlabs.consumer.fravar;

import no.fint.model.resource.utdanning.vurdering.FravarResource;
import no.fintlabs.core.consumer.shared.resource.kafka.EntityKafkaConsumer;
import no.fintlabs.kafka.common.ListenerBeanRegistrationService;
import no.fintlabs.kafka.entity.EntityConsumerFactoryService;
import no.fintlabs.kafka.entity.topic.EntityTopicService;
import org.springframework.stereotype.Service;

@Service
public class FravarKafkaConsumer extends EntityKafkaConsumer<FravarResource> {

    public FravarKafkaConsumer(
            EntityConsumerFactoryService entityConsumerFactoryService,
            ListenerBeanRegistrationService listenerBeanRegistrationService,
            EntityTopicService entityTopicService,
            FravarConfig fravarConfig) {
        super(entityConsumerFactoryService, listenerBeanRegistrationService, entityTopicService, fravarConfig);
    }
}
