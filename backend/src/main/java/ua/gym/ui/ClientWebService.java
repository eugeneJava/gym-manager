package ua.gym.ui;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.gym.domain.Client;
import ua.gym.domain.ClientRepository;
import ua.gym.ui.dtos.ClientDto;

@RestController
public class ClientWebService {
   private final ClientRepository clientRepository;

   @Autowired
   public ClientWebService(ClientRepository clientRepository) {
      this.clientRepository = clientRepository;
   }

   @GetMapping({"clients"})
   @Transactional(
      readOnly = true
   )
   public List<ClientDto> searchClients(@RequestParam String term) {
      List<Client> clients = this.clientRepository.search(term);
      return (List)clients.stream().map(ClientDto::new).collect(Collectors.toList());
   }

   @GetMapping({"clients/{phone}"})
   @Transactional(
      readOnly = true
   )
   public boolean getClientByPhone(@PathVariable String phone) {
      boolean exists = (Boolean)this.clientRepository.findByPhone(phone).map((client) -> {
         return true;
      }).orElse(false);
      return exists;
   }

   @PostMapping({"clients"})
   @Transactional
   public ClientDto addClient(@RequestBody ClientDto clientDto) {
      Client client = new Client(clientDto.getPhone(), clientDto.getName(), clientDto.getSecondName());
      this.clientRepository.add(client);
      return new ClientDto(client);
   }
}
