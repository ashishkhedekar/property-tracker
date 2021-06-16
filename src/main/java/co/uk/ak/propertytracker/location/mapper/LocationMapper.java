package co.uk.ak.propertytracker.location.mapper;

import co.uk.ak.propertytracker.location.dto.LocationDto;
import co.uk.ak.propertytracker.location.model.LocationModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {

	LocationModel locationDtoToLocationModel(final LocationDto locationDto);
	LocationDto locationModelToLocationDto(final LocationModel locationModel);
}
