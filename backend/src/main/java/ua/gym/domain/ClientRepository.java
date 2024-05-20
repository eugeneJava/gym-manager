package ua.gym.domain;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ua.gym.persistense.JpaRepository;
import ua.gym.utils.StringUtils;

@Repository
public class ClientRepository extends JpaRepository<Client> {
   public List<Client> search(String term) {
      if (StringUtils.isBlank(term)) {
         return Collections.emptyList();
      } else {
         TypedQuery<Client> query = this.entityManager.createQuery("select c from Client c where c.phone like :term  or c.name like :term  or c.secondName like :term ", Client.class);
         query.setParameter("term", "%" + term + "%");
         return query.getResultList();
      }
   }

   public Optional<Client> findByPhone(String phone) {
      TypedQuery<Client> query = this.entityManager.createQuery("select c from Client c where c.phone = :phone ", Client.class);
      query.setParameter("phone", phone);
      return query.getResultStream().findFirst();
   }

   public void add(Client client) {
      super.add(client);
   }
}
