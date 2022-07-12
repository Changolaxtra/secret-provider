package dan.rojas.epam.secretprovider.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class AESService {

  public static final String AES_CBC = "AES/CBC/PKCS5PADDING";
  public static final String AES = "AES";

  private final IvParameterSpec ivParameterSpec;

  public String generateSecretKey() throws NoSuchAlgorithmException {
    log.info("Generating key...");
    final SecretKey secretKey = KeyGenerator.getInstance(AES).generateKey();
    return Base64.getEncoder().encodeToString(secretKey.getEncoded());
  }

  public SecretKey decodeSecretKey(final String secretKeyBase64) {
    log.info("Decoding key...");
    byte[] decodedKey = Base64.getDecoder().decode(secretKeyBase64);
    return new SecretKeySpec(decodedKey, 0, decodedKey.length, AES);
  }

  public String encrypt(final String input, final SecretKey key) {
    String encryptedMessage = null;
    try {
      log.info("Encrypting message...");
      final Cipher cipher = Cipher.getInstance(AES_CBC);
      cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
      final byte[] cipherText = cipher.doFinal(input.getBytes());
      encryptedMessage = Base64.getEncoder()
          .encodeToString(cipherText);
    } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
        BadPaddingException | InvalidKeyException | NoSuchAlgorithmException e) {
      log.error("Error encrypting the message", e);
    }
    return encryptedMessage;
  }

  public String decrypt(final String cipherText, final SecretKey key) {
    String decryptedMessage = null;
    try {
      log.info("Decrypting message...");
      final Cipher cipher = Cipher.getInstance(AES_CBC);
      cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
      final byte[] plainText = cipher.doFinal(Base64.getDecoder()
          .decode(cipherText));
      decryptedMessage = new String(plainText);
    } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
        BadPaddingException | InvalidKeyException | NoSuchAlgorithmException e) {
      log.error("Error decrypting the message", e);
    }
    return decryptedMessage;
  }


}
