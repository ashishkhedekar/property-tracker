package co.uk.ak.propertytracker.properties.model;

import co.uk.ak.propertytracker.location.model.LocationModel;
import co.uk.ak.propertytracker.model.AbstractModel;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "properties")
@Inheritance(strategy = InheritanceType.JOINED)
public class PropertyModel extends AbstractModel
{
   //Id
   private Long propertyId;

   //Attributes
   private Integer bedrooms;
   @Column(length=350)
   private String summary;
   private String propertySubType;
   //todo need mapping
   private String propertyType;

   // Price related information
   private Integer amount;
   private String displayPrice;

   //Display information
   private String displayStatus;
   private String displayAddress;
   private String mainMapImageSrc;
   private String propertyUrl;

   //Market information
   private String channel;
   private Date firstVisibleDate;
   @Column(nullable = true)
   private Boolean onMarket = true;
   private Date offMarketDate;
   private Integer daysOnMarket;
   private Date lastPropertyUpdateReceived;
   private Boolean registered;

   @ManyToMany(mappedBy = "properties", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
   private Set<LocationModel> locations = new HashSet<>();

   @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "property_id")
   private Set<PropertyUpdateModel> propertyUpdates = new HashSet<>();

//   @OneToMany(
//            mappedBy = "property",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//   )
//   private List<PropertyUpdateRecordModel> propertyUpdateRecords = new ArrayList<>();

   public Long getPropertyId()
   {
      return propertyId;
   }

   public void setPropertyId(Long propertyId)
   {
      this.propertyId = propertyId;
   }

   public Integer getBedrooms()
   {
      return bedrooms;
   }

   public void setBedrooms(Integer bedrooms)
   {
      this.bedrooms = bedrooms;
   }

   public String getSummary()
   {
      return summary;
   }

   public void setSummary(String summary)
   {
      this.summary = summary;
   }

   public String getPropertySubType()
   {
      return propertySubType;
   }

   public void setPropertySubType(String propertySubType)
   {
      this.propertySubType = propertySubType;
   }

   public String getPropertyType()
   {
      return propertyType;
   }

   public void setPropertyType(String propertyType)
   {
      this.propertyType = propertyType;
   }

   public Integer getAmount()
   {
      return amount;
   }

   public void setAmount(Integer amount)
   {
      this.amount = amount;
   }

   public String getDisplayPrice()
   {
      return displayPrice;
   }

   public void setDisplayPrice(String displayPrice)
   {
      this.displayPrice = displayPrice;
   }

   public String getDisplayStatus()
   {
      return displayStatus;
   }

   public void setDisplayStatus(String displayStatus)
   {
      this.displayStatus = displayStatus;
   }

   public String getDisplayAddress()
   {
      return displayAddress;
   }

   public void setDisplayAddress(String displayAddress)
   {
      this.displayAddress = displayAddress;
   }

   public String getMainMapImageSrc()
   {
      return mainMapImageSrc;
   }

   public void setMainMapImageSrc(String mainMapImageSrc)
   {
      this.mainMapImageSrc = mainMapImageSrc;
   }

   public String getPropertyUrl()
   {
      return propertyUrl;
   }

   public void setPropertyUrl(String propertyUrl)
   {
      this.propertyUrl = propertyUrl;
   }

   public String getChannel()
   {
      return channel;
   }

   public void setChannel(String channel)
   {
      this.channel = channel;
   }

   public Date getFirstVisibleDate()
   {
      return firstVisibleDate;
   }

   public void setFirstVisibleDate(Date firstVisibleDate)
   {
      this.firstVisibleDate = firstVisibleDate;
   }

   public Date getOffMarketDate()
   {
      return offMarketDate;
   }

   public void setOffMarketDate(Date offMarketDate)
   {
      this.offMarketDate = offMarketDate;
   }

   public Integer getDaysOnMarket()
   {
      return daysOnMarket;
   }

   public void setDaysOnMarket(Integer daysOnMarket)
   {
      this.daysOnMarket = daysOnMarket;
   }

//   public List<PropertyUpdateRecordModel> getPropertyUpdateRecords()
//   {
//      return propertyUpdateRecords;
//   }
//
//   public void setPropertyUpdateRecords(List<PropertyUpdateRecordModel> propertyUpdateRecords)
//   {
//      this.propertyUpdateRecords = propertyUpdateRecords;
//   }

   public Boolean isOnMarket()
   {
      return onMarket;
   }

   public void setOnMarket(Boolean onMarket)
   {
      this.onMarket = onMarket;
   }

   public Date getLastPropertyUpdateReceived()
   {
      return lastPropertyUpdateReceived;
   }

   public void setLastPropertyUpdateReceived(Date lastPropertyUpdateReceived)
   {
      this.lastPropertyUpdateReceived = lastPropertyUpdateReceived;
   }

   public Boolean getRegistered()
   {
      return registered;
   }

   public void setRegistered(Boolean registered)
   {
      this.registered = registered;
   }

   public Set<LocationModel> getLocations() {
      return locations;
   }

   public void setLocations(Set<LocationModel> locations) {
      this.locations = locations;
   }

   public Set<PropertyUpdateModel> getPropertyUpdates() {
      return propertyUpdates;
   }

   public void setPropertyUpdates(Set<PropertyUpdateModel> propertyUpdates) {
      this.propertyUpdates = propertyUpdates;
   }
}
