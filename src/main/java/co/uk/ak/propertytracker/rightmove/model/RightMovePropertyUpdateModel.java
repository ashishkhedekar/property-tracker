package co.uk.ak.propertytracker.rightmove.model;

import co.uk.ak.propertytracker.model.AbstractUpdateModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "rightMovePropertyUpdates")
public class RightMovePropertyUpdateModel extends AbstractUpdateModel {

	private String locationCode;
	private Long propertyId;

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public Long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}

}
