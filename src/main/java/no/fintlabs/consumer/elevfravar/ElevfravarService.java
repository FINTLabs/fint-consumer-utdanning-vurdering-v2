package no.fintlabs.consumer.elevfravar;

import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.resource.utdanning.vurdering.ElevfravarResource;
import no.fintlabs.cache.Cache;
import no.fintlabs.cache.CacheManager;
import no.fintlabs.cache.packing.PackingTypes;
import no.fintlabs.core.consumer.shared.resource.CacheService;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
@DependsOn("metricService")
public class ElevfravarService extends CacheService<ElevfravarResource> {
    private final ElevfravarKafkaConsumer elevfravarKafkaConsumer;

    private final ElevfravarLinker linker;

    private final MetricService metricService;

    public ElevfravarService(
            ElevfravarConfig elevfravarConfig,
            CacheManager cacheManager,
            ElevfravarKafkaConsumer elevfravarKafkaConsumer,
            ElevfravarLinker linker,
            MetricService metricService) {
        super(elevfravarConfig, cacheManager, elevfravarKafkaConsumer);
        this.elevfravarKafkaConsumer = elevfravarKafkaConsumer;
        this.linker = linker;
        this.metricService = metricService;
    }

    @Override
    protected Cache<ElevfravarResource> initializeCache(CacheManager cacheManager, ConsumerConfig<ElevfravarResource> consumerConfig, String s) {
        Cache<ElevfravarResource> cache = cacheManager.create(PackingTypes.POJO, consumerConfig.getOrgId(), consumerConfig.getResourceName());

        // temporary custom code to test metrics:
        metricService.register(consumerConfig.getDomainName(), consumerConfig.getPackageName(), consumerConfig.getResourceName(), consumerConfig.getOrgId(), cache);

        return cache;
    }

    @PostConstruct
    private void registerKafkaListener() {
        long retension = elevfravarKafkaConsumer.registerListener(ElevfravarResource.class, this::addResourceToCache);
        getCache().setRetentionPeriodInMs(retension);
    }

    private void addResourceToCache(ConsumerRecord<String, ElevfravarResource> consumerRecord) {
        this.eventLogger.logDataRecieved();
        if (consumerRecord.value() == null) {
            getCache().remove(consumerRecord.key());
        } else {
            ElevfravarResource elevFravarResource = consumerRecord.value();
            linker.mapLinks(elevFravarResource);
            getCache().put(consumerRecord.key(), elevFravarResource, linker.hashCodes(elevFravarResource));
        }
    }

    @Override
    public Optional<ElevfravarResource> getBySystemId(String systemId) {
        return getCache().getLastUpdatedByFilter(systemId.hashCode(),
                resource -> Optional
                        .ofNullable(resource)
                        .map(ElevfravarResource::getSystemId)
                        .map(Identifikator::getIdentifikatorverdi)
                        .map(systemId::equals)
                        .orElse(false));
    }
}