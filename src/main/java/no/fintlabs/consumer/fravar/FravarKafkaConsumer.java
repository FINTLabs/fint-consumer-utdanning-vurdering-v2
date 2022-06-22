package no.fintlabs.consumer.fravar;

import no.fint.model.resource.utdanning.vurdering.FravarResource;
import no.fintlabs.consumer.KafkaConsumer;
import no.fintlabs.kafka.common.ListenerBeanRegistrationService;
import no.fintlabs.kafka.entity.EntityConsumerFactoryService;
import no.fintlabs.kafka.entity.topic.EntityTopicService;
import org.springframework.stereotype.Service;

@Service
public class FravarKafkaConsumer extends KafkaConsumer<FravarResource> {

    public FravarKafkaConsumer(
            EntityConsumerFactoryService entityConsumerFactoryService,
            ListenerBeanRegistrationService listenerBeanRegistrationService,
            EntityTopicService entityTopicService) {
        super("utdanning-vurdering-fravar", entityConsumerFactoryService, listenerBeanRegistrationService, entityTopicService);
    }
}
