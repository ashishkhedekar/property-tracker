package co.uk.ak.propertytracker.location.controller;

import co.uk.ak.propertytracker.location.dto.LocationDto;
import co.uk.ak.propertytracker.location.dto.LocationsDto;
import co.uk.ak.propertytracker.location.facade.LocationFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/location")
public class LocationController {

	private final LocationFacade locationFacade;

	//todo: price range
	@GetMapping
	public ResponseEntity<?> getAllLocations(@RequestParam(required = false, defaultValue = "0") final int minBed, @RequestParam(required = false, defaultValue = "100") final int maxBed)
	{
		final LocationsDto locationsDto = new LocationsDto();
		locationsDto.setLocations(locationFacade.getAllLocations(minBed, maxBed));
		return ResponseEntity.status(HttpStatus.OK).body(locationsDto);
	}

	@GetMapping(value = "/code/{code}")
	public ResponseEntity<?> getLocation(@PathVariable final String code)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(locationFacade.getLocationByCode(code));
	}

	@PostMapping
	public ResponseEntity<?> createLocation(@RequestBody final LocationDto locationDto)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(locationFacade.createLocation(locationDto));
	}
}
