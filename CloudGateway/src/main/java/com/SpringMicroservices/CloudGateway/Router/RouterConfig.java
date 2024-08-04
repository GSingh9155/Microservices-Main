package com.SpringMicroservices.CloudGateway.Router;


//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.function.RouterFunction;
//import org.springframework.web.servlet.function.ServerResponse;
//
//import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
//import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
//import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
//import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

//@Configuration
//public class RouterConfig {
//
//    @Bean
//    public RouterFunction<ServerResponse> routeConfig(){
//        return route("ORDER-SERVICE_route")
//                .route(path("/order/**").or(path("/order")), http("http://localhost:8082"))
//                .filter(circuitBreaker(config -> config.setId("ORDER-SERVICE-CircuitBreaker")
//                        .setStatusCodes("500")))
//                .build();
//    }
//
//}
