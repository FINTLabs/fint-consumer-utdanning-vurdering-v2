package no.fintlabs.fravar;

import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.utdanning.vurdering.FravarResource;
import no.fintlabs.kafka.common.FintListenerBeanRegistrationService;
import no.fintlabs.kafka.entity.EntityTopicNameParameters;
import no.fintlabs.kafka.entity.EntityTopicService;
import no.fintlabs.kafka.entity.FintKafkaEntityConsumerFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
public class FravarKafkaConsumer {

    private final FintKafkaEntityConsumerFactory entityConsumer;
    private final FintListenerBeanRegistrationService fintListenerBeanRegistrationService;
    private final EntityTopicService entityTopicService;

    public FravarKafkaConsumer(FintKafkaEntityConsumerFactory entityConsumer, FintListenerBeanRegistrationService fintListenerBeanRegistrationService, EntityTopicService entityTopicService) {
        this.entityConsumer = entityConsumer;
        this.fintListenerBeanRegistrationService = fintListenerBeanRegistrationService;
        this.entityTopicService = entityTopicService;
    }

    public void registerListener(Consumer<ConsumerRecord<String, FravarResource>> consumer) {
        EntityTopicNameParameters topicNameParameters = EntityTopicNameParameters
                .builder()
                .orgId("fintlabs.no")
                .domainContext("fint-core")
                .resource("utdanning-vurdering-fravar")
                .build();

        ConcurrentMessageListenerContainer<String, FravarResource> messageListenerContainer =
                entityConsumer.createConsumer(topicNameParameters, FravarResource.class, consumer, new CommonLoggingErrorHandler());

        fintListenerBeanRegistrationService.registerBean(messageListenerContainer);
    }
}
