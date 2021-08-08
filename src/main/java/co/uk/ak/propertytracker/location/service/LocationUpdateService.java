package co.uk.ak.propertytracker.location.service;

import co.uk.ak.propertytracker.location.dto.LocationDto;
import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface LocationUpdateService {

	void recordLocationUpdates(LocationDto locationDto) throws JsonProcessingException;
}
