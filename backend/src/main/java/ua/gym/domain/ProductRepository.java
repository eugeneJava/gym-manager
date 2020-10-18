package ua.gym.domain;

import org.springframework.stereotype.Repository;
import ua.gym.persistense.JpaRepository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ProductRepository extends JpaRepository<Product> {
   public Product getById(String id) {
      return this.entityManager.find(Product.class, id);
   }

   public void add(Product product) {
      super.add(product);
   }

   public List<Product> getProducts() {
      TypedQuery<Product> query = this.entityManager.createQuery("SELECT product FROM Product product WHERE product.active = true ORDER BY product.orderIndex", Product.class);
      return query.getResultList();
   }
}
