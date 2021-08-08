package co.uk.ak.propertytracker.properties.facade;

import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;

public interface PropertiesFacade {

	void createOrUpdateProperty(String locationCode, RightMoveProperty rightMoveProperty);
}
