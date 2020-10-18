package ua.gym.persistense;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
