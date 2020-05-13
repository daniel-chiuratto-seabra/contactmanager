package nl.jungle.makedefinitiveservice.service;

import java.net.URISyntaxException;

public interface KafkaService {

    void consumeId(String id) throws URISyntaxException;

}
