package ua.gym.persistense;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public class Identifiable {
   @Id
   private String id = UUID.randomUUID().toString();

   public String getId() {
      return this.id;
   }
}
