package nl.jungle.emailservice.service.impl;

import lombok.val;
import nl.jungle.emailservice.model.Contact;
import nl.jungle.emailservice.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Collection;
import java.util.Properties;

import static java.lang.String.format;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Override
    public void sendEmail(final String id) throws MessagingException {
        val messageBody = "Please confirm the contact addition through the following address:\n\nhttp://127.0.0.1:8083/approve/" + id;
        val subject = format("Please confirm contact of id %s", id);

        this.sendEmail(messageBody, subject);
    }

    @Override
    public void sendCleanEmail(final Collection<Contact> contact) throws MessagingException {
        val messageBody = new StringBuilder();
        contact.forEach(c -> {
            if (messageBody.length() > 0)
                messageBody.append("<br>");
            else messageBody.append("The following contacts were removed due to lack of approval:\n");
            messageBody.append(c.toString());
        });

        this.sendEmail(messageBody.toString(), format("There are %d contacts that were removed", contact.size()));
    }

    private void sendEmail(final String messageBody, final String subject) throws MessagingException {
        val session = Session.getInstance(this.getProps(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        val message = new MimeMessage(session);
        message.setFrom(new InternetAddress("info@contact-manager.nl"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("dseabra84@hotmail.com"));
        message.setSubject(subject);


        val mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(messageBody, MediaType.TEXT_HTML_VALUE);

        val multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }


    private Properties getProps() {
        val props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        return props;
    }

}
