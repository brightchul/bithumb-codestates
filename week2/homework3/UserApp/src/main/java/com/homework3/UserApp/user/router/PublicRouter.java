package com.homework3.UserApp.user.router;

import com.homework3.UserApp.user.controller.PublicHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class PublicRouter {
    @Bean
    public RouterFunction<ServerResponse> route(PublicHandler publicHandler) {
        return RouterFunctions.route()
                .GET("/user", publicHandler::getUser)
                .GET("/users", publicHandler::getUsers)
                .build();
    }
}
