package no.fintlabs.fravar;

import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.utdanning.vurdering.FravarResource;
import no.fintlabs.kafka.KafkaConfiguration;
import no.fintlabs.kafka.TopicService;
import no.fintlabs.kafka.common.FintListenerBeanRegistrationService;
import no.fintlabs.kafka.entity.EntityTopicNameParameters;
import no.fintlabs.kafka.entity.EntityTopicService;
import no.fintlabs.kafka.entity.FintKafkaEntityConsumerFactory;
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

    private final FintKafkaEntityConsumerFactory entityConsumer;
    private final FintListenerBeanRegistrationService fintListenerBeanRegistrationService;
    private final EntityTopicService entityTopicService;
    private final TopicService topicService;

    public FravarKafkaConsumer(FintKafkaEntityConsumerFactory entityConsumer, FintListenerBeanRegistrationService fintListenerBeanRegistrationService, EntityTopicService entityTopicService, TopicService topicService) {
        this.entityConsumer = entityConsumer;
        this.fintListenerBeanRegistrationService = fintListenerBeanRegistrationService;
        this.entityTopicService = entityTopicService;
        this.topicService = topicService;
    }

    public void registerListener(Consumer<ConsumerRecord<String, FravarResource>> consumer) {
        EntityTopicNameParameters topicNameParameters = EntityTopicNameParameters
                .builder()
                .orgId("fintlabs.no")
                .domainContext("fint-core")
                .resource("utdanning-vurdering-fravar")
                .build();

        long retention = getRetention(topicNameParameters);
        // TODO: 11/03/2022 What to do if fails to get retention 
        if (retention == 0) return;
        // TODO: 11/03/2022 Use retiontion in cache register.

        ConcurrentMessageListenerContainer<String, FravarResource> messageListenerContainer =
                entityConsumer.createConsumer(topicNameParameters, FravarResource.class, consumer, new CommonLoggingErrorHandler());

        fintListenerBeanRegistrationService.registerBean(messageListenerContainer);
    }

    private long getRetention(EntityTopicNameParameters topicNameParameters) {
        try {
            Map<String, String> config = topicService.getTopicConfig(topicNameParameters);
            String output = config.get(TopicConfig.RETENTION_MS_CONFIG);
            return Long.parseLong(output);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
