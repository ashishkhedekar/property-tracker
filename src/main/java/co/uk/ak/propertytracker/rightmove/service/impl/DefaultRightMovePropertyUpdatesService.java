package co.uk.ak.propertytracker.rightmove.service.impl;

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
	public void recordPropertyUpdate(RightMoveProperty rightMoveProperty) throws JsonProcessingException {

		RightMovePropertyUpdateModel updateModel = new RightMovePropertyUpdateModel();
		updateModel.setJson(objectMapper.writeValueAsString(rightMoveProperty));
		updateModel.setPropertyId(rightMoveProperty.getId());
		rightMovePropertyUpdateRepository.save(updateModel);

	}
}
