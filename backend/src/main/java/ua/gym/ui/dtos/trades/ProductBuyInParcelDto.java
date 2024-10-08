package ua.gym.ui.dtos.trades;

import ua.gym.domain.trades.DeliveryType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProductBuyInParcelDto {
    private BigDecimal buyPrice;
    private BigDecimal deliveryPrice;
    private LocalDateTime parcelFormedDate;
    private DeliveryType deliveryType;

    public ProductBuyInParcelDto() {
    }

    public ProductBuyInParcelDto(BigDecimal buyPrice, BigDecimal deliveryPrice, LocalDateTime parcelFormedDate, DeliveryType deliveryType) {
    this.buyPrice = buyPrice;
    this.deliveryPrice = deliveryPrice;
    this.parcelFormedDate = parcelFormedDate;
    this.deliveryType = deliveryType;
}

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public LocalDateTime getParcelFormedDate() {
        return parcelFormedDate;
    }

    public void setParcelFormedDate(LocalDateTime parcelFormedDate) {
        this.parcelFormedDate = parcelFormedDate;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }


}