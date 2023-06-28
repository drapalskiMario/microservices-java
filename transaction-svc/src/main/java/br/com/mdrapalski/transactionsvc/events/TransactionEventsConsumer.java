package br.com.mdrapalski.transactionsvc.events;

import br.com.mdrapalski.transactionsvc.dto.StatusType;
import br.com.mdrapalski.transactionsvc.dto.TransactionDto;
import br.com.mdrapalski.transactionsvc.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class TransactionEventsConsumer {

    private static final Logger log = LoggerFactory.getLogger(TransactionEventsConsumer.class);

    private final TransactionService service;
    private final ObjectMapper mapper;

    public TransactionEventsConsumer(TransactionService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "${app.topic}")
    public void consumeTransaction(String message) {
        try {
            var transaction = getTransaction(message);
            service.save(transaction);
        } catch (IOException exception) {
            log.error(exception.getMessage(), exception);
        }
    }

    @KafkaListener(topics = "${app.returnTopic}")
    public void consumeReversedTransaction(String message) {
        try {
            var transaction = getTransaction(message);
            log.info("Incoming analyzed transaction {}", transaction);

            if (!(transaction.getStatus() == StatusType.ANALYZED)) {
                return;
            }

            service.approveTransaction(transaction);
            log.info("Analyzed transaction {}", transaction);
        } catch (IOException exception) {
            log.error(exception.getMessage(), exception);
        }
    }

    private TransactionDto getTransaction(String message) throws IOException {
        var transaction = mapper.readValue(message, TransactionDto.class);
        transaction.setDate(LocalDateTime.now());
        return transaction;
    }
}
