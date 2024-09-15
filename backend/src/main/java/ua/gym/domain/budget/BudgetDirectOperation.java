package ua.gym.domain.budget;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import ua.gym.persistense.Identifiable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static ua.gym.utils.Assertions.assertPresent;

@Entity
@Table(name = "budget_direct_operation")
public class BudgetDirectOperation extends Identifiable {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal spentAmount;

    @Column(nullable = false)
    private BudgetTransactionType type;

    @Column(nullable = false)
    private LocalDateTime date;

    private String comments;

    private long amount;

    BudgetDirectOperation() {
    }

    public BudgetDirectOperation(String name, BigDecimal spentAmount, BudgetTransactionType type, LocalDateTime date) {
        assertPresent(name, spentAmount, type, date);
        this.name = name;
        this.spentAmount = spentAmount;
        this.type = type;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getSpentAmount() {
        return spentAmount;
    }

    public BudgetTransactionType getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getComments() {
        return comments;
    }
}
