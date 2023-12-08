package ua.gym.domain.tableManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.gym.utils.NumberUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.*;
import static ua.gym.domain.tableManager.TimeUtils.nowTruncatedtoMinutes;
import static ua.gym.utils.NumberUtils.v;

public class TableSessionTest {

    @Test
    public void addTime_0h15m_15paid_() {
        LocalDateTime startDate = nowTruncatedtoMinutes();
        TableSession tableSession = new TableSession(new ClientSession(), startDate, v(100), 1, new TimeDto(1, 0), ZERO);

        tableSession.addTime(new TimeDto(0, 15), v(15));

        Assertions.assertThat(tableSession.getEndDate()).isEqualTo(startDate.plusMinutes(15));
        Assertions.assertThat(tableSession.getDuration()).isEqualTo(new TimeDto(1, 15));
        Assertions.assertThat(tableSession.getDurationAsString()).isEqualTo("01:15");
        Assertions.assertThat(tableSession.getTotalPay()).isEqualTo(v(125));
        Assertions.assertThat(tableSession.getPaidAmount()).isEqualTo(v(15));
        Assertions.assertThat(tableSession.calculateNeedToPay()).isEqualTo(v(110));
    }
}