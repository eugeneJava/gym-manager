package ua.gym.domain.tableManager;

import ua.gym.domain.Client;
import ua.gym.persistense.Identifiable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static ua.gym.domain.tableManager.TimeUtils.nowTruncatedtoMinutes;

@Entity
@Table(name = "client_session")
public class ClientSession extends Identifiable {

    //private Set<Client> clients = new HashSet<>();

    @OneToMany(mappedBy = "clientSession", cascade = CascadeType.ALL)
    private Set<TableSession> sessions = new HashSet<>();

    public ClientSession() {
    }

    public void setClients(Set<Client> clients) {
        //this.clients = clients;
    }

    void addTableSession(TableSession session) {
        this.sessions.add(session);
    }

    public List<TableSession> getTableSessions() {
        return sessions.stream()
                .sorted(comparing(TableSession::getStartDate))
                .collect(toList());
    }

    public TableSession getCurrentSession() {
        //Check time
        List<TableSession> sessions = getTableSessions();
        return sessions.isEmpty() ? null : sessions.get(sessions.size() - 1);
    }


    public void moveToAnotherTable(int table, BigDecimal rate) {
        TableSession currentSession = getCurrentSession();
        //compare tables
        currentSession.close();

        LocalDateTime endDate = currentSession.getEndDate();
        LocalDateTime closeDate = currentSession.getCloseDate();

        TimeDto duration = TimeUtils.duration(closeDate, endDate);

        new TableSession(this, nowTruncatedtoMinutes(), rate, table, duration, BigDecimal.ZERO);

    }
}
