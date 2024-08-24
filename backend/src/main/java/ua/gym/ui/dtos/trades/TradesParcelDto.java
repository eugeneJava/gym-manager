package ua.gym.ui.dtos.trades;

import ua.gym.domain.trades.DeliveryType;
import ua.gym.domain.trades.TradesParcel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TradesParcelDto {

    private String id;
    private BigDecimal weight;
    private BigDecimal deliveryPrice;
    private String deliveryDurationInDays;
    private LocalDateTime startedDeliveryAt;
    private LocalDate deliveredAt;
    private DeliveryType deliveryType;
    private String comments;
    private String name;
    private List<TradesParcelGroupDto> parcelGroups;
    private BigDecimal totalBuyPriceOfAllGroups;

    public TradesParcelDto() {
    }

    public TradesParcelDto(TradesParcel parcel) {
        this.id = parcel.getId();
        this.weight = parcel.getWeight();
        this.deliveryPrice = parcel.getDeliveryPrice();
        this.startedDeliveryAt = parcel.getStartedDeliveryAt();
        this.deliveredAt = parcel.getDeliveredAt();
        this.deliveryType = parcel.getDeliveryType();
        this.comments = parcel.getComments();
        this.parcelGroups = parcel.getParcelGroups().stream().map(TradesParcelGroupDto::new).collect(Collectors.toList());
        this.name = parcel.getName();
        this.totalBuyPriceOfAllGroups = parcel.getTotalBuyPriceOfAllGroups();
        this.deliveryDurationInDays = parcel.getDeliveryDurationFormatted();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public LocalDateTime getStartedDeliveryAt() {
        return startedDeliveryAt;
    }

    public void setStartedDeliveryAt(LocalDateTime startedDeliveryAt) {
        this.startedDeliveryAt = startedDeliveryAt;
    }

    public LocalDate getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDate deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<TradesParcelGroupDto> getParcelGroups() {
        return parcelGroups;
    }

    public void setParcelGroups(List<TradesParcelGroupDto> parcelGroups) {
        this.parcelGroups = parcelGroups;
    }

    public BigDecimal getTotalBuyPriceOfAllGroups() {
        return totalBuyPriceOfAllGroups;
    }

    public String getDeliveryDurationInDays() {
        return deliveryDurationInDays;
    }
}
