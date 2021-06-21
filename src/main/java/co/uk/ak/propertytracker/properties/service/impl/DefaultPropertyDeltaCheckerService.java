package co.uk.ak.propertytracker.properties.service.impl;

import co.uk.ak.propertytracker.properties.model.PropertyModel;
import co.uk.ak.propertytracker.properties.model.PropertyUpdateModel;
import co.uk.ak.propertytracker.properties.service.PropertyDeltaCheckerService;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DefaultPropertyDeltaCheckerService implements PropertyDeltaCheckerService {

	@Override
	public boolean hasChangeUpdates(PropertyModel propertyModel, RightMoveProperty rightMoveProperty) {
		return !propertyModel.getDisplayStatus().equals(rightMoveProperty.getDisplayStatus());
	}

	@Override
	public Set<PropertyUpdateModel> getPropertyUpdates(PropertyModel propertyModel,
			RightMoveProperty rightMoveProperty) {

		final Set<PropertyUpdateModel> propertyUpdateModels = new HashSet<>();
		if (!propertyModel.getDisplayStatus().equals(rightMoveProperty.getDisplayStatus()))
		{
			final PropertyUpdateModel propertyUpdateModel = new PropertyUpdateModel();
			propertyUpdateModel.setField("DisplayStatus");
			propertyUpdateModel.setOldValue(propertyModel.getDisplayStatus());
			propertyUpdateModel.setNewValue(rightMoveProperty.getDisplayStatus());
			propertyUpdateModels.add(propertyUpdateModel);
		}
		return propertyUpdateModels;
	}
}