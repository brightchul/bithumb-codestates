package com.week2.homework1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Homework1ApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("/hello에 name없이 보낸다.")
    public void testHelloNameEmpty() {
        webTestClient.get().uri("/hello").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBody(Hello.class).value(hello -> {
            Assertions.assertThat(hello.getTo()).isEqualTo("");
            Assertions.assertThat(hello.getMessage()).isEqualTo("hello ");
        });

        webTestClient.get().uri("/hello?name=").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBody(Hello.class).value(hello -> {
            Assertions.assertThat(hello.getTo()).isEqualTo("");
            Assertions.assertThat(hello.getMessage()).isEqualTo("hello ");
        });
    }

    @Test
    @DisplayName("/hello에 name=james를 보낸다.")
    public void testHelloNameJames() {
        webTestClient.get().uri("/hello?name=james").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBody(Hello.class).value(hello -> {
            Assertions.assertThat(hello.getTo()).isEqualTo("james");
            Assertions.assertThat(hello.getMessage()).isEqualTo("hello james");
        });
    }

}
