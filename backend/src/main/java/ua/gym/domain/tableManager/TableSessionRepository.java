package ua.gym.domain.tableManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TableSessionRepository extends JpaRepository<TableSession, String> {

    @Query(value = "SELECT u FROM TableSession u where u.tableNumber = :tableNumber and u.closeDate is null ")
    List<TableSession> getActiveTableSessions(int tableNumber);

    @Query(value = "SELECT u FROM TableSession u where u.clientSession.id = :clientSessionId order by u.startDate asc")
    List<TableSession> getTableSessions(String clientSessionId);
}
