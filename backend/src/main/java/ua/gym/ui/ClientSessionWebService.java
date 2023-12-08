package ua.gym.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.gym.domain.tableManager.*;
import ua.gym.service.ClientSessionService;
import ua.gym.ui.dtos.TableSessionDto;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static ua.gym.domain.tableManager.TimeUtils.nowTruncatedtoMinutes;

@RestController
public class ClientSessionWebService {
   private final ClientSessionRepository clientSessionRepository;
   private final TableSessionRepository tableSessionRepository;
   private final ClientSessionService clientSessionService;

   @Autowired
   public ClientSessionWebService(
           ClientSessionRepository clientSessionRepository,
           TableSessionRepository tableSessionRepository,
           ClientSessionService clientSessionService) {
      this.clientSessionRepository = clientSessionRepository;
      this.tableSessionRepository = tableSessionRepository;
      this.clientSessionService = clientSessionService;
   }

   @GetMapping("clientSessions/{clientSessionId}/preCloseCalculations")
   @Transactional(readOnly = true)
   public List<TableSessionDto> getPreCloseCalculations(@PathVariable String clientSessionId) {
      LocalDateTime now =  nowTruncatedtoMinutes();

      List<TableSession> tableSessions = tableSessionRepository.getTableSessions(clientSessionId);
      return tableSessions.stream()
              .map(tableSession -> clientSessionService.mapTableSession(tableSession, now))
              .collect(toList());
   }

   @GetMapping("clientSessions/{clientSessionId}/tableSessions")
   @Transactional(readOnly = true)
   public List<TableSessionDto> getActiveTableSessions(@PathVariable String clientSessionId) {
      List<TableSession> tableSessions = tableSessionRepository.getTableSessions(clientSessionId);
      return tableSessions.stream()
              .map(TableSessionDto::new)
              .collect(toList());
   }


   @PostMapping("tableSessions/start")
   @Transactional
   public TableSessionDto addTableSession(@RequestBody TableSessionDto tableSessionDto) {
      List<TableSession> activeTableSessions = tableSessionRepository.getActiveTableSessions(tableSessionDto.getTableNumber());

      if (!activeTableSessions.isEmpty()) {
         throw new IllegalStateException("Cannot create table session. Not closed sessions exists");
      }

      ClientSession clientSession = new ClientSession();
      TableSession tableSession = new TableSession(
              clientSession,
              nowTruncatedtoMinutes(),
              tableSessionDto.getRate(),
              tableSessionDto.getTableNumber(),
              tableSessionDto.getDuration(),
              tableSessionDto.getPaidAmount());
      this.clientSessionRepository.save(clientSession);
      return new TableSessionDto(tableSession);
   }

   @PostMapping("tableSessions/{tableSessionId}/close")
   @Transactional
   public TableSessionDto closeTableSession(@PathVariable String tableSessionId) {
      TableSession tableSession = tableSessionRepository.findById(tableSessionId).orElseThrow();
      tableSession.close();
      return new TableSessionDto(tableSession);
   }
}
