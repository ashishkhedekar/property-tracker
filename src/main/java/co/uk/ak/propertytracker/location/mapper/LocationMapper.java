package co.uk.ak.propertytracker.location.mapper;

import co.uk.ak.propertytracker.location.dto.LocationDto;
import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.properties.model.PropertyModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.util.CollectionUtils;

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
			final long soldProperties = properties.stream()
					.filter(e -> e.getDisplayStatus() != null && e.getDisplayStatus().equals("Sold STC")).count();
			locationDto.setNumberOfSoldProperties(soldProperties);
		}
	}

	@AfterMapping
	default void calculateNumberOfProperties(final LocationModel locationModel, @MappingTarget final LocationDto locationDto)
	{
		locationDto.setNumberOfProperties(locationModel.getProperties().size());
	}
}
