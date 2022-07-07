package dan.rojas.epam.secretprovider.dao;

import dan.rojas.epam.secretprovider.model.SecretMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretRepository extends JpaRepository<SecretMessage, String> {
  SecretMessage findByUuidAndReceiver(String uuid, String receiver);
}
