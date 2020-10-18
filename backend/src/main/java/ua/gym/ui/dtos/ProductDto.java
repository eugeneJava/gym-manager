package ua.gym.ui.dtos;

import ua.gym.domain.Product;

import java.math.BigDecimal;

public class ProductDto {
   private String id;
   private String name;
   private String description;
   private BigDecimal price;
   private boolean active;
   private String faIcon;

   ProductDto() {
   }

   public ProductDto(Product product) {
      this.id = product.getId();
      this.name = product.getName();
      this.description = product.getDescription();
      this.price = product.getPrice();
      this.faIcon = product.getFaIcon();
      this.active = product.isActive();
   }

   public String getId() {
      return this.id;
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

   public boolean isActive() {
      return this.active;
   }

   public String getFaIcon() {
      return this.faIcon;
   }
}
