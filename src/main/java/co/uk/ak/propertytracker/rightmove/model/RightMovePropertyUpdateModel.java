package co.uk.ak.propertytracker.rightmove.model;

import co.uk.ak.propertytracker.model.AbstractModel;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "rightMovePropertyUpdates")
public class RightMovePropertyUpdateModel extends AbstractModel {

	private Long propertyId;
	@Lob
	private String json;

	public Long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
}
