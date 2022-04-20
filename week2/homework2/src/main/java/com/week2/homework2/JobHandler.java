package com.week2.homework2;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class JobHandler {
    public Mono<ServerResponse> group(ServerRequest request) {
        String name = request.queryParam("name").orElse("");
        Job    job  = new Job(String.format("%s's job!!!", name));

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(job), Job.class);
    }
}