package nl.jungle.emailservice.service;

import com.fasterxml.jackson.databind.JsonNode;

import javax.mail.MessagingException;
import java.net.URISyntaxException;

public interface KafkaService {

    void consumeContact(JsonNode contactJsonNode) throws MessagingException, URISyntaxException;

    void cleanResult(JsonNode contactCollectionJsonNode) throws MessagingException;
}
