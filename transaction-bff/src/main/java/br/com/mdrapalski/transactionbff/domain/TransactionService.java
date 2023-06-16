package br.com.mdrapalski.transactionbff.domain;

import br.com.mdrapalski.transactionbff.dto.RequestTransactionDto;
import br.com.mdrapalski.transactionbff.dto.TransactionDto;
import br.com.mdrapalski.transactionbff.redis.TransactionRedisRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRedisRepository transactionRedisRepository;

    public TransactionService(TransactionRedisRepository transactionRedisRepository, RedisTemplate<String, String> redisTemplate) {
        this.transactionRedisRepository = transactionRedisRepository;
    }

    @Transactional
    public Optional<TransactionDto> save(final RequestTransactionDto requestTransactionDto) {
        requestTransactionDto.setDate(LocalDateTime.now());
        return Optional.of(this.transactionRedisRepository.save(requestTransactionDto));
    }

    public Optional<TransactionDto> findById(final String id) {
        return this.transactionRedisRepository.findById(id);
    }
}
