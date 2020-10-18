package ua.gym.ui.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import ua.gym.domain.ProductItem;

public class ProductItemDto {
   private String id;
   private BigDecimal price;
   private Integer tableNumber;
   private boolean paid;
   private LocalDateTime createDate;
   private ProductDto product;

   public ProductItemDto() {
   }

   public ProductItemDto(ProductItem productItem) {
      this.id = productItem.getId();
      this.price = productItem.getPrice();
      this.tableNumber = productItem.getTableNumber();
      this.paid = productItem.isPaid();
      this.createDate = productItem.getCreateDate();
      this.product = new ProductDto(productItem.getProduct());
   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public BigDecimal getPrice() {
      return this.price;
   }

   public void setPrice(BigDecimal price) {
      this.price = price;
   }

   public Integer getTableNumber() {
      return this.tableNumber;
   }

   public void setTableNumber(Integer tableNumber) {
      this.tableNumber = tableNumber;
   }

   public boolean isPaid() {
      return this.paid;
   }

   public void setPaid(boolean paid) {
      this.paid = paid;
   }

   public LocalDateTime getCreateDate() {
      return this.createDate;
   }

   public ProductDto getProduct() {
      return this.product;
   }

   public void setProduct(ProductDto product) {
      this.product = product;
   }
}
