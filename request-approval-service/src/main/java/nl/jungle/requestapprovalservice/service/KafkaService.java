package nl.jungle.requestapprovalservice.service;

import nl.jungle.requestapprovalservice.model.Contact;

public interface KafkaService {

    void produce(String topic, Iterable<Contact> contacts);

}
