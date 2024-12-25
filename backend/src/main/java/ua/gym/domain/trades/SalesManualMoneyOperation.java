package ua.gym.domain.trades;

import jakarta.persistence.*;
import ua.gym.persistense.Identifiable;
import ua.gym.utils.Assertions;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales_manual_money_operation")
public class SalesManualMoneyOperation extends Identifiable {

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal paid;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String addedBy;

    SalesManualMoneyOperation() {
    }

    public SalesManualMoneyOperation(String description, BigDecimal paid, String addedBy) {
        Assertions.assertPresent(description, paid, addedBy);
        this.description = description;
        this.paid = paid;
        this.createdAt = LocalDateTime.now();
        this.addedBy = addedBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }
}
