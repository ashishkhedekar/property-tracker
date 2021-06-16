package co.uk.ak.propertytracker.location.repository;

import co.uk.ak.propertytracker.location.model.LocationModel;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<LocationModel, Long>  {
	LocationModel findByCode(String code);
}
