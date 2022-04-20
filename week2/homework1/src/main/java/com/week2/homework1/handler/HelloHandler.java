package com.week2.homework1.handler;


import com.week2.homework1.model.Hello;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class HelloHandler {
    public Mono<ServerResponse> hello(ServerRequest request) {
        String name = request.queryParam("name").orElse("");
        WebClient client = WebClient.create("http://localhost:8081");

        return client.get().uri("/uri?name=" + name).retrieve().bodyToMono(JobResponse.class).flatMap(o -> {

            Hello hello = new Hello(name, String.format("hello %s", name), o.getJob());
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(hello), Hello.class);
        });
    }
}
