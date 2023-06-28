package br.com.mdrapalski.transactionbff.service;

import br.com.mdrapalski.transactionbff.dto.RequestTransactionDto;
import br.com.mdrapalski.transactionbff.dto.TransactionDto;
import br.com.mdrapalski.transactionbff.exception.exceptions.NotFoundException;
import br.com.mdrapalski.transactionbff.http.TransactionsClient;
import br.com.mdrapalski.transactionbff.repository.TransactionRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    @Value("${app.topic}")
    private String topic;

    private final TransactionRedisRepository repository;
    private final ReactiveKafkaProducerTemplate<String, RequestTransactionDto> eventProducer;
    private final TransactionsClient transactionsClient;

    public TransactionService(
            TransactionRedisRepository repository,
            ReactiveKafkaProducerTemplate reactiveKafkaProducerTemplate,
            TransactionsClient transactionsClient) {
        this.repository = repository;
        this.eventProducer = reactiveKafkaProducerTemplate;
        this.transactionsClient = transactionsClient;
    }

    @Transactional
    public Mono<RequestTransactionDto> save(final RequestTransactionDto requestTransactionDto) {
        return Mono
                .fromCallable(() -> {
                    requestTransactionDto.setDate(LocalDateTime.now());
                    return repository.save(requestTransactionDto);
                })

                .doOnError(throwable -> {
                    log.error(throwable.getMessage(), throwable);
                    throw new NotFoundException("Unable to find resource");
                })

                .doOnSuccess(requestTransactionDto1 -> {
                    log.info("Successfully persisted transaction");
                    eventProducer
                            .send(topic, requestTransactionDto1)
                            .doOnSuccess(voidSenderResult -> log.info(voidSenderResult.toString()))
                            .subscribe();
                })

                .doFinally(signalType -> {
                    if (signalType.compareTo(SignalType.ON_COMPLETE) == 0) {
                        log.info("Message sent successfully");
                    }
                });

    }

    @Retryable(retryFor = QueryTimeoutException.class, maxAttempts = 5, backoff = @Backoff(delay = 100))
    public Optional<TransactionDto> findById(final String id) {
        return repository.findById(id);
    }

    public Flux<List<TransactionDto>> findByBankBranchAndAccountFlux(Long bankBranch, Long account) {
        var transactions = findByBankBranchAndAccount(bankBranch, account);
        return Flux
                .fromIterable(transactions)
                .cache(Duration.ofSeconds(2))
                .limitRate(200)
                .defaultIfEmpty(new TransactionDto())
                .buffer(200);
    }

    public List<TransactionDto> findByBankBranchAndAccount(Long bankBranch, Long account) {
        return transactionsClient.findTransactions(bankBranch, account);
    }
}
