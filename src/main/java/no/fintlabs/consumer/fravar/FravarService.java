package no.fintlabs.consumer.fravar;

import lombok.extern.slf4j.Slf4j;
import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.resource.utdanning.vurdering.FravarResource;
import no.fint.model.utdanning.vurdering.Fravar;
import no.fintlabs.cache.Cache;
import no.fintlabs.cache.CacheManager;
import no.fintlabs.cache.packing.PackingTypes;
import no.fintlabs.core.consumer.shared.ConsumerProps;
import no.fintlabs.core.consumer.shared.resource.CacheService;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Slf4j
@Service
public class FravarService extends CacheService<FravarResource> {

    private final FravarKafkaConsumer fravarKafkaConsumer;

    private final FravarLinker linker;

    public FravarService(
            FravarKafkaConsumer fravarKafkaConsumer,
            FravarLinker linker,
            CacheManager cacheManager,
            FravarConfig fravarConfig) {
        super(fravarConfig, cacheManager, fravarKafkaConsumer);
        this.fravarKafkaConsumer = fravarKafkaConsumer;
        this.linker = linker;
    }

    @Override
    protected Cache<FravarResource> initializeCache(CacheManager cacheManager, ConsumerConfig<FravarResource> consumerConfig, String modelName) {
        return cacheManager.<FravarResource>create(PackingTypes.POJO, consumerConfig.getOrgId(), consumerConfig.getResourceName());
    }

    @PostConstruct
    private void registerKafkaListener() {
        long retention = fravarKafkaConsumer.registerListener(FravarResource.class, this::addResourceToCache);
        getCache().setRetentionPeriodInMs(retention);
    }

    private void addResourceToCache(ConsumerRecord<String, FravarResource> consumerRecord) {
        FravarResource fravarResource = consumerRecord.value();
        linker.mapLinks(fravarResource);
        this.getCache().put(consumerRecord.key(), fravarResource, linker.hashCodes(fravarResource));

        //log.info("The cache now containes " + this.getCacheSize() + " elements.");
    }

    @Override
    public Optional<FravarResource> getBySystemId(String systemId) {
        return getCache().getLastUpdatedByFilter(systemId.hashCode(),
                (resource) -> Optional
                        .ofNullable(resource)
                        .map(FravarResource::getSystemId)
                        .map(Identifikator::getIdentifikatorverdi)
                        .map(systemId::equals)
                        .orElse(false)
        );
    }
}
