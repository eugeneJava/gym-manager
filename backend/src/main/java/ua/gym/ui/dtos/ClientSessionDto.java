package ua.gym.ui.dtos;

import ua.gym.domain.tableManager.ClientSession;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ClientSessionDto {
    private List<ClientDto> clients;
    private List<TableSessionDto> tableSessions = new ArrayList<>();

    public ClientSessionDto() {
    }

    public ClientSessionDto(ClientSession clientSession) {
       this.tableSessions = clientSession.getTableSessions().stream().map(TableSessionDto::new).collect(toList());
    }

    public List<TableSessionDto> getTableSessions() {
        return tableSessions;
    }
}
