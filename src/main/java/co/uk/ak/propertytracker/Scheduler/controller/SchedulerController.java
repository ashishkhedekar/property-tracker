package co.uk.ak.propertytracker.Scheduler.controller;

import co.uk.ak.propertytracker.Scheduler.Scheduler;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/schedule")
public class SchedulerController {

	private static final Logger LOG = LoggerFactory.getLogger(SchedulerController.class);

	private final Scheduler scheduler;

	@GetMapping
	public ResponseEntity<?> runScheduleNow() throws Exception {
		LOG.info("Running schedule on ad-hoc basis");
		scheduler.fetchRightMovePropertyUpdates();
		 return ResponseEntity.status(HttpStatus.OK).body("Schedule ran successfully");
	}
}
