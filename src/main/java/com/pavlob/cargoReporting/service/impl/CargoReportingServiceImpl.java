package com.pavlob.cargoReporting.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pavlob.cargoReporting.model.CargoDto;
import com.pavlob.cargoReporting.service.CargoReportingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static com.pavlob.Constant.BEARER_PREFIX;
import static com.pavlob.Constant.Kafka.CARGO_REPORT_TOPIC;

@Service
@Slf4j
public class CargoReportingServiceImpl implements CargoReportingService {

    private static final String KAFKA_AUTH_HEADER = KafkaHeaders.PREFIX + HttpHeaders.AUTHORIZATION;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void processCargoReport(final String jwt, final CargoDto cargoDto) throws JsonProcessingException {
        final Message<String> kafkaMessage = MessageBuilder
                .withPayload(objectMapper.writeValueAsString(cargoDto))
                .setHeader(KafkaHeaders.TOPIC, CARGO_REPORT_TOPIC)
                .setHeader(KAFKA_AUTH_HEADER, BEARER_PREFIX + jwt)
                .build();

        log.info("send message {} to kafka", kafkaMessage);
        kafkaTemplate.send(kafkaMessage);
    }
}
