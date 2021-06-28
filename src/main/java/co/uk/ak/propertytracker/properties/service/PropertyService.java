package co.uk.ak.propertytracker.properties.service;

import co.uk.ak.propertytracker.location.dto.LocationDto;
import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.properties.model.PropertyModel;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;

import java.time.LocalDate;

public interface PropertyService {

	PropertyModel saveProperty(LocationModel location, RightMoveProperty rightMoveProperty);
	PropertyModel updateProperty(LocationModel location, RightMoveProperty rightMoveProperty);
	PropertyModel logProperty(LocationModel location, RightMoveProperty rightMoveProperty);
	boolean isNewProperty(Long id);
	boolean hasPropertyChanged(RightMoveProperty id);
	long getPropertiesAddedBetween(LocationDto locationDto, LocalDate startDate, LocalDate endOfMonth);
	long getPropertiesSoldBetween(LocationDto locationDto, LocalDate startDate, LocalDate endDate);
	double getAverageDaysOnMarket(LocationDto locationDto, LocalDate startDate, LocalDate endOfMonth);
}
