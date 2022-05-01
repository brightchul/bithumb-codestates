package com.example.demo.controller;

import com.example.demo.dto.Response;
import com.example.demo.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


// @RestController로 해도 됨
@Configuration
public class ReactiveFunctionalMathConfig {
    @Autowired
    ReactiveMathService mathService;

    @Bean
    public RouterFunction<ServerResponse> functionalMath() {
        return route().nest(path("/functional-math"), builder -> {
            builder
                    .GET("/square/{input}", (req) -> this.squareHandler(req))
                    .GET("/table/{input}", (req -> this.multiplicationTable(req)));
        }).build();
    }

    public Mono<ServerResponse> squareHandler(ServerRequest req) {
        int input = Integer.parseInt(req.pathVariable("input"));
        Mono<Response> a = mathService.findSquare(input);
        return ServerResponse.ok().body(a, Response.class);
    }

    public Mono<ServerResponse> multiplicationTable(ServerRequest req) {
        int input = Integer.parseInt(req.pathVariable("input"));
        Flux<Response> fluxTale = mathService.multiplicationTable(input);
        return ServerResponse.ok().body(fluxTale, Response.class);
    }
}
