package ua.gym.domain.trades;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TradeOperation {
    TradeDirection getDirection();

    BigDecimal spentAmount();

    LocalDate getDate();
}
