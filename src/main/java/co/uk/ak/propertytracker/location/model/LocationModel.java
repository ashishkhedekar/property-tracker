package co.uk.ak.propertytracker.location.model;

import co.uk.ak.propertytracker.model.AbstractModel;
import co.uk.ak.propertytracker.properties.model.PropertyModel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations", uniqueConstraints={@UniqueConstraint(columnNames={"code"})})
public class LocationModel extends AbstractModel {

	private String code;
	private String name;
	private String description;
	private String mainImage;
	private String thumbnail;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "locations_properties",
			joinColumns = {
					@JoinColumn(name = "location_id", referencedColumnName = "id",
							nullable = false, updatable = false)},
			inverseJoinColumns = {
					@JoinColumn(name = "property_id", referencedColumnName = "id",
							nullable = false, updatable = false)})
	private Set<PropertyModel> properties = new HashSet<>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Set<PropertyModel> getProperties() {
		return properties;
	}

	public void setProperties(Set<PropertyModel> properties) {
		this.properties = properties;
	}
}
