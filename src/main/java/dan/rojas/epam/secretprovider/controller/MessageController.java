package dan.rojas.epam.secretprovider.controller;

import dan.rojas.epam.secretprovider.dto.CreatedMessageResponse;
import dan.rojas.epam.secretprovider.dto.RetrievedMessageDto;
import dan.rojas.epam.secretprovider.dto.SingleMessageDto;
import dan.rojas.epam.secretprovider.service.SecretMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

  private final SecretMessageService secretMessageService;

  @PostMapping
  public CreatedMessageResponse sendMessage(@RequestBody final SingleMessageDto singleMessageDto) {
    return secretMessageService
        .sendMessage(singleMessageDto.getMessage(), singleMessageDto.getReceiverUsername());
  }

  @GetMapping("/{uuid}")
  public RetrievedMessageDto getMessage(@PathVariable final String uuid) {
    return secretMessageService.retrieveMessage(uuid);
  }


}
