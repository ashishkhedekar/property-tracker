package co.uk.ak.propertytracker.location.facade.impl;

import co.uk.ak.propertytracker.aspect.RecordLocationUpdates;
import co.uk.ak.propertytracker.location.dto.LocationDto;
import co.uk.ak.propertytracker.location.facade.LocationFacade;
import co.uk.ak.propertytracker.location.mapper.LocationMapper;
import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.location.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultLocationFacade implements LocationFacade {

	private final LocationService locationService;
	private final LocationMapper locationMapper;

	@Override
	public List<LocationDto> getAllLocations() {
		return locationService.getAllLocations()
				.stream()
				.map(locationMapper::locationModelToLocationDto)
				.collect(Collectors.toList());
	}

	@Override
	public LocationDto getLocationByCode(String code) {
		return locationMapper.locationModelToLocationDto(locationService.getLocation(code));
	}

	@Override
	@RecordLocationUpdates
	public LocationDto createLocation(LocationDto locationDto) {

		final LocationModel locationModel = locationMapper.locationDtoToLocationModel(locationDto);
		final LocationModel location = locationService.createLocation(locationModel);
		return locationMapper.locationModelToLocationDto(location);
	}
}
