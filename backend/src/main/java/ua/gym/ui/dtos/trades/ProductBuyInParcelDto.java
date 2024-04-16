package ua.gym.ui.dtos.trades;

import ua.gym.domain.trades.DeliveryType;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductBuyInParcelDto {
    private BigDecimal buyPrice;
    private LocalDate parcelFormedDate;
    private DeliveryType deliveryType;

    public ProductBuyInParcelDto() {
    }

    public ProductBuyInParcelDto(BigDecimal buyPrice, LocalDate parcelFormedDate, DeliveryType deliveryType) {
    this.buyPrice = buyPrice;
    this.parcelFormedDate = parcelFormedDate;
    this.deliveryType = deliveryType;
}

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public LocalDate getParcelFormedDate() {
        return parcelFormedDate;
    }

    public void setParcelFormedDate(LocalDate parcelFormedDate) {
        this.parcelFormedDate = parcelFormedDate;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }
}