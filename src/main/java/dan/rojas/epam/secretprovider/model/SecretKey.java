package dan.rojas.epam.secretprovider.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "users_secret_key")
public class SecretKey {
  @Id
  @EqualsAndHashCode.Include
  private String username;

  @Column(name = "secret_key")
  private String key;
}
