package nl.jungle.requestapprovalservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jungle.requestapprovalservice.model.Contact;
import nl.jungle.requestapprovalservice.service.KafkaService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, Contact> kafkaTemplate;

    @Override
    public void produce(final String topic, final Iterable<Contact> contacts) {
        log.info("The following message is being send to the topic '{}': {}", topic, contacts);

        contacts.forEach(contact -> this.kafkaTemplate.send(new ProducerRecord<>(topic, UUID.randomUUID().toString(), contact)));
    }

}
