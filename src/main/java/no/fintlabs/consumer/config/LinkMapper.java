package no.fintlabs.consumer.config;

import com.google.common.collect.ImmutableMap;
import no.fint.model.utdanning.vurdering.*;

import java.util.Map;

public class LinkMapper {

    public static Map<String, String> linkMapper(String contextPath) {
        return ImmutableMap.<String, String>builder()
                .put(Anmerkninger.class.getName(), contextPath + RestEndpoints.ANMERKNINGER)
                .put(Eksamensgruppe.class.getName(), contextPath + RestEndpoints.EKSAMENSGRUPPE)
                .put(Eksamensgruppemedlemskap.class.getName(), contextPath + RestEndpoints.EKSAMENSGRUPPEMEDLEMSKAP)
                .put(Fravar.class.getName(), contextPath + RestEndpoints.FRAVAR)
                .put(Fravarsoversikt.class.getName(), contextPath + RestEndpoints.FRAVARSOVERSIKT)
                .put(Halvarsfagvurdering.class.getName(), contextPath + RestEndpoints.HALVARSFAGVURDERING)
                .put(Halvarsordensvurdering.class.getName(), contextPath + RestEndpoints.HALVARSORDENSVURDERING)
                .put(Karakterverdi.class.getName(), contextPath + RestEndpoints.KARAKTERVERDI)
                .put(Sluttfagvurdering.class.getName(), contextPath + RestEndpoints.SLUTTFAGVURDERING)
                .put(Sluttordensvurdering.class.getName(), contextPath + RestEndpoints.SLUTTORDENSVURDERING)
                .put(Underveisfagvurdering.class.getName(), contextPath + RestEndpoints.UNDERVEISFAGVURDERING)
                .put(Underveisordensvurdering.class.getName(), contextPath + RestEndpoints.UNDERVEISORDENSVURDERING)
                .put(Fravarsregistrering.class.getName(), contextPath + RestEndpoints.FRAVARSREGISTRERING)
                .put(Vurdering.class.getName(), contextPath + RestEndpoints.VURDERING)
                .put("no.fint.model.utdanning.kodeverk.Skolear", "/utdanning/kodeverk/skolear")
                .put("no.fint.model.utdanning.elev.Elevforhold", "/utdanning/elev/elevforhold")
                .put("no.fint.model.utdanning.timeplan.Fag", "/utdanning/timeplan/fag")
                .put("no.fint.model.utdanning.utdanningsprogram.Skole", "/utdanning/utdanningsprogram/skole")
                .put("no.fint.model.utdanning.kodeverk.Termin", "/utdanning/kodeverk/termin")
                .put("no.fint.model.utdanning.kodeverk.Eksamensform", "/utdanning/kodeverk/eksamensform")
                .put("no.fint.model.utdanning.elev.Undervisningsforhold", "/utdanning/elev/undervisningsforhold")
                .put("no.fint.model.utdanning.elev.Medlemskap", "/utdanning/elev/medlemskap")
                .put("no.fint.model.utdanning.kodeverk.Karakterstatus", "/utdanning/kodeverk/karakterstatus")
                .put("no.fint.model.utdanning.elev.Skoleressurs", "/utdanning/elev/skoleressurs")
                .put("no.fint.model.utdanning.timeplan.Undervisningsgruppe", "/utdanning/timeplan/undervisningsgruppe")
                .put("no.fint.model.utdanning.kodeverk.Fravarstype", "/utdanning/kodeverk/fravarstype")
                .put("no.fint.model.utdanning.kodeverk.Karakterskala", "/utdanning/kodeverk/karakterskala")
                .put("no.fint.model.utdanning.vurdering.Elevfravar", "/utdanning/vurdering/elevfravar")
                /* .put(TODO,TODO) */
                .build();
    }

}
