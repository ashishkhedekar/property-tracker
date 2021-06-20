package co.uk.ak.propertytracker.properties.service.impl;

import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.properties.PropertyRepository;
import co.uk.ak.propertytracker.properties.mapper.RightMovePropertyToPropertModelMapper;
import co.uk.ak.propertytracker.properties.model.PropertyModel;
import co.uk.ak.propertytracker.properties.service.PropertyService;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultPropertyService implements PropertyService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultPropertyService.class);

	private final PropertyRepository propertyRepository;
	private final RightMovePropertyToPropertModelMapper mapper;

	@Override
	public PropertyModel saveProperty(LocationModel location, RightMoveProperty rightMoveProperty) {
		final PropertyModel propertyModel = mapper.rightMovePropertyToPropertyModel(rightMoveProperty);
		//logic to check if property exists
		propertyModel.getLocations().add(location);
		return propertyRepository.save(propertyModel);
	}
}
