package ua.gym.ui.internal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ParcelDeliveryInfoDto {
    private String deliveryType;
    private BigDecimal totalCost;
    private LocalDate sendAt;
    private LocalDate expectedDeliveryDate ;
    private String deliveryDurationInDays;
    private List<ParcelContentItemDto> content = new ArrayList<>();

    public ParcelDeliveryInfoDto(String deliveryType, BigDecimal totalCost, LocalDate sendAt, LocalDate expectedDeliveryDate, String deliveryDurationInDays) {
        this.deliveryType = deliveryType;
        this.totalCost = totalCost;
        this.sendAt = sendAt;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.deliveryDurationInDays = deliveryDurationInDays;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public LocalDate getSendAt() {
        return sendAt;
    }

    public void setSendAt(LocalDate sendAt) {
        this.sendAt = sendAt;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getDeliveryDurationInDays() {
        return deliveryDurationInDays;
    }

    public void setDeliveryDurationInDays(String deliveryDurationInDays) {
        this.deliveryDurationInDays = deliveryDurationInDays;
    }

    public List<ParcelContentItemDto> getContent() {
        return content;
    }

    public void setContent(List<ParcelContentItemDto> content) {
        this.content = content;
    }
}
