package co.uk.ak.propertytracker.location.model;

import co.uk.ak.propertytracker.properties.model.PropertyModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations", uniqueConstraints={@UniqueConstraint(columnNames={"code"})})
public class LocationModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

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
}
