package ua.gym.domain.tableManager;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static ua.gym.utils.NumberUtils.v;

public class TableSessionTest {

    @Test
    public void addTime_0h15m_15paid_() {
        LocalDateTime startDate = LocalDateTime.of(2023, 12, 8, 15,0);
        TableSession tableSession = new TableSession(new ClientSession(), startDate, v(100), 1, new TimeDto(1, 0), ZERO);

        tableSession.addTime(new TimeDto(0, 15), v(15));

        assertThat(tableSession.getEndDate()).isEqualTo(LocalDateTime.of(2023, 12, 8, 16,15));
        assertThat(tableSession.getDuration()).isEqualTo(new TimeDto(1, 15));
        assertThat(tableSession.getDurationAsString()).isEqualTo("01:15");
        assertThat(tableSession.getTotalPay()).isEqualTo(v(125));
        assertThat(tableSession.getPaidAmount()).isEqualTo(v(15));
        assertThat(tableSession.calculateNeedToPay()).isEqualTo(v(110));
    }

    @Test
    public void addTime_0h15m_02paid_() {
        LocalDateTime startDate = LocalDateTime.of(2023, 12, 8, 15,0);
        TableSession tableSession = new TableSession(new ClientSession(), startDate, v(100), 1, new TimeDto(1, 0), v(33));

        tableSession.addTime(new TimeDto(0, 15), v(15));

        assertThat(tableSession.getEndDate()).isEqualTo(LocalDateTime.of(2023, 12, 8, 16,15));
        assertThat(tableSession.getDuration()).isEqualTo(new TimeDto(1, 15));
        assertThat(tableSession.getDurationAsString()).isEqualTo("01:15");
        assertThat(tableSession.getTotalPay()).isEqualTo(v(125));
        assertThat(tableSession.getPaidAmount()).isEqualTo(v(48));
        assertThat(tableSession.calculateNeedToPay()).isEqualTo(v(80));
    }
}