package nl.jungle.databaseservice.scheduled;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import nl.jungle.databaseservice.model.Contact;
import nl.jungle.databaseservice.repository.ContactTempRepository;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor
public class ContactTempCleaner {

    private static final String CLEAN_RESULT_TOPIC = "CONTACT-CLEAN-RESULT";

    private final ModelMapper modelMapper;
    private final ContactTempRepository contactTempRepository;
    private final KafkaTemplate<String, Collection<Contact>> kafkaTemplate;

    @Scheduled(fixedRate = 30000)
    public void contactTempCleaner() throws ParseException {
        log.info("Checking if there is expired contacts for removal");

        val now = this.getNowAsDate();

        val result = this.contactTempRepository.findAll().stream().filter(c -> c.getExpiryDateTime().before(now)).collect(toList());

        if (!result.isEmpty()) {
            log.info("Cleaning {} non-approved temporary Contacts", result.size());

            val resultCollection = new ArrayList<Contact>();
            result.forEach(c -> resultCollection.add(this.modelMapper.map(c, Contact.class)));

            this.contactTempRepository.deleteAll(result);

            this.kafkaTemplate.send(new ProducerRecord<>(CLEAN_RESULT_TOPIC, resultCollection));
        }
    }

    private Date getNowAsDate() throws ParseException {
        val pattern = "dd/MM/yyyy HH:mm:ss";
        return new SimpleDateFormat(pattern).parse(now(of("Europe/Amsterdam")).format(ofPattern(pattern)));
    }

}
