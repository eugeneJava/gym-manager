package ua.gym.domain;

import org.springframework.stereotype.Repository;
import ua.gym.persistense.JpaRepository;
import ua.gym.utils.StringUtils;

import jakarta.persistence.Query;
import java.util.Collections;
import java.util.List;

@Repository
public class NameRepository extends JpaRepository<String> {
   public List<String> search(String namePart) {
      if (StringUtils.isBlank(namePart)) {
         return Collections.emptyList();
      } else {
         Query query = this.entityManager.createNativeQuery("SELECT `name` FROM Name  WHERE `name` LIKE :namePart ORDER BY `name` ");
         query.setParameter("namePart", "%" + namePart + "%");
         List<String> resultList = query.getResultList();
         return resultList;
      }
   }
}
