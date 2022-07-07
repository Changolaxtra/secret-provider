package dan.rojas.epam.secretprovider.service;

import dan.rojas.epam.secretprovider.dao.SecretRepository;
import dan.rojas.epam.secretprovider.dto.CreatedMessageResponse;
import dan.rojas.epam.secretprovider.dto.RetrievedMessageDto;
import dan.rojas.epam.secretprovider.model.SecretMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecretMessageService {

  private final AESService aesService;
  private final SecretKeyService secretKeyService;
  private final SecretRepository secretRepository;
  private final URLBuilder urlBuilder;

  public CreatedMessageResponse sendMessage(final String message, final String receiverUsername) {
    log.info("Creating message in db...");
    String uuid = null;
    String error = null;
    final String senderUsername = getAuthenticatedUsername();
    validateSecretKey(senderUsername);
    final SecretKey secretKey = secretKeyService.getSecretKey(senderUsername);
    final String encryptedMessage = aesService.encrypt(message, secretKey);

    if (Objects.nonNull(encryptedMessage)) {
      uuid = RandomStringUtils.randomAlphanumeric(10);
      final SecretMessage secretMessage = new SecretMessage();
      secretMessage.setUuid(uuid);
      secretMessage.setSender(senderUsername);
      secretMessage.setReceiver(receiverUsername);
      secretMessage.setMessage(encryptedMessage);
      secretRepository.save(secretMessage);
      log.info("Message created.");
    } else {
      error = "Message was not created";
    }

    return buildCreatedMessageResponse(uuid, error);
  }

  public RetrievedMessageDto retrieveMessage(final String uuid) {
    log.info("Getting message for: {} with id: {}", getAuthenticatedUsername(), uuid);
    final RetrievedMessageDto.RetrievedMessageDtoBuilder builder = RetrievedMessageDto.builder();
    final SecretMessage secretMessage = secretRepository.findByUuidAndReceiver(uuid, getAuthenticatedUsername());
    if (secretMessage != null) {
      log.info("Message found: {}", secretMessage);
      final String sender = secretMessage.getSender();
      final SecretKey secretKey = secretKeyService.getSecretKey(sender);
      final String message = aesService.decrypt(secretMessage.getMessage(), secretKey);
      builder.senderUsername(sender).message(message);
    } else {
      builder.error("Message not found.");
    }
    return builder.build();
  }

  private String getAuthenticatedUsername() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }

  private void validateSecretKey(final String username) {
    log.info("Validating secret key for user: {}", username);
    if (!secretKeyService.isSecretKeyAvailable(username)) {
      try {
        secretKeyService.generateSecretKey(username);
      } catch (NoSuchAlgorithmException e) {
        log.error("SecretMessage key could not be generated", e);
      }
    }
  }

  private CreatedMessageResponse buildCreatedMessageResponse(final String id, final String error) {
    CreatedMessageResponse.CreatedMessageResponseBuilder builder = CreatedMessageResponse.builder();
    final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    builder.url(baseUrl + "/message/" + id).error(error);
    return builder.build();
  }


}
