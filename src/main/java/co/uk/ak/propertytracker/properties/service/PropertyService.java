package co.uk.ak.propertytracker.properties.service;

import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.properties.model.PropertyModel;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;

public interface PropertyService {

	PropertyModel saveProperty(LocationModel location, RightMoveProperty rightMoveProperty);
	PropertyModel updateProperty(LocationModel location, RightMoveProperty rightMoveProperty);
	boolean isNewProperty(Long id);
	boolean hasPropertyChanged(RightMoveProperty id);
}
