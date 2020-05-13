package nl.jungle.emailservice.service;

import nl.jungle.emailservice.model.Contact;

import javax.mail.MessagingException;
import java.util.Collection;

public interface EmailService {

    void sendEmail(String contactId) throws MessagingException;

    void sendCleanEmail(Collection<Contact> contact) throws MessagingException;
}
