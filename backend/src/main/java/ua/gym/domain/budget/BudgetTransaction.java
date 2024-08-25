package ua.gym.domain.budget;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface BudgetTransaction {
    BudgetTransactionType getTransactionType();

    BigDecimal spentAmount();

    LocalDateTime getDate();

    List<String> getNames();

    String comments();
    long amount();
}
