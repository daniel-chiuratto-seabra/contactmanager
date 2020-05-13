package nl.jungle.databaseservice.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity(name = "CONTACT")
public class ContactEntity {

    @Id
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "DATE_OF_BIRTH")
    private String dateOfBirth;

    @Column(name = "STREET_ADDRESS")
    private String streetAddress;

    @Column(name = "NUMBER")
    private int number;

    @Column(name = "CITY")
    private String city;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "HOME_PHONE_NUMBER")
    private String homePhoneNumber;

    @Column(name = "CELL_PHONE_NUMBER")
    private String cellPhoneNumber;

}
