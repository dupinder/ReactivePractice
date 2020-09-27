package com.reactive.practice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MonoController {

    @GetMapping("/mono")
    public Mono<String> returnMono(){
        return Mono.just("Hello Jarvis")
                .log();
    }
}
