package co.uk.ak.propertytracker.properties.facade;

import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;

import java.util.List;

public interface PropertiesFacade {

	List<RightMoveProperty> getPropertiesForLocation() throws Exception;
}
