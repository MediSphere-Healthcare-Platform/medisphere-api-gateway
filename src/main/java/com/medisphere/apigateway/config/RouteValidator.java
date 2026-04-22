package com.medisphere.apigateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
@ConfigurationProperties(prefix = "gateway")
public class RouteValidator {

    private List<String> whitelistUrls = new ArrayList<>();

    public List<String> getWhitelistUrls() {
        return whitelistUrls;
    }

    public void setWhitelistUrls(List<String> whitelistUrls) {
        this.whitelistUrls = whitelistUrls;
    }

    public Predicate<ServerHttpRequest> isSecured = request -> whitelistUrls
            .stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
