package br.com.mdrapalski.transactionbff.domain;

import br.com.mdrapalski.transactionbff.dto.RequestTransactionDto;
import br.com.mdrapalski.transactionbff.dto.TransactionDto;
import br.com.mdrapalski.transactionbff.redis.TransactionRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    @Value("${app.topic}")
    private String topic;
    private final TransactionRedisRepository transactionRedisRepository;
    private final ReactiveKafkaProducerTemplate<String, RequestTransactionDto> kafkaProducerTemplate;

    public TransactionService(
            TransactionRedisRepository transactionRedisRepository,
            ReactiveKafkaProducerTemplate reactiveKafkaProducerTemplate
    ) {
        this.transactionRedisRepository = transactionRedisRepository;
        this.kafkaProducerTemplate = reactiveKafkaProducerTemplate;
    }

    @Transactional
    public Optional<TransactionDto> save(final RequestTransactionDto requestTransactionDto) {
        requestTransactionDto.setDate(LocalDateTime.now());
        kafkaProducerTemplate
                .send(topic, requestTransactionDto)
                .doOnSuccess(voidSenderResult -> log.info(voidSenderResult.toString()))
                .subscribe();
        return Optional.of(this.transactionRedisRepository.save(requestTransactionDto));
    }

    @Retryable(retryFor = QueryTimeoutException.class, maxAttempts = 5, backoff = @Backoff(delay = 100))
    public Optional<TransactionDto> findById(final String id) {
        return this.transactionRedisRepository.findById(id);
    }
}
