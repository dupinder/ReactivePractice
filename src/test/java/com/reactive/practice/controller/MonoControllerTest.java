package com.reactive.practice.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebFluxTest
public class MonoControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @Test
    public void monoTest() {
        String expectedString = "Hello Jarvis";
        webTestClient.get()
                .uri("/mono")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith((res) -> {
                    assertEquals(expectedString, res.getResponseBody());
                });

    }
}
