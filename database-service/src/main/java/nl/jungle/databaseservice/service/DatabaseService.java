package nl.jungle.databaseservice.service;

import nl.jungle.databaseservice.model.Contact;

import java.text.ParseException;

public interface DatabaseService {

    Contact save(Contact contact);

    Iterable<Contact> getContacts();

    Contact saveContactTemp(Contact contact) throws ParseException;

    Iterable<Contact> getContactTemps();

    Contact deleteContactTempById(String id);

    Contact getContactTempById(String id);

}
