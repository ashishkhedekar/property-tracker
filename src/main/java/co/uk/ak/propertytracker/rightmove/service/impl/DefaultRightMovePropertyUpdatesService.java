package co.uk.ak.propertytracker.rightmove.service.impl;

import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import co.uk.ak.propertytracker.rightmove.model.RightMovePropertyUpdateModel;
import co.uk.ak.propertytracker.rightmove.repository.RightMovePropertyUpdateRepository;
import co.uk.ak.propertytracker.rightmove.service.RightMovePropertyUpdatesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class DefaultRightMovePropertyUpdatesService implements RightMovePropertyUpdatesService {

	private final RightMovePropertyUpdateRepository rightMovePropertyUpdateRepository;
	private final ObjectMapper objectMapper;

	@Override
	public boolean propertyUpdateExists(RightMoveProperty rightMoveProperty) {

		final List<RightMovePropertyUpdateModel> allByPropertyId = rightMovePropertyUpdateRepository.findAllByPropertyId(rightMoveProperty.getId());
		return !CollectionUtils.isEmpty(allByPropertyId);
	}

	@Transactional
	@Override
	public void recordPropertyUpdate(LocationModel locationModel, RightMoveProperty rightMoveProperty) throws JsonProcessingException {

		RightMovePropertyUpdateModel updateModel = new RightMovePropertyUpdateModel();
		updateModel.setLocationCode(locationModel.getCode());
		updateModel.setJson(objectMapper.writeValueAsString(rightMoveProperty));
		updateModel.setPropertyId(rightMoveProperty.getId());
		locationModel.getRightMovePropertyUpdates().add(updateModel);
		rightMovePropertyUpdateRepository.save(updateModel);

	}
}
