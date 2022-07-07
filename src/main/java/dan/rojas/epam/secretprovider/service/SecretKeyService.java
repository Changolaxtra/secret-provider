package dan.rojas.epam.secretprovider.service;

import dan.rojas.epam.secretprovider.dao.SecretKeyRepository;
import dan.rojas.epam.secretprovider.model.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecretKeyService {

  private final AESService aesService;
  private final SecretKeyRepository secretKeyRepository;

  public void generateSecretKey(final String username) throws NoSuchAlgorithmException {
    log.info("Generating secret key for user: {}", username);
    final String secretKey = aesService.generateSecretKey();
    final SecretKey secretKeyEntity = new SecretKey();
    secretKeyEntity.setUsername(username);
    secretKeyEntity.setKey(secretKey);
    secretKeyRepository.save(secretKeyEntity);
  }

  public javax.crypto.SecretKey getSecretKey(final String username) {
    log.info("Getting secret key for user: {}", username);
    final SecretKey secretKey = secretKeyRepository.getReferenceById(username);
    return aesService.decodeSecretKey(secretKey.getKey());
  }

  public boolean isSecretKeyAvailable(final String username) {
    log.info("Checking if secret key is available for {}", username);
    return secretKeyRepository.existsById(username);
  }

}
