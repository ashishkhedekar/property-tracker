package co.uk.ak.propertytracker.location.service;

import co.uk.ak.propertytracker.location.model.LocationModel;

import java.util.List;

public interface LocationService {

	List<LocationModel> getAllLocations();
	LocationModel getLocation(final String code);
	LocationModel createLocation(LocationModel locationDto);
}
