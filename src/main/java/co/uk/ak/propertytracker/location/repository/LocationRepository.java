package co.uk.ak.propertytracker.location.repository;

import co.uk.ak.propertytracker.location.model.LocationModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<LocationModel, Long>  {
	LocationModel findByCode(String code);
	List<LocationModel> findByProperties_bedroomsGreaterThan(int minBed);
}
