package co.uk.ak.propertytracker.properties.mapper;

import co.uk.ak.propertytracker.properties.model.PropertyModel;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static co.uk.ak.propertytracker.properties.model.PropertyUpdateType.GONE_OFF_MARKET;

@Mapper(componentModel = "spring")
public interface RightMovePropertyToPropertyModelMapper
{
   @Mapping(target = "firstVisibleDate", source = "firstVisibleDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
   @Mapping(target = "propertyId", source = "id")
   PropertyModel rightMovePropertyToPropertyModel(RightMoveProperty rightMoveProperty);

   @AfterMapping
   default void setOffMarketDate(final RightMoveProperty rightMoveProperty, @MappingTarget final PropertyModel propertyModel)
   {
      if (rightMoveProperty.getDisplayStatus() != null && rightMoveProperty.getDisplayStatus().equals(GONE_OFF_MARKET.getCode()))
      {
         final Date today = new Date();
         propertyModel.setOffMarketDate(today);
         final Date firstVisibleDate = propertyModel.getFirstVisibleDate();
         final long daysOnMarket = TimeUnit.DAYS
                 .convert((today.getTime() - firstVisibleDate.getTime()), TimeUnit.MILLISECONDS);
         propertyModel.setDaysOnMarket((int) daysOnMarket);
      }

   }
}
