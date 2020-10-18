package ua.gym.domain;

import ua.gym.persistense.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table
public class ProductDeleteLog extends Identifiable {
   @Column(
      nullable = false
   )
   private String productName;
   @Column(
      nullable = false
   )
   private BigDecimal price;
   @Column
   private Integer tableNumber;
   @Column(
      nullable = false
   )
   private boolean paid;
   @Column(
      nullable = false
   )
   private LocalDateTime createDate;

   protected ProductDeleteLog() {
   }

   public ProductDeleteLog(ProductItem productItem) {
      this.productName = productItem.getProduct().getName();
      this.price = productItem.getPrice();
      this.tableNumber = productItem.getTableNumber();
      this.paid = productItem.isPaid();
      this.createDate = LocalDateTime.now();
   }

   public BigDecimal getPrice() {
      return this.price;
   }

   public Integer getTableNumber() {
      return this.tableNumber;
   }

   public boolean isPaid() {
      return this.paid;
   }

   public LocalDateTime getCreateDate() {
      return this.createDate;
   }
}
