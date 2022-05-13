package no.fintlabs.consumer.fravar;

import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.utdanning.vurdering.FravarResource;
import no.fintlabs.kafka.common.ListenerBeanRegistrationService;
import no.fintlabs.kafka.common.topic.TopicService;
import no.fintlabs.kafka.entity.EntityConsumerFactoryService;
import no.fintlabs.kafka.entity.topic.EntityTopicNameParameters;
import no.fintlabs.kafka.entity.topic.EntityTopicService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

@Slf4j
@Service
public class FravarKafkaConsumer {

    private final EntityConsumerFactoryService entityConsumerFactoryService;
    private final ListenerBeanRegistrationService listenerBeanRegistrationService;
    private final EntityTopicService entityTopicService;

    public FravarKafkaConsumer(EntityConsumerFactoryService entityConsumerFactoryService, ListenerBeanRegistrationService listenerBeanRegistrationService, EntityTopicService entityTopicService) {
        this.entityConsumerFactoryService = entityConsumerFactoryService;
        this.listenerBeanRegistrationService = listenerBeanRegistrationService;
        this.entityTopicService = entityTopicService;
    }

    public long registerListener(Consumer<ConsumerRecord<String, FravarResource>> consumer) {
        EntityTopicNameParameters topicNameParameters = EntityTopicNameParameters
                .builder()
                .resource("utdanning-vurdering-fravar")
                .build();

        long retention = getRetention(topicNameParameters);
        // TODO: 11/03/2022 What to do if fails to get retention 

        ConcurrentMessageListenerContainer<String, FravarResource> messageListenerContainer =
                entityConsumerFactoryService
                        .createFactory(FravarResource.class, consumer, new CommonLoggingErrorHandler())
                        .createContainer(topicNameParameters);

        listenerBeanRegistrationService.registerBean(messageListenerContainer);

        return retention;
    }

    private long getRetention(EntityTopicNameParameters topicNameParameters) {
        try {
            Map<String, String> config = entityTopicService.getTopicConfig(topicNameParameters);
            String output = config.get(TopicConfig.RETENTION_MS_CONFIG);
            return Long.parseLong(output);

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
