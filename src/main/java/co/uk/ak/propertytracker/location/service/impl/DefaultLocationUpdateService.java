package co.uk.ak.propertytracker.location.service.impl;

import co.uk.ak.propertytracker.location.dto.LocationDto;
import co.uk.ak.propertytracker.location.model.LocationUpdateModel;
import co.uk.ak.propertytracker.location.repository.LocationUpdateRepository;
import co.uk.ak.propertytracker.location.service.LocationUpdateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultLocationUpdateService implements LocationUpdateService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultLocationUpdateService.class);

	private final LocationUpdateRepository locationUpdateRepository;

	@Override
	public void recordLocationUpdates(LocationDto locationDto) {

		LOG.info("saving location update for [{}] ", locationDto.getCode());
		LocationUpdateModel locationUpdate = new LocationUpdateModel();
		locationUpdate.setLocationCode(locationDto.getCode());
		final String locationJson = new Gson().toJson(locationDto);
		LOG.info("The location json for id [{}] is [{}] ", locationDto.getCode(), locationJson);
		locationUpdate.setJson(locationJson);
		locationUpdateRepository.save(locationUpdate);
	}
}
