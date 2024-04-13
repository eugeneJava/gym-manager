package ua.gym.ui.dtos;

import ua.gym.domain.tableManager.TimeDto;

import java.math.BigDecimal;

public class AddedTimeDto {
    private BigDecimal paidAmount;
    private TimeDto duration;

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public TimeDto getDuration() {
        return duration;
    }

    public void setDuration(TimeDto duration) {
        this.duration = duration;
    }
}
