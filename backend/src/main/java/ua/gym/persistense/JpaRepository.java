package ua.gym.persistense;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class JpaRepository<Entity> {
   @PersistenceContext
   protected EntityManager entityManager;

   protected void add(Entity entity) {
      this.entityManager.persist(entity);
   }

   protected void delete(Entity entity) {
      this.entityManager.remove(entity);
   }
}
