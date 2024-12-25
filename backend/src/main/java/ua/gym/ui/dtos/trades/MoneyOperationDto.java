package ua.gym.ui.dtos.trades;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MoneyOperationDto {
    private BigDecimal spentAmount;
    private BigDecimal currentTotal;
    private String message;
    private LocalDateTime createdAt;
    private String addedBy;

    public MoneyOperationDto(BigDecimal spentAmount, String message, LocalDateTime createdAt, String addedBy) {
        this.spentAmount = spentAmount;
        this.message = message;
        this.createdAt = createdAt;
        this.addedBy = addedBy;
    }

    public BigDecimal getSpentAmount() {
        return spentAmount;
    }

    public void setSpentAmount(BigDecimal spentAmount) {
        this.spentAmount = spentAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public BigDecimal getCurrentTotal() {
        return currentTotal;
    }

    public void setCurrentTotal(BigDecimal currentTotal) {
        this.currentTotal = currentTotal;
    }
}
