package co.uk.ak.propertytracker.model;

import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class AbstractModel
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @Temporal(TemporalType.TIMESTAMP)
   private Date creationTime = DateTime.now().toDate();
   @Temporal(TemporalType.TIMESTAMP)
   private Date modificationTime = DateTime.now().toDate();

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public Date getCreationTime()
   {
      return creationTime;
   }

   public void setCreationTime(Date creationTime)
   {
      this.creationTime = creationTime;
   }

   public Date getModificationTime()
   {
      return modificationTime;
   }

   public void setModificationTime(Date modificationTime)
   {
      this.modificationTime = modificationTime;
   }
}
