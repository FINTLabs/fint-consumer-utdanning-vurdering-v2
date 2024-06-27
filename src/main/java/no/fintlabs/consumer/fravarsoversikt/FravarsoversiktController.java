package no.fintlabs.consumer.fravarsoversikt;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import no.fint.antlr.FintFilterService;
import no.fint.model.resource.utdanning.vurdering.FravarsoversiktResource;
import no.fint.relations.FintRelationsMediaType;
import no.fintlabs.consumer.config.RestEndpoints;
import no.fintlabs.core.consumer.shared.resource.ConsumerRestController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(name = "Fravarsoversikt", value = RestEndpoints.FRAVARSOVERSIKT, produces = {FintRelationsMediaType.APPLICATION_HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class FravarsoversiktController extends ConsumerRestController<FravarsoversiktResource> {

    public FravarsoversiktController(FravarsoversiktService cacheRepository, FravarsoversiktLinker linker, FintFilterService oDataFilterService) {
        super(cacheRepository, linker, oDataFilterService);
    }

    @PostConstruct
    private void registerIdentificators() {
        super.registerIdenficatorHandler("systemid", FravarsoversiktResource::getSystemId);
    }

}

