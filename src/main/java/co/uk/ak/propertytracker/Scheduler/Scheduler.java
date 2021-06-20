package co.uk.ak.propertytracker.Scheduler;

import co.uk.ak.propertytracker.properties.facade.PropertiesFacade;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class Scheduler
{
   private static final Logger LOG = LoggerFactory.getLogger(Scheduler.class);
   private final PropertiesFacade propertiesFacade;

   //Runs every house between 6am till 11pm
//   @Scheduled(cron = "0 * * * * *")
   public void fetchRightMovePropertyUpdates() throws Exception {
     propertiesFacade.getPropertiesForLocation();
   }

}
