package no.fintlabs.consumer.fravarsoversikt;

import no.fint.model.resource.utdanning.vurdering.FravarsoversiktResource;
import no.fintlabs.core.consumer.shared.KafkaConsumer;
import no.fintlabs.kafka.common.ListenerBeanRegistrationService;
import no.fintlabs.kafka.entity.EntityConsumerFactoryService;
import no.fintlabs.kafka.entity.topic.EntityTopicService;
import org.springframework.stereotype.Service;

@Service
public class FravarsoversiktKafkaConsumer extends KafkaConsumer<FravarsoversiktResource> {

    private final EntityConsumerFactoryService entityConsumerFactoryService;
    private final ListenerBeanRegistrationService listenerBeanRegistrationService;
    private final EntityTopicService entityTopicService;

    public FravarsoversiktKafkaConsumer(EntityConsumerFactoryService entityConsumerFactoryService, ListenerBeanRegistrationService listenerBeanRegistrationService, EntityTopicService entityTopicService) {
        super("utdanning-vurdering-fravarsoversikt", entityConsumerFactoryService, listenerBeanRegistrationService, entityTopicService);
        this.entityConsumerFactoryService = entityConsumerFactoryService;
        this.listenerBeanRegistrationService = listenerBeanRegistrationService;
        this.entityTopicService = entityTopicService;
    }
}
