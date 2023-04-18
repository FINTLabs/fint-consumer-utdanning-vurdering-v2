package no.fintlabs.consumer.model.fravarsoversikt;

import no.fint.model.resource.utdanning.vurdering.FravarsoversiktResource;
import no.fint.model.resource.utdanning.vurdering.FravarsoversiktResources;
import no.fint.relations.FintLinker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Component
public class FravarsoversiktLinker extends FintLinker<FravarsoversiktResource> {

    public FravarsoversiktLinker() {
        super(FravarsoversiktResource.class);
    }

    public void mapLinks(FravarsoversiktResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public FravarsoversiktResources toResources(Collection<FravarsoversiktResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public FravarsoversiktResources toResources(Stream<FravarsoversiktResource> stream, int offset, int size, int totalItems) {
        FravarsoversiktResources resources = new FravarsoversiktResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(FravarsoversiktResource fravar) {
        return getAllSelfHrefs(fravar).findFirst().orElse(null);
    }


    @Override
    public Stream<String> getAllSelfHrefs(FravarsoversiktResource fravar) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(fravar.getSystemId()) && !StringUtils.isEmpty(fravar.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(fravar.getSystemId().getIdentifikatorverdi(), "systemid"));
        }

        return builder.build();
    }

    int[] hashCodes(FravarsoversiktResource fravar) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(fravar.getSystemId()) && !StringUtils.isEmpty(fravar.getSystemId().getIdentifikatorverdi())) {
            builder.add(fravar.getSystemId().getIdentifikatorverdi().hashCode());
        }

        return builder.build().toArray();
    }
}