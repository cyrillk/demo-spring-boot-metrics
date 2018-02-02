package com.example.demo.controllers;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    private static final ParameterizedTypeReference<String> TYPE_REF = new ParameterizedTypeReference<String>() {
    };

    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:9999").build();

    @GetMapping("/hello")
    public Mono<String> handle() {
        return webClient.get().uri("/remote").exchange().flatMap(f -> f.bodyToMono(TYPE_REF));
    }
}