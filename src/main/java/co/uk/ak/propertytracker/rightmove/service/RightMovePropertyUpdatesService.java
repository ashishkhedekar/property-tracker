package co.uk.ak.propertytracker.rightmove.service;

import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface RightMovePropertyUpdatesService {

	void recordPropertyUpdate(LocationModel locationModel, RightMoveProperty rightMoveProperty) throws JsonProcessingException;

	boolean propertyUpdateExists(RightMoveProperty rightMoveProperty);
}
