package co.uk.ak.propertytracker.properties.service.impl;

import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.properties.PropertyRepository;
import co.uk.ak.propertytracker.properties.mapper.RightMovePropertyToPropertModelMapper;
import co.uk.ak.propertytracker.properties.model.PropertyModel;
import co.uk.ak.propertytracker.properties.service.PropertyDeltaCheckerService;
import co.uk.ak.propertytracker.properties.service.PropertyService;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultPropertyService implements PropertyService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultPropertyService.class);

	private final PropertyRepository propertyRepository;
	private final RightMovePropertyToPropertModelMapper mapper;
	private final PropertyDeltaCheckerService propertyDeltaCheckerService;

	@Override
	public PropertyModel saveProperty(LocationModel location, RightMoveProperty rightMoveProperty) {
		final PropertyModel propertyModel = mapper.rightMovePropertyToPropertyModel(rightMoveProperty);
		//logic to check if property exists
		propertyModel.getLocations().add(location);
		return propertyRepository.save(propertyModel);
	}

	@Override
	public PropertyModel updateProperty(LocationModel location, RightMoveProperty rightMoveProperty) {

		final PropertyModel propertyModel = mapper.rightMovePropertyToPropertyModel(rightMoveProperty);
		propertyModel.getPropertyUpdates()
				.addAll(propertyDeltaCheckerService.getPropertyUpdates(propertyModel, rightMoveProperty));
		return propertyModel;
	}

	@Override
	public boolean isNewProperty(Long id) {
		return propertyRepository.findByPropertyId(id).isEmpty();
	}

	@Override
	public boolean hasPropertyChanged(RightMoveProperty rightMoveProperty) {
		final Optional<PropertyModel> existingPropertyOptional = propertyRepository.findByPropertyId(rightMoveProperty.getId());
		return existingPropertyOptional
				.filter(propertyModel -> propertyDeltaCheckerService.hasChangeUpdates(propertyModel, rightMoveProperty))
				.isPresent();
	}
}
