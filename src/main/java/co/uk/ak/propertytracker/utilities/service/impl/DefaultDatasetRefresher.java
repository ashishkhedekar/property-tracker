package co.uk.ak.propertytracker.utilities.service.impl;

import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.location.repository.LocationMarketStatsRepository;
import co.uk.ak.propertytracker.location.repository.LocationRepository;
import co.uk.ak.propertytracker.properties.facade.PropertiesFacade;
import co.uk.ak.propertytracker.properties.model.PropertyModel;
import co.uk.ak.propertytracker.properties.repository.PropertyRepository;
import co.uk.ak.propertytracker.properties.service.PropertyService;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import co.uk.ak.propertytracker.rightmove.repository.RightMovePropertyUpdateRepository;
import co.uk.ak.propertytracker.utilities.service.DatasetRefresher;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultDatasetRefresher implements DatasetRefresher {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultDatasetRefresher.class);

	private final PropertiesFacade propertiesFacade;
	private final PropertyService propertyService;
	private final PropertyRepository propertyRepository;
	private final LocationRepository locationRepository;
	private final LocationMarketStatsRepository locationMarketStatsRepository;
	private final RightMovePropertyUpdateRepository rightMovePropertyUpdateRepository;

	@Transactional
	@Override
	public void refreshDataset() {

		locationRepository.findAll().forEach(locationModel -> {
			locationModel.setProperties(null);
		});
		propertyRepository.deleteAll();

		rightMovePropertyUpdateRepository.findAllByOrderByCreationTimeDesc().forEach(propertyUpdate -> {
			String rightMovePropertyJson = propertyUpdate.getJson();
			final String locationCode = propertyUpdate.getLocationCode();
			final LocationModel locationModel = locationRepository.findByCode(locationCode);
			final RightMoveProperty rightMoveProperty = new Gson().fromJson(rightMovePropertyJson, RightMoveProperty.class);
			LOG.info("Saving The right Move property id [{}] for location [{}] ", rightMoveProperty.getId(), locationModel.getId());
			final Optional<PropertyModel> byPropertyId = propertyRepository.findByPropertyId(rightMoveProperty.getId());
			if (byPropertyId.isPresent())
			{
				propertyService.updateProperty(locationModel, rightMoveProperty);
				LOG.info("The right Move property id [{}] for location [{}] UPDATED successfully", rightMoveProperty.getId(), locationModel.getId());
			}
			else
			{
				propertyService.saveProperty(locationModel, rightMoveProperty, true);
				LOG.info("The right Move property id [{}] for location [{}] SAVED successfully", rightMoveProperty.getId(), locationModel.getId());
			}
		});
	}

	private void deleteOldData() {

//		locationRepository.findAll().forEach(locationModel -> {
//			locationModel.getProperties().stream().forEach();
//		});

//		locationMarketStatsRepository.deleteAll();
	}
}
