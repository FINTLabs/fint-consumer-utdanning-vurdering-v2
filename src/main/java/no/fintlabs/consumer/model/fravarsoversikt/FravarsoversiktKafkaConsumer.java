package no.fintlabs.consumer.model.fravarsoversikt;

import no.fint.model.resource.utdanning.vurdering.FravarsoversiktResource;
import no.fintlabs.core.consumer.shared.resource.kafka.EntityKafkaConsumer;
import no.fintlabs.kafka.common.ListenerBeanRegistrationService;
import no.fintlabs.kafka.entity.EntityConsumerFactoryService;
import no.fintlabs.kafka.entity.topic.EntityTopicService;
import org.springframework.stereotype.Service;

@Service
public class FravarsoversiktKafkaConsumer extends EntityKafkaConsumer<FravarsoversiktResource> {

    private final EntityConsumerFactoryService entityConsumerFactoryService;
    private final ListenerBeanRegistrationService listenerBeanRegistrationService;
    private final EntityTopicService entityTopicService;

    public FravarsoversiktKafkaConsumer(
            EntityConsumerFactoryService entityConsumerFactoryService,
            ListenerBeanRegistrationService listenerBeanRegistrationService,
            EntityTopicService entityTopicService,
            FravarsoversiktConfig fravarsoversiktConfig) {
        super(entityConsumerFactoryService, listenerBeanRegistrationService, entityTopicService, fravarsoversiktConfig);
        this.entityConsumerFactoryService = entityConsumerFactoryService;
        this.listenerBeanRegistrationService = listenerBeanRegistrationService;
        this.entityTopicService = entityTopicService;
    }
}
