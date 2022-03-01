package no.fintlabs.fravar;

import lombok.extern.slf4j.Slf4j;

import no.fint.model.resource.utdanning.vurdering.FravarResource;
import no.fintlabs.kafka.entity.EntityTopicNameParameters;
import no.fintlabs.kafka.entity.FintKafkaEntityConsumerFactoryService;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
public class FravarKafkaConsumer  {

    private final FintKafkaEntityConsumerFactoryService entityConsumer;

    public FravarKafkaConsumer(FintKafkaEntityConsumerFactoryService entityConsumer) {
        this.entityConsumer = entityConsumer;
    }

    public void registerListener(Consumer<FravarResource> consumer) {

        ConcurrentMessageListenerContainer<String, String> resourceConsumer =
                entityConsumer.createConsumer(EntityTopicNameParameters
                        .builder()
                        .orgId("fintlabs.no")
                        .domainContext("fint-core")
                        .resource("utdanning-vurdering-fravar")
                        .build(),
                FravarResource.class,
                consumer,
                (e) -> log.error(e.getMessage())
        );
    }
}
