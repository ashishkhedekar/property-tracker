package co.uk.ak.propertytracker.properties.service;

import co.uk.ak.propertytracker.properties.model.PropertyModel;
import co.uk.ak.propertytracker.properties.model.PropertyUpdateModel;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;

import java.util.Set;

public interface PropertyDeltaCheckerService {
	 boolean hasChangeUpdates(PropertyModel propertyModel, RightMoveProperty rightMoveProperty);
	 Set<PropertyUpdateModel> getPropertyUpdates(PropertyModel propertyModel, RightMoveProperty rightMoveProperty);
}
