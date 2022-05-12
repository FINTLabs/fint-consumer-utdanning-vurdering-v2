package no.fintlabs.consumer.fravarsoversikt;

import lombok.extern.slf4j.Slf4j;
import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.resource.utdanning.vurdering.FravarsoversiktResource;
import no.fint.model.utdanning.vurdering.Fravarsoversikt;
import no.fintlabs.cache.Cache;
import no.fintlabs.cache.CacheManager;
import no.fintlabs.cache.packing.PackingTypes;
import no.fintlabs.consumer.ConsumerService;
import no.fintlabs.consumer.config.ConsumerProps;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Slf4j
@Service
public class FravarsoversiktService extends ConsumerService<FravarsoversiktResource> {

    private final FravarsoversiktKafkaConsumer fravarsoversiktKafkaConsumer;

    private final FravarsoversiktLinker linker;

    public FravarsoversiktService(FravarsoversiktKafkaConsumer fravarKafkaConsumer, FravarsoversiktLinker linker, CacheManager cacheManager, ConsumerProps consumerProps) {
        super(cacheManager, Fravarsoversikt.class, consumerProps);
        this.fravarsoversiktKafkaConsumer = fravarKafkaConsumer;
        this.linker = linker;
    }

    @Override
    protected Cache<FravarsoversiktResource> initializeCache(CacheManager cacheManager, ConsumerProps consumerProps, String modelName) {
        return cacheManager.<FravarsoversiktResource>create(PackingTypes.DEFLATE, consumerProps.getOrgId(), modelName);
    }

    @PostConstruct
    private void registerKafkaListener() {
        long retention = fravarsoversiktKafkaConsumer.registerListener(this::addResourceToCache);
        getCache().setRetentionPeriodInMs(retention);
    }

    private void addResourceToCache(ConsumerRecord<String, FravarsoversiktResource> consumerRecord) {
        FravarsoversiktResource fravarResource = consumerRecord.value();
        linker.mapLinks(fravarResource);
        this.getCache().put(consumerRecord.key(), fravarResource, linker.hashCodes(fravarResource));

        //log.info("The cache now containes " + this.getCacheSize() + " elements.");
    }

    public Optional<FravarsoversiktResource> getFravarsoversiktBySystemId(String systemId) {
        return getCache().getLastUpdatedByFilter(systemId.hashCode(),
                (resource) -> Optional
                        .ofNullable(resource)
                        .map(FravarsoversiktResource::getSystemId)
                        .map(Identifikator::getIdentifikatorverdi)
                        .map(systemId::equals)
                        .orElse(false)
        );
    }


}
