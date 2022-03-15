package no.fintlabs.fravar;

import lombok.extern.slf4j.Slf4j;
import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.resource.utdanning.vurdering.FravarResource;
import no.fint.model.utdanning.vurdering.Vurdering;
import no.fint.relations.internal.FintLinkMapper;
import no.fintlabs.ConsumerService;
import no.fintlabs.cache.FintCache;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Slf4j
@Service
public class FravarService extends ConsumerService<FravarResource> {
    public static final String MODEL = Vurdering.class.getSimpleName().toLowerCase();

    private FravarKafkaConsumer fravarKafkaConsumer;
    private FravarLinker linker;

    public FravarService(FravarKafkaConsumer fravarKafkaConsumer, FravarLinker linker) {
        super(new FintCache<>());

        this.fravarKafkaConsumer = fravarKafkaConsumer;
        this.linker = linker;
    }

    @PostConstruct
    private void registerKafkaListener() {
        fravarKafkaConsumer.registerListener(this::addResourceToCache);
    }

    private void addResourceToCache(ConsumerRecord<String, FravarResource> consumerRecord) {
        FravarResource fravarResource = consumerRecord.value();
        linker.mapLinks(fravarResource);
        this.getCache().put(consumerRecord.key(), fravarResource, linker.hashCodes(fravarResource));
    }

    public Optional<FravarResource> getFravarBySystemId(String systemId) {
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
