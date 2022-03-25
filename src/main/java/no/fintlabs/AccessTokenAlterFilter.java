package no.fintlabs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Slf4j
public class AccessTokenAlterFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info(String.valueOf(exchange.getRequest().getHeaders().get("Authorization")));
        return chain.filter(exchange);
    }
}