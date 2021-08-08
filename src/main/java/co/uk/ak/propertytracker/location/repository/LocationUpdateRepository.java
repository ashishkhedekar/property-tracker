package co.uk.ak.propertytracker.location.repository;

import co.uk.ak.propertytracker.location.model.LocationUpdateModel;
import org.springframework.data.repository.CrudRepository;

public interface LocationUpdateRepository extends CrudRepository<LocationUpdateModel, Long> {
}
