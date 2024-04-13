package ua.gym.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import ua.gym.domain.tableManager.ClientSession;
import ua.gym.domain.tableManager.TableSession;
import ua.gym.domain.tableManager.TimeDto;
import ua.gym.ui.dtos.TableSessionDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.math.BigDecimal.ZERO;
import static ua.gym.domain.tableManager.TimeUtils.nowTruncatedtoMinutes;

@RunWith(MockitoJUnitRunner.class)
public class ClientSessionServiceTest {

    @InjectMocks private ClientSessionService clientSessionService;

    @Test
    public void mapTableSession_1h_R100_P100() {
        LocalDateTime startDate = nowTruncatedtoMinutes();
        TableSession tableSession = new TableSession(new ClientSession(), startDate, BigDecimal.valueOf(100), 1, new TimeDto(1, 0), ZERO);

        TableSessionDto dto = clientSessionService.mapTableSessionForClose(tableSession, startDate.plusHours(2));

        Assertions.assertThat(dto.getTotalPay()).isEqualTo(BigDecimal.valueOf(100));
        Assertions.assertThat(dto.getNeedToPay()).isEqualTo(BigDecimal.valueOf(100));
    }

    @Test
    public void mapTableSession_1h30m_R100_P150() {
        LocalDateTime startDate = nowTruncatedtoMinutes();
        TableSession tableSession = new TableSession(new ClientSession(), startDate, BigDecimal.valueOf(100), 1, new TimeDto(1, 30), ZERO);

        TableSessionDto dto = clientSessionService.mapTableSessionForClose(tableSession, startDate.plusHours(2));

        Assertions.assertThat(dto.getTotalPay()).isEqualTo(BigDecimal.valueOf(150));
        Assertions.assertThat(dto.getNeedToPay()).isEqualTo(BigDecimal.valueOf(150));
    }

    @Test
    public void mapTableSession_1h20m_r100__P100() {
        LocalDateTime startDate = nowTruncatedtoMinutes();
        TableSession tableSession = new TableSession(new ClientSession(), startDate, BigDecimal.valueOf(100), 1, new TimeDto(1, 20), ZERO);

        TableSessionDto dto = clientSessionService.mapTableSessionForClose(tableSession, startDate.plusHours(2));

        Assertions.assertThat(dto.getTotalPay()).isEqualTo(BigDecimal.valueOf(135)); //rounding
        Assertions.assertThat(dto.getNeedToPay()).isEqualTo(BigDecimal.valueOf(135));
    }

    @Test
    public void mapTableSession__1h_rate100_paid50__total100_needToPay50() {
        LocalDateTime startDate = nowTruncatedtoMinutes();
        TableSession tableSession = new TableSession(new ClientSession(), startDate, BigDecimal.valueOf(100), 1, new TimeDto(1, 0), BigDecimal.valueOf(50));

        TableSessionDto dto = clientSessionService.mapTableSessionForClose(tableSession, startDate.plusHours(2));

        Assertions.assertThat(dto.getTotalPay()).isEqualTo(BigDecimal.valueOf(100));
        Assertions.assertThat(dto.getNeedToPay()).isEqualTo(BigDecimal.valueOf(50));
    }

    @Test
    public void mapTableSession___1h_rate100_played45m_paid10___total75_needToPay65() {
        LocalDateTime startDate = nowTruncatedtoMinutes();
        TableSession tableSession = new TableSession(new ClientSession(), startDate, BigDecimal.valueOf(100), 1, new TimeDto(1, 0), BigDecimal.valueOf(10));

        TableSessionDto dto = clientSessionService.mapTableSessionForClose(tableSession, tableSession.getEndDate().minusMinutes(15));

        Assertions.assertThat(dto.getTotalPay()).isEqualTo(BigDecimal.valueOf(75));
        Assertions.assertThat(dto.getNeedToPay()).isEqualTo(BigDecimal.valueOf(65));
    }

    @Test
    public void mapTableSession_1h_rate100_played1m__total75() {
        LocalDateTime startDate = nowTruncatedtoMinutes();
        TableSession tableSession = new TableSession(new ClientSession(), startDate, BigDecimal.valueOf(100), 1, new TimeDto(1, 0), ZERO);

        TableSessionDto dto = clientSessionService.mapTableSessionForClose(tableSession, tableSession.getEndDate().minusMinutes(59));

        Assertions.assertThat(dto.getTotalPay()).isEqualTo(BigDecimal.valueOf(5));
        Assertions.assertThat(dto.getNeedToPay()).isEqualTo(BigDecimal.valueOf(5));
    }

    @Test
    public void mapTableSession_1h_rate100_played1h_paid1831__total100_needToPay85() {
        LocalDateTime startDate = nowTruncatedtoMinutes();
        TableSession tableSession = new TableSession(new ClientSession(), startDate, BigDecimal.valueOf(100), 1, new TimeDto(1, 0), BigDecimal.valueOf(18.31));

        TableSessionDto dto = clientSessionService.mapTableSessionForClose(tableSession, tableSession.getEndDate());

        Assertions.assertThat(dto.getTotalPay()).isEqualTo(BigDecimal.valueOf(100));
        Assertions.assertThat(dto.getNeedToPay()).isEqualTo(BigDecimal.valueOf(85)); //100 - 18.31 = 81 = 81.69 - round to five up = 85
    }

    @Test
    public void mapTableSession_2h30m_played1h16m_paid81_() {
        LocalDateTime startDate = nowTruncatedtoMinutes();
        TableSession tableSession = new TableSession(new ClientSession(), startDate, BigDecimal.valueOf(100), 1, new TimeDto(2, 30), BigDecimal.valueOf(81));

        TableSessionDto dto = clientSessionService.mapTableSessionForClose(tableSession, tableSession.getEndDate().minusHours(1).minusMinutes(14));

        Assertions.assertThat(dto.getTotalPay()).isEqualTo(BigDecimal.valueOf(130));
        Assertions.assertThat(dto.getNeedToPay()).isEqualTo(BigDecimal.valueOf(50));
    }
}
