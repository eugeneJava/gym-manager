package ua.gym.domain;

import ua.gym.persistense.Identifiable;
import ua.gym.utils.Assertions;
import ua.gym.utils.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Client extends Identifiable {
   private String phone;
   private String name;
   private String secondName;

   protected Client() {
   }

   public Client(String phone, String name, String secondName) {
      Assertions.assertPresent(phone, name);
      UAMobileNumberFormatValidator.assertUAMobileFormat(phone);
      StringUtils.cleanup(phone, name, secondName);
      this.phone = phone;
      this.name = name;
      this.secondName = secondName;
   }

   public String getPhone() {
      return this.phone;
   }

   public String getName() {
      return this.name;
   }

   public String getSecondName() {
      return this.secondName;
   }
}
