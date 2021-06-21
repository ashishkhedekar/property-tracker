package co.uk.ak.propertytracker.properties.facade.impl;

import co.uk.ak.propertytracker.location.dto.LocationDto;
import co.uk.ak.propertytracker.location.facade.LocationFacade;
import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.location.service.LocationService;
import co.uk.ak.propertytracker.properties.facade.PropertiesFacade;
import co.uk.ak.propertytracker.properties.model.PropertyModel;
import co.uk.ak.propertytracker.properties.service.PropertyService;
import co.uk.ak.propertytracker.rightmove.client.RightMoveWebClient;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class DefaultPropertiesFacade implements PropertiesFacade {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultPropertiesFacade.class);

	private final RightMoveWebClient webClient;
	private final ObjectMapper objectMapper;
	private final LocationFacade locationFacade;
	private final LocationService locationService;
	private final PropertyService propertyService;

	@Transactional
	@Override
	public List<RightMoveProperty> getPropertiesForLocation() {

		final List<LocationDto> allLocations = locationFacade.getAllLocations();
		allLocations.forEach(locationDto -> {
			LOG.info("Finding properties for location [{}]", locationDto.getCode());
			int index = 0;
			int resultCount = Integer.MAX_VALUE;
			while (index < resultCount) {
				final String rightMoveResponse = webClient.getPropertiesForLocation(locationDto.getCode(), index);
				final RightMoveResult rightMoveResult;
				try {
					rightMoveResult = objectMapper.readValue(rightMoveResponse, RightMoveResult.class);
					LOG.info("Total number [{}] ", rightMoveResult.getResultCount());
					if (!CollectionUtils.isEmpty(rightMoveResult.getProperties())) {
						rightMoveResult.getProperties().forEach(rightMoveProperty -> {
							LOG.info("Looking for rightMoveProperty with id [{}] ", rightMoveProperty.getId());
							if (propertyService.isNewProperty(rightMoveProperty.getId())) {
								LOG.info("Saving new rightMoveProperty [{}] ", rightMoveProperty.getId());
								final LocationModel location = locationService.getLocation(locationDto.getCode());
								final PropertyModel saveProperty = propertyService.saveProperty(location, rightMoveProperty);
								location.getProperties().add(saveProperty);
							}
							else if (propertyService.hasPropertyChanged(rightMoveProperty))
							{
								LOG.info("Updating existing rightMoveProperty [{}] ", rightMoveProperty.getId());
								final LocationModel location = locationService.getLocation(locationDto.getCode());
								propertyService.updateProperty(location, rightMoveProperty);
							}
						});
					}
					index = index + Integer.parseInt(rightMoveResult.getMaxCardsPerPage());
					resultCount = Integer.parseInt(rightMoveResult.getResultCount());
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		});
		return null;
	}
}