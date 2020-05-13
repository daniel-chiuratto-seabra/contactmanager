package nl.jungle.makedefinitiveservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jungle.makedefinitiveservice.service.KafkaService;
import nl.jungle.makedefinitiveservice.service.MakeDefinitiveService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private static final String TOPIC = "CONTACT-DEFINITIVE-REQUEST";

    private final MakeDefinitiveService makeDefinitiveService;

    @Override
    @KafkaListener(topics = TOPIC)
    public void consumeId(final String id) throws URISyntaxException {
        log.info("The following id will become definitive: {}", id);

        this.makeDefinitiveService.makeDefinitive(id);
    }

}
