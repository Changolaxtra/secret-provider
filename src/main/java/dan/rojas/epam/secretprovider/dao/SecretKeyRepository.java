package dan.rojas.epam.secretprovider.dao;

import dan.rojas.epam.secretprovider.model.SecretKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretKeyRepository extends JpaRepository<SecretKey, String> {
}
