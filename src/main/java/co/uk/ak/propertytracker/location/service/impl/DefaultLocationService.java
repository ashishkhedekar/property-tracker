package co.uk.ak.propertytracker.location.service.impl;

import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.location.repository.LocationRepository;
import co.uk.ak.propertytracker.location.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class DefaultLocationService implements LocationService {

	private final LocationRepository locationRepository;

	@Override
	public List<LocationModel> getAllLocations() {
		return StreamSupport
				.stream(locationRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public List<LocationModel> getLocations(int minBed, int maxbed) {
		return StreamSupport
				.stream(locationRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public LocationModel getLocation(final String code) {
		return locationRepository.findByCode(code);
	}

	@Override
	public LocationModel createLocation(LocationModel locationModel) {
		final LocationModel existingCode = locationRepository.findByCode(locationModel.getCode());
		if (existingCode != null)
		{
			locationModel.setId(existingCode.getId());
		}
		return locationRepository.save(locationModel);
	}
}
