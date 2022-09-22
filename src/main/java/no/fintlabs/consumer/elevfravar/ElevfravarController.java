package no.fintlabs.consumer.elevfravar;

import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.utdanning.vurdering.ElevfravarResource;
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
@RequestMapping(name = "Elevfravar", value = RestEndpoints.ELEVFRAVAR, produces = {FintRelationsMediaType.APPLICATION_HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class ElevfravarController extends ConsumerRestController<ElevfravarResource> {

    public ElevfravarController(ElevfravarService elevfravarService, ElevfravarLinker elevfravarLinker) {
        super(elevfravarService, elevfravarLinker);
    }
}
