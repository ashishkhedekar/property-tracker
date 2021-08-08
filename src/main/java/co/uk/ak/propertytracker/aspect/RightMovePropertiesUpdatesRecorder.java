package co.uk.ak.propertytracker.aspect;

import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import co.uk.ak.propertytracker.rightmove.service.RightMovePropertyUpdatesService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Aspect
@Configuration
@Service
@AllArgsConstructor
public class RightMovePropertiesUpdatesRecorder {

	private static final Logger LOG = LoggerFactory.getLogger(RightMovePropertiesUpdatesRecorder.class);
	private final RightMovePropertyUpdatesService rightMovePropertyUpdatesService;

	@Before("@annotation(co.uk.ak.propertytracker.aspect.RecordPropertyUpdate)")
	public Object saveRightMoveProperty(final JoinPoint joinPoint) throws IOException {
		final String methodName = joinPoint.getSignature().getName();
		final LocationModel locationModel = (LocationModel) joinPoint.getArgs()[0];
		final RightMoveProperty rightMoveProperty = (RightMoveProperty) joinPoint.getArgs()[1];
		LOG.info("The method name is [{}]",methodName);
		LOG.info("The isLoadMethod [{}]",isLoadMethod(joinPoint));
		LOG.info("The propertyUpdateExists [{}]",rightMovePropertyUpdatesService.propertyUpdateExists(rightMoveProperty));
		if (!isLoadMethod(joinPoint) && (!methodName.equals("logProperty") || !rightMovePropertyUpdatesService.propertyUpdateExists(rightMoveProperty)))
		{
			LOG.info("Recording new update for property [{}] ", rightMoveProperty.getId());
			rightMovePropertyUpdatesService.recordPropertyUpdate(locationModel, rightMoveProperty);
		}
		return null;
	}

	private boolean isLoadMethod(JoinPoint joinPoint) {
		if (joinPoint.getArgs().length == 3)
		{
			return (boolean) joinPoint.getArgs()[2];
		}
		return false;
	}
}
