package ua.gym.domain;

import ua.gym.persistense.Identifiable;
import ua.gym.utils.Assertions;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table
public class Product extends Identifiable {
   private String name;
   private String description;
   private BigDecimal price;
   private boolean active;
   private String faIcon;
   private int orderIndex;

   protected Product() {
   }

   public Product(String name, BigDecimal price, String faIcon, boolean active, int orderIndex) {
      Assertions.assertPresent(name, price, faIcon);
      this.name = name;
      this.setPrice(price);
      this.active = active;
      this.faIcon = faIcon;
      this.orderIndex = orderIndex;
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public BigDecimal getPrice() {
      return this.price;
   }

   public void setPrice(BigDecimal price) {
      Assertions.assertState(price.doubleValue() > 0.0D, "Price should be greater than 0");
      this.price = price;
   }

   public boolean isActive() {
      return this.active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public String getFaIcon() {
      return this.faIcon;
   }

   public void setFaIcon(String faIcon) {
      this.faIcon = faIcon;
   }

   public int getOrderIndex() {
      return this.orderIndex;
   }

   public void setOrderIndex(int orderIndex) {
      this.orderIndex = orderIndex;
   }
}
