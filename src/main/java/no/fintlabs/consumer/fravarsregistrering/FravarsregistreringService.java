package no.fintlabs.consumer.fravarsregistrering;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.resource.utdanning.vurdering.FravarsregistreringResource;
import no.fintlabs.cache.Cache;
import no.fintlabs.cache.CacheManager;
import no.fintlabs.cache.packing.PackingTypes;
import no.fintlabs.core.consumer.shared.resource.CacheService;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class FravarsregistreringService extends CacheService<FravarsregistreringResource> {

    private final FravarsregistreringKafkaConsumer fravarsregistreringKafkaConsumer;

    private final FravarsregistreringLinker linker;

    public FravarsregistreringService(
            FravarsregistreringKafkaConsumer fravarsregistreringKafkaConsumer,
            FravarsregistreringLinker linker,
            CacheManager cacheManager,
            FravarsregistreringConfig fravarsregistreringConfig) {
        super(fravarsregistreringConfig, cacheManager, fravarsregistreringKafkaConsumer);
        this.fravarsregistreringKafkaConsumer = fravarsregistreringKafkaConsumer;
        this.linker = linker;
    }

    @Override
    protected Cache<FravarsregistreringResource> initializeCache(CacheManager cacheManager, ConsumerConfig<FravarsregistreringResource> consumerConfig, String modelName) {
        return cacheManager.<FravarsregistreringResource>create(PackingTypes.POJO, consumerConfig.getOrgId(), consumerConfig.getResourceName());
    }

    @PostConstruct
    private void registerKafkaListener() {
        long retention = fravarsregistreringKafkaConsumer.registerListener(FravarsregistreringResource.class, this::addResourceToCache);
        getCache().setRetentionPeriodInMs(retention);
    }

    private void addResourceToCache(ConsumerRecord<String, FravarsregistreringResource> consumerRecord) {
        this.eventLogger.logDataRecieved();
        FravarsregistreringResource fravarResource = consumerRecord.value();
        linker.mapLinks(fravarResource);
        this.getCache().put(consumerRecord.key(), fravarResource, linker.hashCodes(fravarResource));
    }

    @Override
    public Optional<FravarsregistreringResource> getBySystemId(String systemId) {
        return getCache().getLastUpdatedByFilter(systemId.hashCode(),
                (resource) -> Optional
                        .ofNullable(resource)
                        .map(FravarsregistreringResource::getSystemId)
                        .map(Identifikator::getIdentifikatorverdi)
                        .map(systemId::equals)
                        .orElse(false)
        );
    }
}
