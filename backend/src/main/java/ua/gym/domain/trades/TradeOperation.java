package ua.gym.domain.trades;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TradeOperation {
    TradeDirection getDirection();

    BigDecimal spentAmount();

    LocalDateTime getDate();
}
