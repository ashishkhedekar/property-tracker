package co.uk.ak.propertytracker.location.model;

import co.uk.ak.propertytracker.model.AbstractUpdateModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "location_updates")
public class LocationUpdateModel extends AbstractUpdateModel {

	private String locationCode;

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationId) {
		this.locationCode = locationId;
	}
}
