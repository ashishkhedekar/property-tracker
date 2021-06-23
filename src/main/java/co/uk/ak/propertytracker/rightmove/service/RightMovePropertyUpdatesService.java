package co.uk.ak.propertytracker.rightmove.service;

import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface RightMovePropertyUpdatesService {

	void recordPropertyUpdate(RightMoveProperty rightMoveProperty) throws JsonProcessingException;

	boolean propertyUpdateExists(RightMoveProperty rightMoveProperty);
}
