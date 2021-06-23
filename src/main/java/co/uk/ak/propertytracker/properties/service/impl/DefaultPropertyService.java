package co.uk.ak.propertytracker.properties.service.impl;

import co.uk.ak.propertytracker.aspect.RecordPropertyUpdate;
import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.properties.repository.PropertyRepository;
import co.uk.ak.propertytracker.properties.mapper.RightMovePropertyToPropertyModelMapper;
import co.uk.ak.propertytracker.properties.model.PropertyModel;
import co.uk.ak.propertytracker.properties.model.PropertyUpdateModel;
import co.uk.ak.propertytracker.properties.model.PropertyUpdateType;
import co.uk.ak.propertytracker.properties.service.PropertyDeltaCheckerService;
import co.uk.ak.propertytracker.properties.service.PropertyService;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class DefaultPropertyService implements PropertyService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultPropertyService.class);

	private final PropertyRepository propertyRepository;
	private final RightMovePropertyToPropertyModelMapper mapper;
	private final PropertyDeltaCheckerService propertyDeltaCheckerService;

	@RecordPropertyUpdate
	@Override
	public PropertyModel saveProperty(LocationModel location, RightMoveProperty rightMoveProperty) {
		LOG.info("Saving property first");
		final PropertyModel propertyModel = mapper.rightMovePropertyToPropertyModel(rightMoveProperty);
		//logic to check if property exists
		propertyModel.getLocations().add(location);
		return propertyRepository.save(propertyModel);
	}

	@RecordPropertyUpdate
	@Override
	public PropertyModel updateProperty(LocationModel location, RightMoveProperty rightMoveProperty) {

		final PropertyModel propertyModel = mapper.rightMovePropertyToPropertyModel(rightMoveProperty);
		final Set<PropertyUpdateModel> propertyUpdates = propertyDeltaCheckerService.getPropertyUpdates(propertyModel, rightMoveProperty);
		propertyModel.getPropertyUpdates().addAll(propertyUpdates);
		if (propertyGoneOffMarket(propertyUpdates))
		{
			propertyModel.setOffMarketDate(new Date());
		}
		return propertyModel;
	}

	@RecordPropertyUpdate
	@Override
	public PropertyModel logProperty(LocationModel location, RightMoveProperty rightMoveProperty) {
		LOG.debug("Property update received for [{}]", rightMoveProperty.getId());
		return null;
	}

	private boolean propertyGoneOffMarket(Set<PropertyUpdateModel> propertyUpdates) {

		return propertyUpdates.stream().anyMatch(
				propertyUpdateModel -> propertyUpdateModel.getPropertyUpdateType()
						.equals(PropertyUpdateType.GONE_OFF_MARKET));
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
