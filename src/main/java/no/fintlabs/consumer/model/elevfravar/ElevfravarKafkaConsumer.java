package no.fintlabs.consumer.model.elevfravar;

import no.fint.model.resource.utdanning.vurdering.ElevfravarResource;
import no.fintlabs.core.consumer.shared.resource.kafka.EntityKafkaConsumer;
import no.fintlabs.kafka.common.ListenerBeanRegistrationService;
import no.fintlabs.kafka.entity.EntityConsumerFactoryService;
import no.fintlabs.kafka.entity.topic.EntityTopicService;
import org.springframework.stereotype.Service;

@Service
public class ElevfravarKafkaConsumer extends EntityKafkaConsumer<ElevfravarResource> {
    public ElevfravarKafkaConsumer(
            EntityConsumerFactoryService entityConsumerFactoryService,
            ListenerBeanRegistrationService listenerBeanRegistrationService,
            EntityTopicService entityTopicService,
            ElevfravarConfig elevfravarConfig) {
        super(entityConsumerFactoryService,
                listenerBeanRegistrationService,
                entityTopicService,
                elevfravarConfig);
    }
}
