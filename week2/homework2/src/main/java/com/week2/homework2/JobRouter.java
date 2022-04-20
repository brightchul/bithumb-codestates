package com.week2.homework2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class JobRouter {
    @Bean
    public RouterFunction<ServerResponse> route(JobHandler groupHandler) {
        return RouterFunctions.route()
                .GET("/uri", groupHandler::group)
                .build();
    }
}
