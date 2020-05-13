package nl.jungle.emailservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Contact {

    private String id;
    private String name;
    private String middleName;
    private String lastName;
    private String dateOfBirth;
    private String streetAddress;
    private int number;
    private String city;
    private String country;
    private String homePhoneNumber;
    private String cellPhoneNumber;

}
