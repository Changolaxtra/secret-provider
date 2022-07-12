package dan.rojas.epam.secretprovider.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "secrets")
@ToString
public class SecretMessage {
  @Id
  @EqualsAndHashCode.Include
  private String uuid;

  @Column(name = "sender_username")
  private String sender;

  @Column(name = "receiver_username")
  private String receiver;

  private String message;
}
