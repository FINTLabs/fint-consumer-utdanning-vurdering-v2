package no.fintlabs.consumer.elevfravar;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class ElevfravarService extends CacheService<ElevfravarResource> {
    private final ElevfravarKafkaConsumer elevfravarKafkaConsumer;

    private final ElevfravarLinker linker;

    private final MetricService metricService;

    private final ElevfravarConfig elevfravarConfig;

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
        this.elevfravarConfig = elevfravarConfig;
    }

    @Override
    protected Cache<ElevfravarResource> initializeCache(CacheManager cacheManager, ConsumerConfig<ElevfravarResource> consumerConfig, String s) {
        return cacheManager.create(PackingTypes.POJO, consumerConfig.getOrgId(), consumerConfig.getResourceName());
    }

    // temporary custom code to test metrics:
    @PostConstruct()
    private void registerMetric() {
        while (getCache() == null){
            log.warn("Cache is null. Will wait 5 seconds before register metrics");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.debug("Failed to wait");
            }
        }

        metricService.register(elevfravarConfig.getDomainName(), elevfravarConfig.getPackageName(), elevfravarConfig.getResourceName(), elevfravarConfig.getOrgId(), getCache());
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