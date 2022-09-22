package no.fintlabs.consumer.elevfravar;

import no.fint.model.resource.utdanning.vurdering.ElevfravarResource;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import no.fintlabs.core.consumer.shared.resource.kafka.EntityKafkaConsumer;
import no.fintlabs.kafka.common.ListenerBeanRegistrationService;
import no.fintlabs.kafka.entity.EntityConsumerFactoryService;
import no.fintlabs.kafka.entity.topic.EntityTopicService;

public class ElevfravarKafkaConsumer extends EntityKafkaConsumer<ElevfravarResource> {
    public ElevfravarKafkaConsumer(
            EntityConsumerFactoryService entityConsumerFactoryService,
            ListenerBeanRegistrationService listenerBeanRegistrationService,
            EntityTopicService entityTopicService,
            ConsumerConfig consumerConfig) {
        super(entityConsumerFactoryService,
                listenerBeanRegistrationService,
                entityTopicService,
                consumerConfig);
    }
}
