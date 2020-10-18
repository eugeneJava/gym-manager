package ua.gym.ui;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.gym.domain.NameRepository;

@RestController
public class NameWebService {
   private final NameRepository nameRepository;

   @Autowired
   public NameWebService(NameRepository nameRepository) {
      this.nameRepository = nameRepository;
   }

   @GetMapping({"names"})
   @Transactional(
      readOnly = true
   )
   public List<String> searchNames(@RequestParam String term) {
      List<String> names = this.nameRepository.search(term);
      return names;
   }
}
