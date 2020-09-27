package com.reactive.practice.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebFluxTest
public class FluxControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @Test
    public void fluxTestApproach1() {
        Flux<Integer> integerFlux = webTestClient.get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();
        StepVerifier.create(integerFlux)
                .expectSubscription()
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .verifyComplete();
    }

    @Test
    public void fluxTestApproach2() {

        webTestClient.get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Integer.class)
                .hasSize(4);
    }


    @Test
    public void fluxTestApproach3() {

        List<Integer> expectedIntegerList = Arrays.asList(1, 2, 3, 4);

        EntityExchangeResult entityExchangeResult =
                webTestClient.get()
                        .uri("/flux")
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON)
                        .expectBodyList(Integer.class)
                        .returnResult();

        assertEquals(expectedIntegerList, entityExchangeResult.getResponseBody());
    }

    @Test
    public void fluxTestApproach4() {

        List<Integer> expectedIntegerList = Arrays.asList(1, 2, 3, 4);
        webTestClient.get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Integer.class)
                .consumeWith((res) -> {
                    assertEquals(expectedIntegerList, res.getResponseBody());
                });

    }


    @Test
    public void fluxInfiniteStreamTestApproach1(){

        Flux<Long> longFlux = webTestClient.get()
                .uri("/fluxInfiniteStream")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier.create(longFlux)
                .expectNext(0l)
                .expectNext(1l)
                .thenCancel()
                .verify();
    }


}
