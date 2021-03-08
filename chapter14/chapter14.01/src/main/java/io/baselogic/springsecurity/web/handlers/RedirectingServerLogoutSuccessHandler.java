package io.baselogic.springsecurity.web.handlers;

import io.baselogic.springsecurity.domain.EventUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URL;

/**
 * RedirectingServerLogoutSuccessHandler
 *
 * @author mickknutson
 * @since chapter14.01
 */
@Component
@Slf4j
public class RedirectingServerLogoutSuccessHandler implements ServerLogoutSuccessHandler {

    private String logoutSuccessUrl = "/login?logout";

    public RedirectingServerLogoutSuccessHandler() {
        log.debug("*** RedirectingServerLogoutSuccessHandler ***");
    }

    /**
     * Invoked after log out was successful
     *
     * @param exchange the exchange
     * @param authentication the {@link Authentication}
     * @return a completion notification (success or error)
     */
    @Override
    public Mono<Void> onLogoutSuccess(final WebFilterExchange exchange,
                                      final Authentication authentication){

        log.info("*** onLogoutSuccess({}, {})", exchange, authentication);

        ServerWebExchange serverWebExchange = exchange.getExchange();
        serverWebExchange.getSession().subscribe(s -> s.invalidate());

        ServerHttpRequest request = serverWebExchange.getRequest();
        ServerHttpResponse response = serverWebExchange.getResponse();
        response.getCookies().clear();
        request.getCookies().clear();

//        performLogout(authentication);

        log.debug("send redirect to {}", logoutSuccessUrl);

        String path = logoutSuccessUrl.substring(0, logoutSuccessUrl.indexOf('?'));
        String query = logoutSuccessUrl.substring(logoutSuccessUrl.indexOf('?'));

        log.debug("path {}", path);
        log.debug("query {}", query);
        log.debug("send redirect to {}", logoutSuccessUrl);

        return ReactiveHandlerUtils.sendRedirect(request, response, logoutSuccessUrl);

    }

    /**
     * Perform Logout
     * TODO: Need to figure out how to delete JSESSIONID Cookies on server besides:
     * <code>response.getCookies().clear();</code>
     *
     * @param authentication Authentication to logout
     */
    private Mono<String> performLogout(final Authentication authentication){
        log.debug("*** performLogout({})", authentication.getName());

        StringBuilder sb = new StringBuilder("Logout Success");

        if (authentication != null) {
            sb.append(" for ").append(authentication.getName());
//            authentication.setAuthenticated(false);
//            ReactiveSecurityContextHolder.clearContext();
        }

        sb.append(".");

        log.info(sb.toString());

        return Mono.just(sb.toString());
    }


    public RedirectingServerLogoutSuccessHandler logoutSuccessUrl(final String logoutSuccessUrl) {
        this.logoutSuccessUrl = logoutSuccessUrl;
        return this;
    }


} // The End...
