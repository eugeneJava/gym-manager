package ua.gym.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ua.gym.persistense.JpaRepository;

@Repository
public class ProductItemRepository extends JpaRepository<ProductItem> {
   public ProductItem getById(String id) {
      return (ProductItem)this.entityManager.find(ProductItem.class, id);
   }

   public void add(ProductItem productItem) {
      super.add(productItem);
   }

   public void delete(ProductItem productItem) {
      super.delete(productItem);
   }

   public List<ProductItem> getProductsForCurrentDay() {
      LocalDate now = LocalDate.now();
      LocalDateTime startOfDay = LocalDateTime.of(now, LocalTime.MIDNIGHT);
      LocalDateTime endOfDay = LocalDateTime.of(now, LocalTime.MAX);
      TypedQuery<ProductItem> query = this.entityManager.createQuery("SELECT product FROM ProductItem product WHERE product.createDate >= :startOfDay AND product.createDate <= :endOfDay", ProductItem.class);
      query.setParameter("startOfDay", startOfDay);
      query.setParameter("endOfDay", endOfDay);
      return query.getResultList();
   }
}
