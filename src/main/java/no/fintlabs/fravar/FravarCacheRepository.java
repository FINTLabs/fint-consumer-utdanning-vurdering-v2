package no.fintlabs.fravar;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.utdanning.vurdering.FravarResource;
import no.fint.model.utdanning.vurdering.Vurdering;
import no.fintlabs.CacheRepository;
import no.fintlabs.cache.Cache;
import no.fintlabs.cache.CacheObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
@Service
public class FravarCacheRepository extends CacheRepository<FravarResource> {
    public static final String MODEL = Vurdering.class.getSimpleName().toLowerCase();

    private FravarKafkaConsumer fravarKafkaConsumer;
    private FravarLinker linker;

    public FravarCacheRepository(Cache<FravarResource> cache, FravarKafkaConsumer fravarKafkaConsumer, FravarLinker linker) {
        super(cache);
        this.fravarKafkaConsumer = fravarKafkaConsumer;
        this.linker = linker;
    }

    @PostConstruct
    private void init() {
        fravarKafkaConsumer.registerListener(this::addResourceToCache);
    }

    private void addResourceToCache(FravarResource fravarResource) {
        String key = getKey(fravarResource);
        linker.mapLinks(fravarResource);
        this.getCache().put(key, fravarResource, linker.hashCodes(fravarResource));
    }

    private String getKey(FravarResource resource) {
        return resource.getSelfLinks()
                .stream()
                .filter(s -> !StringUtils.isNotBlank(s.getHref()))
                .map(s -> s.getHref())
                .map(k -> k.replaceFirst("^https:/\\/.+\\.felleskomponent.no", ""))
                .sorted()
                .findFirst()
                .orElseThrow();
    }

//    public Optional<FravarResource> getFravarBySystemId(String orgId, String systemId) {
//        // TODO: 01/03/2022 hashcode er vel ikke nødvendigvis bygget på systemID?
//        // TODO: 01/03/2022 null-safe?
//        return streamByHashCode(systemId.hashCode())
//                .filter(fravarResource -> fravarResource.getSystemId().getIdentifikatorverdi().equals(systemId))
//                .sorted((f1, f2) -> Long.compare(f2) Comparator.comparingLong(CacheObject::getLastUpdated))
//                .
//    }
//
//    public Optional<FravarResource> getOne(int hashCode, Predicate<T> idFunction) {
//
//        return cache.filter(hashCode, idFunction).max(Comparator.comparingLong(CacheObject::getLastUpdated)).map(CacheObject::getObject);
//    }
}
