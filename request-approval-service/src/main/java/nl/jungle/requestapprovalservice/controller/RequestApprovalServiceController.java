package nl.jungle.requestapprovalservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jungle.requestapprovalservice.model.Contact;
import nl.jungle.requestapprovalservice.service.KafkaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/requestapproval")
public class RequestApprovalServiceController {

    private static final String TOPIC = "CONTACT-APPROVAL-REQUEST";

    private final KafkaService kafkaService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postContact(final @RequestBody Iterable<Contact> contact) {
        log.info("A contact request has been received with the following data: {}", contact);

        this.kafkaService.produce(TOPIC, contact);

        return ResponseEntity.ok("An e-mail will be sent to approve the request");
    }

}
