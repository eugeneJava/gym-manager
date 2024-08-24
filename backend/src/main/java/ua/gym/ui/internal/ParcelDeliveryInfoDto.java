package ua.gym.ui.internal;

import ua.gym.domain.trades.TradesParcel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ParcelDeliveryInfoDto {
    private String deliveryType;
    private BigDecimal totalCost;
    private LocalDateTime sendAt;
    private LocalDate expectedDeliveryDate ;
    private String deliveryDurationInDays;
    private List<ParcelContentItemDto> content = new ArrayList<>();


    public ParcelDeliveryInfoDto(TradesParcel parcel, LocalDate expectedDeliveryDate) {
        this.deliveryType = parcel.getDeliveryType().name();
        this.totalCost = parcel.getTotalPrice();
        this.sendAt = parcel.getStartedDeliveryAt();
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.deliveryDurationInDays = parcel.getDeliveryDurationFormatted();
        this.content = parcel.getParcelGroups().stream().map(ParcelContentItemDto::new)
                .sorted(Comparator.comparing(ParcelContentItemDto::getProductName))
                .collect(Collectors.toList());
    }

    public ParcelDeliveryInfoDto() {
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

    public LocalDateTime getSendAt() {
        return sendAt;
    }

    public void setSendAt(LocalDateTime sendAt) {
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
