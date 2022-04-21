package co.uk.ak.propertytracker.home.controller;

import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.location.service.LocationService;
import co.uk.ak.propertytracker.properties.facade.PropertiesFacade;
import co.uk.ak.propertytracker.properties.repository.PropertyRepository;
import co.uk.ak.propertytracker.properties.repository.PropertyUpdateRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final LocationService locationService;
    private final PropertyRepository propertyRepository;
    private final PropertyUpdateRepository propertyUpdateRepository;

    @GetMapping
    public ResponseEntity<?> homePage()
    {
        final List<LocationModel> allLocations = locationService.getAllLocations();
        long propertiesCount = propertyRepository.count();
        long propertyUpdatesCount = propertyUpdateRepository.count();
        return ResponseEntity.status(HttpStatus.OK).body(String.format("There are %d locations and %d properties and %d property updates", allLocations.size(), propertiesCount, propertyUpdatesCount));
    }

}