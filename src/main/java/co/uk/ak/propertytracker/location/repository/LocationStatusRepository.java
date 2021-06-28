package co.uk.ak.propertytracker.location.repository;

import co.uk.ak.propertytracker.location.model.LocationMarketStatsModel;
import co.uk.ak.propertytracker.location.model.LocationModel;
import org.springframework.data.repository.CrudRepository;

public interface LocationStatusRepository extends CrudRepository<LocationMarketStatsModel, Long> {

	LocationMarketStatsModel findAllByMonthYearAndLocation(String monthYear, LocationModel locationModel);

}
