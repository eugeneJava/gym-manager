package ua.gym.domain;

import org.springframework.stereotype.Repository;
import ua.gym.persistense.JpaRepository;

@Repository
public class ProductDeleteLogRepository extends JpaRepository<ProductDeleteLog> {
   public void add(ProductDeleteLog productDeleteLog) {
      super.add(productDeleteLog);
   }
}
