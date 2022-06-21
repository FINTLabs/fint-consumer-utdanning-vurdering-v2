package no.fintlabs.consumer.fravar;

import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.utdanning.vurdering.FravarResource;
import no.fintlabs.consumer.KafkaConsumer;
import no.fintlabs.kafka.common.ListenerBeanRegistrationService;
import no.fintlabs.kafka.entity.EntityConsumerFactoryService;
import no.fintlabs.kafka.entity.topic.EntityTopicService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FravarKafkaConsumer extends KafkaConsumer<FravarResource> {

    // TODO: 21/06/2022 Bean instead of class??
    public FravarKafkaConsumer(
            EntityConsumerFactoryService entityConsumerFactoryService,
            ListenerBeanRegistrationService listenerBeanRegistrationService,
            EntityTopicService entityTopicService) {
        super(entityConsumerFactoryService, listenerBeanRegistrationService, entityTopicService, "utdanning-vurdering-fravar");
    }
}
