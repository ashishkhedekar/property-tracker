package co.uk.ak.propertytracker.location.model;

import co.uk.ak.propertytracker.model.AbstractModel;

import javax.persistence.*;

@Entity
@Table(name = "location_market_stats", uniqueConstraints={
		@UniqueConstraint(columnNames = {"monthYear", "location_id"})
})
public class LocationMarketStatsModel extends AbstractModel {

	private String monthYear;
	private long numberOfNewProperties;
	private long numberOfSoldProperties;
	private double averageAgeOnMarket;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private LocationModel location;


	public String getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}

	public long getNumberOfNewProperties() {
		return numberOfNewProperties;
	}

	public void setNumberOfNewProperties(long numberOfNewProperties) {
		this.numberOfNewProperties = numberOfNewProperties;
	}

	public long getNumberOfSoldProperties() {
		return numberOfSoldProperties;
	}

	public void setNumberOfSoldProperties(long numberOfSoldProperties) {
		this.numberOfSoldProperties = numberOfSoldProperties;
	}

	public double getAverageAgeOnMarket() {
		return averageAgeOnMarket;
	}

	public void setAverageAgeOnMarket(double averageAgeOnMarket) {
		this.averageAgeOnMarket = averageAgeOnMarket;
	}

	public LocationModel getLocation() {
		return location;
	}

	public void setLocation(LocationModel locationModel) {
		this.location = locationModel;
	}
}
