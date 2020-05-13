package nl.jungle.makedefinitiveservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jungle.makedefinitiveservice.model.Contact;
import nl.jungle.makedefinitiveservice.service.MakeDefinitiveService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor
public class MakeDefinitiveServiceImpl implements MakeDefinitiveService {

    private final RestTemplate restTemplate;

    @Override
    public void makeDefinitive(final String id) throws URISyntaxException {
        try {
            var responseEntity = this.restTemplate.getForEntity(new URI("http://localhost:8080/database/temp/" + id), Contact.class);
            responseEntity = this.restTemplate.postForEntity(new URI("http://localhost:8080/database"), responseEntity.getBody(), Contact.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                log.info("The following contact were successfully moved to definitive: {}", responseEntity.getBody());

                this.restTemplate.delete(new URI(format("http://localhost:8080/database/%s", id)));
            } else
                log.error("An error occurred when trying to make the following contact definitive: {}", responseEntity.getBody());
        } catch (final HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.info("There is not Contact with id '{}' in the temp database", id);
            } else {
                throw e;
            }
        }
    }
}
