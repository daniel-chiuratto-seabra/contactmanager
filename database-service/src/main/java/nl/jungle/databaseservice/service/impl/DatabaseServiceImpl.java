package nl.jungle.databaseservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import nl.jungle.databaseservice.model.Contact;
import nl.jungle.databaseservice.repository.ContactRepository;
import nl.jungle.databaseservice.repository.ContactTempRepository;
import nl.jungle.databaseservice.repository.entity.ContactEntity;
import nl.jungle.databaseservice.repository.entity.ContactTempEntity;
import nl.jungle.databaseservice.service.DatabaseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.format.DateTimeFormatter.ofPattern;

@Slf4j
@Service
@AllArgsConstructor
public class DatabaseServiceImpl implements DatabaseService {

    private final ModelMapper modelMapper;
    private final ContactRepository contactRepository;
    private final ContactTempRepository contactTempRepository;

    @Override
    public Contact save(final Contact contact) {
        log.info("The following entity will be stored in the definitive repository: {}", contact);

        var contactDefinitiveEntity = this.modelMapper.map(contact, ContactEntity.class);

        return this.modelMapper.map(this.contactRepository.save(contactDefinitiveEntity), Contact.class);
    }

    @Override
    public Iterable<Contact> getContacts() {
        val result = new ArrayList<Contact>();

        this.contactRepository.findAll().forEach(cT -> result.add(this.modelMapper.map(cT, Contact.class)));

        return result;
    }

    @Override
    public Contact saveContactTemp(final Contact contact) throws ParseException {
        log.info("The following entity will be stored in the temporary repository: {}", contact);

        var contactTempEntity = this.modelMapper.map(contact, ContactTempEntity.class);

        contactTempEntity.setExpiryDateTime(this.getExpiryDate());

        return this.modelMapper.map(this.contactTempRepository.save(contactTempEntity), Contact.class);
    }

    private Date getExpiryDate() throws ParseException {
        val pattern = "dd/MM/yyyy HH:mm:ss";
        return new SimpleDateFormat(pattern).parse(now(of("Europe/Amsterdam")).plusSeconds(60).format(ofPattern(pattern)));
    }

    @Override
    public Iterable<Contact> getContactTemps() {
        val result = new ArrayList<Contact>();

        this.contactTempRepository.findAll().forEach(cT -> result.add(this.modelMapper.map(cT, Contact.class)));

        return result;
    }

    @Override
    public Contact deleteContactTempById(final String id) {
        val contactOptional = this.contactTempRepository.findById(id);

        if (contactOptional.isPresent()) {
            this.contactTempRepository.deleteById(id);
            return this.modelMapper.map(contactOptional.get(), Contact.class);
        } else {
            return null;
        }
    }

    @Override
    public Contact getContactTempById(final String id) {
        val contactTempOptional = this.contactTempRepository.findById(id);
        return contactTempOptional.map(contactTempEntity -> this.modelMapper.map(contactTempEntity, Contact.class)).orElse(null);
    }
}