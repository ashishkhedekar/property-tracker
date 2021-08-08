package co.uk.ak.propertytracker.properties.facade;

import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;

import java.util.List;

public interface PropertiesTrackerFacade {

	List<RightMoveProperty> getPropertiesForLocation() throws Exception;
}
