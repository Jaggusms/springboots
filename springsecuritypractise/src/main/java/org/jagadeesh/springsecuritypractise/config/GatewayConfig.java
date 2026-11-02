//package com.ecommerce.gateway.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GatewayConfig {
//
//    @Bean
//    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
//
//        return builder.routes()
//
//                // User Service
//                .route("user-service", r -> r
//                        .path("/users/**")
//                        .uri("http://localhost:8081"))
//
//                // Product Service
//                .route("product-service", r -> r
//                        .path("/products/**")
//                        .uri("http://localhost:8082"))
//
//                // Order Service
//                .route("order-service", r -> r
//                        .path("/orders/**")
//                        .uri("http://localhost:8083"))
//
//                .build();
//    }
//}

package com.ecommerce.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    // 1. Configure rate limiter
    @Bean
    public RedisRateLimiter redisRateLimiter() {

        return new RedisRateLimiter(
                5,   // Replenish 5 tokens per second
                10   // Maximum burst capacity
        );
    }

    // 2. Identify the client using its IP address
    @Bean
    public KeyResolver userKeyResolver() {

        return exchange -> exchange.getRequest()
                                   .getRemoteAddress() != null
                ? reactor.core.publisher.Mono.just(
                exchange.getRequest()
                        .getRemoteAddress()
                        .getAddress()
                        .getHostAddress()
        )
                : reactor.core.publisher.Mono.just("unknown");
    }

    // 3. Configure routes
    @Bean
    public RouteLocator customRoutes(
            RouteLocatorBuilder builder,
            RedisRateLimiter redisRateLimiter,
            KeyResolver userKeyResolver) {

        return builder.routes()

                // User Service with Rate Limiting
                .route("user-service", r -> r
                        .path("/users/**")
                        .filters(f -> f.requestRateLimiter(config -> config
                                .setRateLimiter(redisRateLimiter)
                                .setKeyResolver(userKeyResolver)
                        ))
                        .uri("http://localhost:8081"))

                // Product Service
                .route("product-service", r -> r
                        .path("/products/**")
                        .uri("http://localhost:8082"))

                // Order Service
                .route("order-service", r -> r
                        .path("/orders/**")
                        .uri("http://localhost:8083"))

                .build();
    }
}