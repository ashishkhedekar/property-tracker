package co.uk.ak.propertytracker.location.mapper;

import co.uk.ak.propertytracker.location.dto.LocationDto;
import co.uk.ak.propertytracker.location.model.LocationModel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-17T16:00:25+0100",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class LocationMapperImpl implements LocationMapper {

    @Override
    public LocationModel locationDtoToLocationModel(LocationDto locationDto) {
        if ( locationDto == null ) {
            return null;
        }

        LocationModel locationModel = new LocationModel();

        locationModel.setId( locationDto.getId() );
        locationModel.setCode( locationDto.getCode() );
        locationModel.setName( locationDto.getName() );
        locationModel.setDescription( locationDto.getDescription() );
        locationModel.setMainImage( locationDto.getMainImage() );
        locationModel.setThumbnail( locationDto.getThumbnail() );

        return locationModel;
    }

    @Override
    public LocationDto locationModelToLocationDto(LocationModel locationModel) {
        if ( locationModel == null ) {
            return null;
        }

        LocationDto locationDto = new LocationDto();

        if ( locationModel.getId() != null ) {
            locationDto.setId( locationModel.getId() );
        }
        locationDto.setCode( locationModel.getCode() );
        locationDto.setName( locationModel.getName() );
        locationDto.setDescription( locationModel.getDescription() );
        locationDto.setMainImage( locationModel.getMainImage() );
        locationDto.setThumbnail( locationModel.getThumbnail() );

        calculateNumberOfSoldProperties( locationModel, locationDto );
        calculateNumberOfProperties( locationModel, locationDto );

        return locationDto;
    }
}
