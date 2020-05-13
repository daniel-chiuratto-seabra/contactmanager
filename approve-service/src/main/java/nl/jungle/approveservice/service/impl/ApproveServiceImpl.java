package nl.jungle.approveservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jungle.approveservice.service.ApproveService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ApproveServiceImpl implements ApproveService {

    private static final String TOPIC = "CONTACT-DEFINITIVE-REQUEST";

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void approve(final String id) {
        this.kafkaTemplate.send(new ProducerRecord<>(TOPIC, id));
    }
}
