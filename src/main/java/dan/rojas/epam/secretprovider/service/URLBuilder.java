package dan.rojas.epam.secretprovider.service;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class URLBuilder {

  public String getURL(final String pathBase, final String pathVariable) {
    final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    return baseUrl + "/" + pathBase + "/" + pathVariable;
  }
}
