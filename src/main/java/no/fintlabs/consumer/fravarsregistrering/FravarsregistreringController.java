package no.fintlabs.consumer.fravarsregistrering;

import lombok.extern.slf4j.Slf4j;
import no.fint.antlr.FintFilterService;
import no.fint.model.resource.utdanning.vurdering.FravarsregistreringResource;
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
@RequestMapping(name = "Fravarsregistrering", value = RestEndpoints.FRAVAR, produces = {FintRelationsMediaType.APPLICATION_HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class FravarsregistreringController extends ConsumerRestController<FravarsregistreringResource> {

    public FravarsregistreringController(FravarsregistreringService fravarsregistreringService, FravarsregistreringLinker linker, FintFilterService oDataFilterService) {
        super(fravarsregistreringService, linker, oDataFilterService);
    }
}

