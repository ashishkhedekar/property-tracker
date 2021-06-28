package co.uk.ak.propertytracker.properties.repository;

import co.uk.ak.propertytracker.properties.model.PropertyModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public interface PropertyRepository extends CrudRepository<PropertyModel, Long>  {
	Optional<PropertyModel> findByPropertyId(Long id);
	long countByFirstVisibleDateGreaterThanAndFirstVisibleDateLessThanAndLocations_Id(Date startDate, Date endDate, long locationId);
	long countByOffMarketDateGreaterThanAndFirstVisibleDateLessThanAndLocations_Id(Date startDate, Date endDate, long locationId);

	@Query(value = "SELECT avg(p.daysOnMarket) FROM PropertyModel p WHERE p.firstVisibleDate >= :startDate AND p.firstVisibleDate <= :endDate")
	Double avgDaysOnMarket(Date startDate, Date endDate);
}
