package ua.gym.ui.dtos;

import ua.gym.domain.tableManager.TableSession;
import ua.gym.domain.tableManager.TimeDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TableSessionDto {
    private String id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime closeDate;
    private BigDecimal rate;
    private BigDecimal paidAmount;
    private BigDecimal totalPay;
    private BigDecimal needToPay;
    private int tableNumber;
    private TimeDto duration;
    private boolean paid;
    private String clientSessionId;

    public TableSessionDto() {
    }

    public TableSessionDto(TableSession tableSession) {
        this.id = tableSession.getId();
        this.startDate = tableSession.getStartDate();
        this.endDate = tableSession.getEndDate();
        this.closeDate = tableSession.getCloseDate();
        this.rate = tableSession.getRate();
        this.tableNumber = tableSession.getTableNumber();
        this.duration = tableSession.getDuration();
        this.paidAmount = tableSession.getPaidAmount();
        this.totalPay = tableSession.getTotalPay();
        this.needToPay = tableSession.calculateNeedToPay();
        this.clientSessionId = tableSession.getClientSession().getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDateTime closeDate) {
        this.closeDate = closeDate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public TimeDto getDuration() {
        return duration;
    }

    public void setDuration(TimeDto duration) {
        this.duration = duration;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getClientSessionId() {
        return clientSessionId;
    }

    public void setClientSessionId(String clientSessionId) {
        this.clientSessionId = clientSessionId;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getTotalPay() {
        return totalPay;
    }

    public BigDecimal getNeedToPay() {
        return needToPay;
    }

    public void setTotalPay(BigDecimal totalPay) {
        this.totalPay = totalPay;
    }

    public void setNeedToPay(BigDecimal needToPay) {
        this.needToPay = needToPay;
    }
}

