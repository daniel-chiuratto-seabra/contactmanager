package nl.jungle.emailservice.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import nl.jungle.emailservice.model.Contact;
import nl.jungle.emailservice.service.EmailService;
import nl.jungle.emailservice.service.KafkaService;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private static final String APPROVAL_REQUEST_TOPIC = "CONTACT-APPROVAL-REQUEST";
    private static final String CLEAN_RESULT_TOPIC = "CONTACT-CLEAN-RESULT";

    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final RestTemplate restTemplate;

    @Override
    @KafkaListener(topics = APPROVAL_REQUEST_TOPIC)
    public void consumeContact(final JsonNode contactJsonNode) throws MessagingException, URISyntaxException {
        val contact = this.objectMapper.convertValue(contactJsonNode, Contact.class);

        log.info("Just consumed from the topic '{}' the following message: {}", APPROVAL_REQUEST_TOPIC, contact);

        val id = UUID.randomUUID().toString();

        contact.setId(id);
        this.emailService.sendEmail(id);

        val responseEntity = this.restTemplate.postForEntity(new URI("http://localhost:8080/database/temp"), contact, Contact.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK)
            log.info("The contact has been successfully added in the temporary database");
        else log.error("An error occurred in the temporary database addition for the contact: {}", contact);
    }

    @Override
    @KafkaListener(topics = CLEAN_RESULT_TOPIC)
    public void cleanResult(final JsonNode contactCollectionJsonNode) throws MessagingException {
        val contactCollection = this.objectMapper.convertValue(contactCollectionJsonNode, Collection.class);
        val result = new ArrayList<Contact>();
        contactCollection.forEach(lH -> result.add(this.objectMapper.convertValue(lH, Contact.class)));

        log.info("The following contacts were removed due to lack of approve: {}", result);

        this.emailService.sendCleanEmail(result);
    }

}
