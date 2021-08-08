package co.uk.ak.propertytracker.Scheduler;

import co.uk.ak.propertytracker.location.facade.LocationStatsFacade;
import co.uk.ak.propertytracker.properties.facade.PropertiesTrackerFacade;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class Scheduler {
	private static final Logger LOG = LoggerFactory.getLogger(Scheduler.class);
	private final PropertiesTrackerFacade propertiesTrackerFacade;
	private final LocationStatsFacade locationStatsFacade;

	@Scheduled(cron = "${rightMove.property.fetch.schedule}")
	public void fetchRightMovePropertyUpdates() throws Exception {
		propertiesTrackerFacade.getPropertiesForLocation();
	}

	@Scheduled(cron = "${rightMove.generate.location.stats.schedule}")
	public void generateLocationStats() {
		locationStatsFacade.generateLocationStats();
	}
}
