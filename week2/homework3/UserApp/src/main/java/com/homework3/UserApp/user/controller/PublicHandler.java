package com.homework3.UserApp.user.controller;

import com.homework3.UserApp.user.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Configuration(proxyBeanMethods = false)
public class PublicHandler {
    @Bean
    public Mono<ServerResponse> getUser(ServerRequest helloHandler) {
        User user = new User();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(user), User.class);
    }

    @Bean
    public Mono<ServerResponse> getUsers(ServerRequest helloHandler) {
        List<User> userList = Arrays.asList(new User());
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(userList), User.class);
    }
}
