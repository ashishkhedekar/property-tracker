package co.uk.ak.propertytracker.location.facade;

import co.uk.ak.propertytracker.location.dto.LocationDto;

import java.util.List;

public interface LocationFacade {

	List<LocationDto> getAllLocations();

	LocationDto getLocationByCode(String code);

	LocationDto createLocation(LocationDto locationDto);
}
