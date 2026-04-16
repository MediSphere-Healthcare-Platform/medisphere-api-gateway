package com.medisphere.apigateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Enumeration;

@RestController
public class ManualProxyController {

    private final RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "/{service}/**")
    public ResponseEntity<byte[]> proxy(@PathVariable String service, @RequestBody(required = false) byte[] body, HttpServletRequest request) {
        
        //Resolve Target Details
        String targetHost = service + "-service";
        int port = 8081; // Default
        boolean useApiV1 = false;

        switch (service) {
            case "appointment" -> { port = 8081; useApiV1 = true; }
            case "payment"     -> { port = 8082; useApiV1 = true; }
            case "auth"        -> { port = 8083; useApiV1 = true; }
            case "patient"     -> { port = 8084; }
            case "doctor"      -> { port = 8085; }
            case "telemedicine" -> { port = 8086; }
            case "notification" -> { port = 8087; }
            case "ai-symptom"   -> { port = 8088; targetHost = "aisymptomcheck-service"; }
            case "admin"        -> { port = 8089; }
        }
        
        String path = request.getRequestURI();
        String query = request.getQueryString();
        String subPath = path.replace("/" + service, "");

        //Build Target URL
        String targetUrl;
        if (path.contains("/actuator/")) {
            targetUrl = "http://" + targetHost + ":" + port + subPath;
        } else {
            if (useApiV1 && !subPath.startsWith("/api/v1")) {
                subPath = "/api/v1" + subPath;
            }
            targetUrl = "http://" + targetHost + ":" + port + subPath;
        }

        if (query != null) targetUrl += "?" + query;

        //Forward Headers
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            headers.add(name, request.getHeader(name));
        }

        //Execute Request
        try {
            return restTemplate.exchange(targetUrl, HttpMethod.valueOf(request.getMethod()), new HttpEntity<>(body, headers), byte[].class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(("Proxy Error: " + e.getMessage()).getBytes());
        }
    }
}
