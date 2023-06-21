package br.com.mdrapalski.transactionbff.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class MyCircuitBreakerConfig {

    @Bean
    public CircuitBreaker countCircuitBreaker() {
        var circuitBreakerConfig = CircuitBreakerConfig
                .custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(5)
                .slowCallRateThreshold(65.0f)
                .slowCallDurationThreshold(Duration.ofSeconds(2))
                .build();

        var circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        var circuitBreaker = circuitBreakerRegistry.circuitBreaker("limitsSearchBasedOnCount");
        return circuitBreaker;
    }

    @Bean
    public CircuitBreaker timeCircuitBreaker() {
        var circuitBreakerConfig = CircuitBreakerConfig
                .custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED)
                .minimumNumberOfCalls(3)
                .slidingWindowSize(5)
                .failureRateThreshold(70.0f)
                .waitDurationInOpenState(Duration.ofSeconds(10))
                .writableStackTraceEnabled(false)
                .build();

        var circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        var circuitBreaker = circuitBreakerRegistry.circuitBreaker("limitsSearchBasedOnTime");
        return circuitBreaker;
    }
}
