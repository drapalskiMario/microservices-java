package br.com.mdrapalski;


import br.com.mdrapalski.entities.DailyLimit;
import br.com.mdrapalski.controller.repository.DailyLimitRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = DailyLimitRepository.class)
@EntityScan(basePackageClasses = DailyLimit.class)
public class LimitsSvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(LimitsSvcApplication.class, args);
    }
}
