package br.com.mdrapalski.events;

import br.com.mdrapalski.dto.TransactionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class DailyLimitEventsProducer {

    private final static Logger log = LoggerFactory.getLogger(DailyLimitEventsProducer.class);

    @Value("${app.returnTopic}")
    private String topic;
    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper mapper;

    public DailyLimitEventsProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    public void send(final TransactionDto transactionDto) {
        try {
            var payload = mapper.writeValueAsString(transactionDto);
            var message = MessageBuilder
                    .withPayload(payload)
                    .setHeader(KafkaHeaders.TOPIC, topic)
                    .setHeader(KafkaHeaders.PARTITION, 0)
                    .build();

            kafkaTemplate.send(message);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
        }
    }
}
