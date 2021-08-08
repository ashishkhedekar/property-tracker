package co.uk.ak.propertytracker.rightmove.repository;

import co.uk.ak.propertytracker.rightmove.model.RightMovePropertyUpdateModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RightMovePropertyUpdateRepository extends CrudRepository<RightMovePropertyUpdateModel, Long>  {

	List<RightMovePropertyUpdateModel> findAllByPropertyId(Long id);

	List<RightMovePropertyUpdateModel> findAllByOrderByCreationTimeDesc();
}
