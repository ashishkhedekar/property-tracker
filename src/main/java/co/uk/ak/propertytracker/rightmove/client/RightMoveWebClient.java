package co.uk.ak.propertytracker.rightmove.client;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class RightMoveWebClient
{
   private static final Logger LOG = LoggerFactory.getLogger(RightMoveWebClient.class);

   @Value("${right.move.base.url}")
   private String rightMoveBaseUrl;

   @Value("${right.move.search.api.url}")
   private String rightMoveSearchApiUrl;

   @Autowired
   private RestTemplate restTemplate;

   @SneakyThrows
   public String getPropertiesForLocation(final String locationId, int index)
   {
      return restTemplate.getForObject(URI.create(buildSearchUrl(locationId, index)), String.class);
   }

   private String buildSearchUrl(String locationId, int index)
   {
      final String url = rightMoveBaseUrl + rightMoveSearchApiUrl + String.format("?locationIdentifier=%s&channel=BUY&includeSSTC=true&index=%d", locationId, index);
      LOG.debug("The url is [{}] ", url);
      return url;
   }
}
