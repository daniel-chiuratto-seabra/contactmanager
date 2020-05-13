package nl.jungle.databaseservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import nl.jungle.databaseservice.model.Contact;
import nl.jungle.databaseservice.service.DatabaseService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Slf4j
@RestController
@RequestMapping("/database")
@AllArgsConstructor
public class DatabaseServiceController {

    private final DatabaseService databaseService;

    @PostMapping(value = "/temp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contact> postContactTemp(final @RequestBody Contact contact) throws ParseException {
        log.info("Database API just received the following payload to be saved in the temporary database: {}", contact);

        return ResponseEntity.ok(this.databaseService.saveContactTemp(contact));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contact> postContact(final @RequestBody Contact contact) {
        log.info("Database API just received the following payload to be saved in the definitive database: {}", contact);

        return ResponseEntity.ok(this.databaseService.save(contact));
    }

    @GetMapping(value = "/temp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Contact>> getContactTemp() {
        log.info("Database API just received a request for temporary Contact list");

        return ResponseEntity.ok(this.databaseService.getContactTemps());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Contact>> getContactDefinitive() {
        log.info("Database API just received a request for definitive Contact list");

        return ResponseEntity.ok(this.databaseService.getContacts());
    }

    @GetMapping(value = "/temp/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contact> getContactTemp(final @PathVariable("id") String id) {
        log.info("A request for the contact with id '{}' has been made", id);

        val contact = this.databaseService.getContactTempById(id);
        if (contact == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(contact);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contact> deleteContact(final @PathVariable("id") String id) {
        log.info("The Contact with the following id will be removed: {}", id);

        val removedContact = this.databaseService.deleteContactTempById(id);
        if (removedContact == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(removedContact);
    }


}
