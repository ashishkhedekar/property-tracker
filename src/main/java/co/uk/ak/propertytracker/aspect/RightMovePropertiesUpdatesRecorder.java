package co.uk.ak.propertytracker.aspect;

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
		final RightMoveProperty rightMoveProperty = (RightMoveProperty) joinPoint.getArgs()[1];
		if (!methodName.equals("logProperty") || !rightMovePropertyUpdatesService.propertyUpdateExists(rightMoveProperty))
		{
			LOG.info("Recording new update for property [{}] ", rightMoveProperty.getId());
			rightMovePropertyUpdatesService.recordPropertyUpdate(rightMoveProperty);
		}
		return null;
	}
}
