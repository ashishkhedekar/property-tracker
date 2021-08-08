package co.uk.ak.propertytracker.aspect;

import co.uk.ak.propertytracker.location.dto.LocationDto;
import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.location.service.LocationUpdateService;
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
public class LocationUpdatesRecorder {

	private static final Logger LOG = LoggerFactory.getLogger(LocationUpdatesRecorder.class);
	private final RightMovePropertyUpdatesService rightMovePropertyUpdatesService;
	private final LocationUpdateService locationUpdateService;

	@Before("@annotation(co.uk.ak.propertytracker.aspect.RecordLocationUpdates)")
	public Object saveLocationUpdate(final JoinPoint joinPoint) throws IOException {
		final String methodName = joinPoint.getSignature().getName();
		final LocationDto locationDto = (LocationDto) joinPoint.getArgs()[0];
		locationUpdateService.recordLocationUpdates(locationDto);
		return null;
	}
}
