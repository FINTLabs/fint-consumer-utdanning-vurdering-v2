package no.fintlabs.consumer.fravarsregistrering;

import no.fint.model.resource.utdanning.vurdering.FravarsregistreringResource;
import no.fint.model.resource.utdanning.vurdering.FravarsregistreringResources;
import no.fint.relations.FintLinker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Component
public class FravarsregistreringLinker extends FintLinker<FravarsregistreringResource> {

    public FravarsregistreringLinker() {
        super(FravarsregistreringResource.class);
    }

    public void mapLinks(FravarsregistreringResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public FravarsregistreringResources toResources(Collection<FravarsregistreringResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public FravarsregistreringResources toResources(Stream<FravarsregistreringResource> stream, int offset, int size, int totalItems) {
        FravarsregistreringResources resources = new FravarsregistreringResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(FravarsregistreringResource fravar) {
        return getAllSelfHrefs(fravar).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(FravarsregistreringResource fravar) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(fravar.getSystemId()) && !StringUtils.isEmpty(fravar.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(fravar.getSystemId().getIdentifikatorverdi(), "systemid"));
        }

        return builder.build();
    }

    int[] hashCodes(FravarsregistreringResource fravar) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(fravar.getSystemId()) && !StringUtils.isEmpty(fravar.getSystemId().getIdentifikatorverdi())) {
            builder.add(fravar.getSystemId().getIdentifikatorverdi().hashCode());
        }

        return builder.build().toArray();
    }
}