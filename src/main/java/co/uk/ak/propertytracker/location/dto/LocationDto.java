package co.uk.ak.propertytracker.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {

	private String code;
	private String name;
	private String description;
	private String mainImage;
	private String thumbnail;

	private long numberOfProperties;
	private long numberOfSoldInLast24Hours;
	private long numberOfSoldInLast30Days;
	private long numberOfNewInLast24Hours;
	private long numberOfNewInLast30Days;


}
