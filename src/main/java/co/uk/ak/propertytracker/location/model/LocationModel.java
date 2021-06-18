package co.uk.ak.propertytracker.location.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"code"})})
public class LocationModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String code;
	private String name;
	private String description;
	private String mainImage;
	private String thumbnail;
}
