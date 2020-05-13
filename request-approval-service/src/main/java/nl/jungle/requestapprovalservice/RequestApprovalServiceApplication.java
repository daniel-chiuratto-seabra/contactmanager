package nl.jungle.requestapprovalservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class RequestApprovalServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(RequestApprovalServiceApplication.class, args);
    }

}
