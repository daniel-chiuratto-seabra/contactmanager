package nl.jungle.databaseservice.repository;

import nl.jungle.databaseservice.repository.entity.ContactTempEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactTempRepository extends JpaRepository<ContactTempEntity, String> {
}
