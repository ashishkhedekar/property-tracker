package co.uk.ak.propertytracker.utilities.controller;

import co.uk.ak.propertytracker.utilities.authorisation.AuthorisationService;
import co.uk.ak.propertytracker.utilities.service.DatasetRefresher;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/utils")
public class CleanupController {

	private static final Logger LOG = LoggerFactory.getLogger(CleanupController.class);

	private final AuthorisationService authorisationService;

	private final DatasetRefresher datasetRefresher;

	@GetMapping(value = "/refresh")
	public ResponseEntity<?> refreshDataset(@RequestHeader("Authorization") String authorisation)
	{
		LOG.info("refreshDataset is called");
		if (authorisationService.isAuthorised(authorisation))
		{
			LOG.info("Refreshing dataset successfully");
			datasetRefresher.refreshDataset();
			return ResponseEntity.status(HttpStatus.OK).body("All data cleared");
		}
		else
		{
			LOG.info("UNAUTHORISED request");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Check your authorisation key");
		}
	}

}
