package ua.gym.ui.dtos;

import ua.gym.domain.Client;

public class ClientDto {
   private String phone;
   private String name;
   private String secondName;

   public ClientDto() {
   }

   public ClientDto(Client client) {
      this.phone = client.getPhone();
      this.name = client.getName();
      this.secondName = client.getSecondName();
   }

   public String getPhone() {
      return this.phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getSecondName() {
      return this.secondName;
   }

   public void setSecondName(String secondName) {
      this.secondName = secondName;
   }
}
