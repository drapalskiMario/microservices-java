package br.com.mdrapalski.transactionsvc.repository;

import br.com.mdrapalski.transactionsvc.dto.TransactionDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends MongoRepository<TransactionDto, UUID> {
}
