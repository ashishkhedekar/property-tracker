package co.uk.ak.propertytracker.properties;

import co.uk.ak.propertytracker.properties.model.PropertyModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PropertyRepository extends CrudRepository<PropertyModel, Long>  {
	Optional<PropertyModel> findByPropertyId(Long id);
}
