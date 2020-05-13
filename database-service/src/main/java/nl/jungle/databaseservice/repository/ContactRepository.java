package nl.jungle.databaseservice.repository;

import nl.jungle.databaseservice.repository.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactEntity, String> {
}
