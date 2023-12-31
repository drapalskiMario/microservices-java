package br.com.mdrapalski.transactionbff;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories(basePackages = {"br.com.mdrapalski.transactionbff.redis"})
@EnableFeignClients
public class TransactionBffApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionBffApplication.class, args);
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonsTags() {
        return registry -> registry
                .config()
                .commonTags("application", "transaction-bff");
    }
}
