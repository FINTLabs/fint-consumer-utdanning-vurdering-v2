package no.fintlabs.consumer.fravarsoversikt;

import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.utdanning.vurdering.FravarsoversiktResource;
import no.fintlabs.consumer.KafkaConsumer;
import no.fintlabs.kafka.common.ListenerBeanRegistrationService;
import no.fintlabs.kafka.entity.EntityConsumerFactoryService;
import no.fintlabs.kafka.entity.topic.EntityTopicService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FravarsoversiktKafkaConsumer extends KafkaConsumer<FravarsoversiktResource> {

    private final EntityConsumerFactoryService entityConsumerFactoryService;
    private final ListenerBeanRegistrationService listenerBeanRegistrationService;
    private final EntityTopicService entityTopicService;

    public FravarsoversiktKafkaConsumer(EntityConsumerFactoryService entityConsumerFactoryService, ListenerBeanRegistrationService listenerBeanRegistrationService, EntityTopicService entityTopicService) {
        super(entityConsumerFactoryService, listenerBeanRegistrationService, entityTopicService, "utdanning-vurdering-fravarsoversikt");
        this.entityConsumerFactoryService = entityConsumerFactoryService;
        this.listenerBeanRegistrationService = listenerBeanRegistrationService;
        this.entityTopicService = entityTopicService;
    }
}
