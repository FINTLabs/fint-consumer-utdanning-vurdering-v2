package no.fintlabs.consumer.fravarsoversikt;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.resource.utdanning.vurdering.FravarsoversiktResource;
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
public class FravarsoversiktService extends CacheService<FravarsoversiktResource> {

    private final FravarsoversiktKafkaConsumer fravarsoversiktKafkaConsumer;

    private final FravarsoversiktLinker linker;

    public FravarsoversiktService(
            FravarsoversiktKafkaConsumer fravarKafkaConsumer,
            FravarsoversiktLinker linker,
            CacheManager cacheManager,
            FravarsoversiktConfig fravarsoversiktConfig) {
        super(fravarsoversiktConfig, cacheManager, fravarKafkaConsumer);
        this.fravarsoversiktKafkaConsumer = fravarKafkaConsumer;
        this.linker = linker;
    }

    @Override
    protected Cache<FravarsoversiktResource> initializeCache(CacheManager cacheManager, ConsumerConfig<FravarsoversiktResource> consumerConfig, String modelName) {
        return cacheManager.create(PackingTypes.POJO, consumerConfig.getOrgId(), consumerConfig.getResourceName());
    }

    @PostConstruct
    private void registerKafkaListener() {
        fravarsoversiktKafkaConsumer.registerListener(FravarsoversiktResource.class, this::addResourceToCache);
    }

    private void addResourceToCache(ConsumerRecord<String, FravarsoversiktResource> consumerRecord) {
        updateRetensionTime(consumerRecord.headers().lastHeader("topic-retension-time"));
        this.eventLogger.logDataRecieved();
        FravarsoversiktResource fravarResource = consumerRecord.value();
        linker.mapLinks(fravarResource);
        this.getCache().put(consumerRecord.key(), fravarResource, linker.hashCodes(fravarResource));
    }

    public Optional<FravarsoversiktResource> getBySystemId(String systemId) {
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
