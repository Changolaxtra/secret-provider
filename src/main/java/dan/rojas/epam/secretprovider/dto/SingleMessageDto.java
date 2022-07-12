package dan.rojas.epam.secretprovider.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class SingleMessageDto {
  String message;
  String receiverUsername;
}
