package co.uk.ak.propertytracker.properties.mapper;

import co.uk.ak.propertytracker.properties.model.PropertyModel;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RightMovePropertyToPropertModelMapper
{
   @Mapping(target = "firstVisibleDate", source = "firstVisibleDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
   @Mapping(target = "propertyId", source = "id")
   PropertyModel rightMovePropertyToPropertyModel(RightMoveProperty rightMoveProperty);
}
