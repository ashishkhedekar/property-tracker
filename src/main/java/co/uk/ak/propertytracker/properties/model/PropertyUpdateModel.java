package co.uk.ak.propertytracker.properties.model;

import co.uk.ak.propertytracker.model.AbstractModel;

import javax.persistence.Entity;

@Entity
public class PropertyUpdateModel extends AbstractModel {

	private String field;
	private String oldValue;
	private String newValue;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
}
