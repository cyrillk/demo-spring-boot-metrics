package com.example.demo.controllers;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.Math.atan;
import static java.lang.Math.tan;

@RestController
public class DumbController {

    @Autowired
    private MeterRegistry meter;

    @GetMapping("/dumb")
    public Mono<String> handle() {
        return Flux.range(0, 65536)
                .map(this::calculate)
                .flatMap(this::calculateOther)
                .count().map(Object::toString);
    }

    @Timed("calculate.plain.timed")
    @Scheduled
    private String calculate(int n) {

        meter.counter("calculate.plain.explicit").increment();

        return String.valueOf(tan(atan(tan(atan(n)))));
    }

    @Timed("calculate.reactive.timed")
    @Scheduled
    private Mono<String> calculateOther(String n) {

        meter.counter("calculate.reactive.explicit").increment();

        return Mono.just(n + "111");
    }
}