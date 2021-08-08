package co.uk.ak.propertytracker.properties.service.impl;

import co.uk.ak.propertytracker.properties.model.PropertyModel;
import co.uk.ak.propertytracker.properties.model.PropertyUpdateModel;
import co.uk.ak.propertytracker.properties.model.PropertyUpdateType;
import co.uk.ak.propertytracker.properties.service.PropertyDeltaCheckerService;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static co.uk.ak.propertytracker.properties.model.PropertyUpdateType.BACK_ON_MARKET;
import static co.uk.ak.propertytracker.properties.model.PropertyUpdateType.GOING_OFF_MARKET;
import static co.uk.ak.propertytracker.properties.model.PropertyUpdateType.GONE_OFF_MARKET;

@Service
public class DefaultPropertyDeltaCheckerService implements PropertyDeltaCheckerService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultPropertyDeltaCheckerService.class);

	@Override
	public boolean hasChangeUpdates(PropertyModel propertyModel, RightMoveProperty rightMoveProperty) {
		LOG.info("propertyModel.DisplayStatus [{}] and rightMoveProperty.DisplayStatus is [{}]", propertyModel.getDisplayStatus(), rightMoveProperty.getDisplayStatus());
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
			if (propertyUpdateModel.getNewValue().equals(GONE_OFF_MARKET.getCode()))
			{
				propertyUpdateModel.setPropertyUpdateType(GONE_OFF_MARKET);
			}
			else if (propertyUpdateModel.getNewValue().equals(BACK_ON_MARKET.getCode()))
			{
				propertyUpdateModel.setPropertyUpdateType(BACK_ON_MARKET);
			}
			else if (propertyUpdateModel.getNewValue().equals(GOING_OFF_MARKET.getCode()))
			{
				propertyUpdateModel.setPropertyUpdateType(GOING_OFF_MARKET);
			}
			propertyUpdateModels.add(propertyUpdateModel);
		}
		return propertyUpdateModels;
	}
}
