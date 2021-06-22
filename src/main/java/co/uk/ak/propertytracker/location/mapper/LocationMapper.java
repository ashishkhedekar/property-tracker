package co.uk.ak.propertytracker.location.mapper;

import co.uk.ak.propertytracker.location.dto.LocationDto;
import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.properties.model.PropertyModel;
import org.joda.time.DateTime;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface LocationMapper {

	LocationModel locationDtoToLocationModel(final LocationDto locationDto);
	LocationDto locationModelToLocationDto(final LocationModel locationModel);

	@AfterMapping
	default void calculateNumberOfSoldProperties(final LocationModel locationModel, @MappingTarget final LocationDto locationDto)
	{
		final Set<PropertyModel> properties = locationModel.getProperties();
		if (!CollectionUtils.isEmpty(properties))
		{
			final long soldPropertiesIn24Hours = getSoldProperties(properties, DateTime.now().minusDays(1).toDate());
			locationDto.setNumberOfSoldInLast24Hours(soldPropertiesIn24Hours);
			final long soldPropertiesIn30Days = getSoldProperties(properties, DateTime.now().minusDays(30).toDate());
			locationDto.setNumberOfSoldInLast30Days(soldPropertiesIn30Days);
			final long newPropertiesIn24Hours = getNewProperties(properties, DateTime.now().minusDays(1).toDate());
			locationDto.setNumberOfNewInLast24Hours(newPropertiesIn24Hours);
			final long newPropertiesIn30Days = getNewProperties(properties, DateTime.now().minusDays(30).toDate());
			locationDto.setNumberOfNewInLast30Days(newPropertiesIn30Days);
		}
	}

	private long getSoldProperties(Set<PropertyModel> properties, Date cutOff) {
		return properties.stream()
				.filter(e -> e.getDisplayStatus() != null
						&& e.getDisplayStatus().equals("Sold STC")
						&& e.getOffMarketDate().after(cutOff)).count();
	}

	private long getNewProperties(Set<PropertyModel> properties, Date cutOff) {
		return properties.stream()
				.filter(e -> e.getDisplayStatus() != null
						&& e.getFirstVisibleDate().after(cutOff)).count();
	}

	@AfterMapping
	default void calculateNumberOfProperties(final LocationModel locationModel, @MappingTarget final LocationDto locationDto)
	{
		locationDto.setNumberOfProperties(locationModel.getProperties().size());
	}
}
