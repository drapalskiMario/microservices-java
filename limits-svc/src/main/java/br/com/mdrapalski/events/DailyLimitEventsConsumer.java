package br.com.mdrapalski.events;

import br.com.mdrapalski.dto.TransactionDto;
import br.com.mdrapalski.service.DailyLimitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class DailyLimitEventsConsumer {

    private final static Logger log = LoggerFactory.getLogger(DailyLimitEventsConsumer.class);

    private DailyLimitService service;
    private ObjectMapper mapper;

    public DailyLimitEventsConsumer(DailyLimitService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "${app.topic}")
    public void onConsume(final String message) {
        try {
            var transaction = getTransaction(message);
            service.validateDailyLimit(transaction);
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
