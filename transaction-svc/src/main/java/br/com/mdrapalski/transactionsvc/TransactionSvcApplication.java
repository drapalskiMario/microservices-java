package br.com.mdrapalski.transactionsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"br.com.mdrapalski.transactionsvc.repository"})
public class TransactionSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionSvcApplication.class, args);
	}

}
