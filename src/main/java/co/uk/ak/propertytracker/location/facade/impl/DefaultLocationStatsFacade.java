package co.uk.ak.propertytracker.location.facade.impl;

import co.uk.ak.propertytracker.location.dto.LocationDto;
import co.uk.ak.propertytracker.location.facade.LocationFacade;
import co.uk.ak.propertytracker.location.facade.LocationStatsFacade;
import co.uk.ak.propertytracker.location.model.LocationMarketStatsModel;
import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.location.repository.LocationRepository;
import co.uk.ak.propertytracker.location.repository.LocationMarketStatsRepository;
import co.uk.ak.propertytracker.properties.service.PropertyService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;

@Service
@AllArgsConstructor
public class DefaultLocationStatsFacade implements LocationStatsFacade {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultLocationStatsFacade.class);
	private final PropertyService propertyService;
	private final LocationFacade locationFacade;
	private final LocationMarketStatsRepository locationMarketStatsRepository;
	private final LocationRepository locationRepository;

	@Transactional
	@Override
	public void generateLocationStats() {

		final LocalDate localDate = LocalDate.now().minusMonths(1);
		final Month month = localDate.getMonth();
		final int year = localDate.getYear();

		final LocalDate startDate = YearMonth.of(year, month.getValue()).atDay(1);
		final LocalDate endOfMonth = YearMonth.of(year, month.getValue()).atEndOfMonth();
		LOG.info("The StartDate [{}] and endDate [{}]", startDate, endOfMonth);

		final List<LocationDto> allLocations = locationFacade.getAllLocations();
		allLocations.forEach(locationDto -> {

			final long newProperties = propertyService.getPropertiesAddedBetween(locationDto, startDate, endOfMonth);
			final long soldProperties = propertyService.getPropertiesSoldBetween(locationDto, startDate, endOfMonth);
			final double averageDaysOnMarket = propertyService.getAverageDaysOnMarket(locationDto, startDate, endOfMonth);
			LOG.info("Stats are [{}], [{}], [{}] ", newProperties, soldProperties, averageDaysOnMarket);

			final LocationModel locationModel = locationRepository.findByCode(locationDto.getCode());
			final String monthAndYear = String.format("%s-%s", month, year);
			final LocationMarketStatsModel statsForTheMonth = locationMarketStatsRepository
					.findAllByMonthYearAndLocation(monthAndYear, locationModel);

			if (statsForTheMonth != null)
			{
				LOG.info("The Schedule for [{}] already found, so updating it", monthAndYear);
				statsForTheMonth.setNumberOfNewProperties(newProperties);
				statsForTheMonth.setNumberOfSoldProperties(soldProperties);
				statsForTheMonth.setAverageAgeOnMarket(averageDaysOnMarket);
			}
			else
			{
				LOG.info("No Schedule for [{}] found, so creating new", monthAndYear);

				LocationMarketStatsModel locationMarketStatsModel = new LocationMarketStatsModel();
				locationMarketStatsModel.setMonthYear(monthAndYear);
				locationMarketStatsModel.setNumberOfNewProperties(newProperties);
				locationMarketStatsModel.setNumberOfSoldProperties(soldProperties);
				locationMarketStatsModel.setAverageAgeOnMarket(averageDaysOnMarket);
				locationMarketStatsModel.setLocation(locationModel);
				locationModel.getLocationMarketStats().add(locationMarketStatsModel);
				locationMarketStatsRepository.save(locationMarketStatsModel);
			}
		});
	}
}
