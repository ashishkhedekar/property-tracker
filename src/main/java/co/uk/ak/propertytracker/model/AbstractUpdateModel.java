package co.uk.ak.propertytracker.model;

import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractUpdateModel extends AbstractModel {
	@Lob
	private String json;

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
}
