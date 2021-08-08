package co.uk.ak.propertytracker.properties.facade.impl;

import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.location.service.LocationService;
import co.uk.ak.propertytracker.properties.facade.PropertiesFacade;
import co.uk.ak.propertytracker.properties.model.PropertyModel;
import co.uk.ak.propertytracker.properties.service.PropertyService;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class DefaultPropertiesFacade implements PropertiesFacade {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultPropertiesFacade.class);

	private final LocationService locationService;
	private final PropertyService propertyService;

	@Transactional
	@Override
	public void createOrUpdateProperty(String locationCode, final RightMoveProperty rightMoveProperty) {

		LOG.debug("Looking for rightMoveProperty with id [{}] ", rightMoveProperty.getId());
		final LocationModel location = locationService.getLocation(locationCode);
		if (propertyService.isNewProperty(rightMoveProperty.getId())) {
			LOG.debug("Saving new rightMoveProperty [{}] ", rightMoveProperty.getId());
			final PropertyModel saveProperty = propertyService.saveProperty(location, rightMoveProperty, false);
			location.getProperties().add(saveProperty);
		}
		else if (propertyService.hasPropertyChanged(rightMoveProperty))
		{
			LOG.debug("Updating existing rightMoveProperty [{}] ", rightMoveProperty.getId());
			propertyService.updateProperty(location, rightMoveProperty);
		}
		else
		{
			propertyService.logProperty(location, rightMoveProperty);
		}
	}
}
