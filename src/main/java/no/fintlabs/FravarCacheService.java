package no.fintlabs;

import lombok.extern.slf4j.Slf4j;
import no.fint.cache.CacheService;
import no.fint.event.model.Event;
import no.fint.model.resource.utdanning.vurdering.FravarResource;
import no.fint.model.utdanning.vurdering.Vurdering;
import no.fintlabs.kafka.entity.EntityTopicNameParameters;
import no.fintlabs.kafka.entity.FintKafkaEntityConsumerFactoryService;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class FravarCacheService extends CacheService<FravarResource> {

    public static final String MODEL = Vurdering.class.getSimpleName().toLowerCase();

    private final FintKafkaEntityConsumerFactoryService entityConsumer;

    public FravarCacheService(FintKafkaEntityConsumerFactoryService entityConsumer) {
        super();
        this.entityConsumer = entityConsumer;
    }

    @PostConstruct
    public void init() {


        ConcurrentMessageListenerContainer<String, String> consumer = entityConsumer.createConsumer(EntityTopicNameParameters
                        .builder()
                        .orgId("fintlabs.no")
                        .domainContext("fint-core")
                        .resource("utdanning-vurdering-fravar")
                        .build(),
                FravarResource.class,
                FravarCacheService::accept,
                (e) -> log.error(e.getMessage())
        );
    }

    @Override
    public void onAction(Event event) {
        log.info("onAction");
    }

    @Override
    public void populateCache(String s) {

    }

    private static void accept(FravarResource f) {
        log.info("hello");
    }
}
