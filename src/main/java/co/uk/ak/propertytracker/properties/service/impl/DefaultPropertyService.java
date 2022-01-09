package co.uk.ak.propertytracker.properties.service.impl;

import co.uk.ak.propertytracker.aspect.RecordPropertyUpdate;
import co.uk.ak.propertytracker.location.dto.LocationDto;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class DefaultPropertyService implements PropertyService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultPropertyService.class);

	private final PropertyRepository propertyRepository;
	private final RightMovePropertyToPropertyModelMapper mapper;
	private final PropertyDeltaCheckerService propertyDeltaCheckerService;

	@RecordPropertyUpdate
	@Override
	public PropertyModel saveProperty(LocationModel location, RightMoveProperty rightMoveProperty, boolean isLoad) {
		LOG.info("Saving property first");
		final PropertyModel propertyModel = mapper.rightMovePropertyToPropertyModel(rightMoveProperty);
		//logic to check if property exists
		if (propertyModel.getLocations() == null)
		{
			Set<LocationModel> locations = new HashSet<>();
			propertyModel.setLocations(locations);
		}
		propertyModel.getLocations().add(location);

		return propertyRepository.save(propertyModel);
	}

	@RecordPropertyUpdate
	@Override
	public PropertyModel updateProperty(LocationModel location, RightMoveProperty rightMoveProperty) {

		final Optional<PropertyModel> propertyModelOptional = propertyRepository.findByPropertyId(rightMoveProperty.getId());
		if (propertyModelOptional.isPresent())
		{
			final PropertyModel propertyModel = propertyModelOptional.get();
			final Set<PropertyUpdateModel> propertyUpdates = propertyDeltaCheckerService.getPropertyUpdates(propertyModel, rightMoveProperty);
			LOG.info("The number of property updates [{}]", propertyUpdates.size());
			propertyModel.getPropertyUpdates().addAll(propertyUpdates);
			if (propertyGoneOffMarket(propertyUpdates)) {
				final Date today = new Date();
				propertyModel.setOffMarketDate(today);
				final Date firstVisibleDate = propertyModel.getFirstVisibleDate();
				final long daysOnMarket = TimeUnit.DAYS
						.convert((today.getTime() - firstVisibleDate.getTime()), TimeUnit.MILLISECONDS);
				propertyModel.setDaysOnMarket((int) daysOnMarket);
				propertyModel.setDisplayStatus(PropertyUpdateType.GONE_OFF_MARKET.getCode());
			} else if (propertyBackOnMarket(propertyUpdates)) {
				propertyModel.setDisplayStatus(getLatestDisplayStatus(propertyUpdates));
				propertyModel.setOffMarketDate(null);
				propertyModel.setDaysOnMarket(0);
			}
			return propertyModel;
		}
		return null;
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

	private boolean propertyBackOnMarket(Set<PropertyUpdateModel> propertyUpdates) {
		return propertyUpdates.stream().anyMatch(propertyUpdateModel -> Arrays
				.asList(PropertyUpdateType.BACK_ON_MARKET, PropertyUpdateType.GOING_OFF_MARKET)
				.contains(propertyUpdateModel.getPropertyUpdateType()));
	}

	private String getLatestDisplayStatus(Set<PropertyUpdateModel> propertyUpdates)
	{
		final Optional<PropertyUpdateModel> displayStatus = propertyUpdates.stream()
				.filter(propertyUpdateModel -> propertyUpdateModel.getField().equals("DisplayStatus")).findFirst();
		return displayStatus.isPresent() ? displayStatus.get().getNewValue() : "";

	}

	@Override
	public boolean isNewProperty(Long id) {
		return propertyRepository.findByPropertyId(id).isEmpty();
	}

	@Override
	public boolean hasPropertyChanged(RightMoveProperty rightMoveProperty) {
		final Optional<PropertyModel> existingPropertyOptional = propertyRepository.findByPropertyId(rightMoveProperty.getId());
		LOG.info("existingPropertyOptional is [{}] ", existingPropertyOptional.isPresent() ? "present" : "not present" );
		return existingPropertyOptional
				.filter(propertyModel -> propertyDeltaCheckerService.hasChangeUpdates(propertyModel, rightMoveProperty))
				.isPresent();
	}

	@Override
	public long getPropertiesAddedBetween(LocationDto locationDto, LocalDate startLocalDate, LocalDate endLocalDate) {
		Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		return propertyRepository
				.countByFirstVisibleDateGreaterThanAndFirstVisibleDateLessThanAndLocations_Id(startDate, endDate, locationDto.getId());
	}

	@Override
	public long getPropertiesSoldBetween(LocationDto locationDto, LocalDate startLocalDate, LocalDate endLocalDate) {
		Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		return propertyRepository
				.countByOffMarketDateGreaterThanAndFirstVisibleDateLessThanAndLocations_Id(startDate, endDate, locationDto.getId());
	}

	@Override
	public double getAverageDaysOnMarket(LocationDto locationDto, LocalDate startLocalDate, LocalDate endLocalDate) {
		Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		final Double avgDaysOnMarket = propertyRepository.avgDaysOnMarket(startDate, endDate);
		return avgDaysOnMarket != null ? avgDaysOnMarket : 0;
	}
}
