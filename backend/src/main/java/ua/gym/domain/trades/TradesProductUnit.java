package ua.gym.domain.trades;

import ua.gym.persistense.Identifiable;
import ua.gym.utils.Assertions;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Optional;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "trades_product_unit")
public class TradesProductUnit extends Identifiable {


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TradesProductBuy productBuy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TradesProduct product;

    @ManyToOne(fetch = FetchType.LAZY)
    private TradesProductSale productSale;

    @Enumerated(STRING)
    public ProductUnitNotAvailabilityReason notAvailabityReason;

    TradesProductUnit() {
    }

    public TradesProductUnit(TradesProductBuy productBuy, TradesProduct product) {
        Assertions.assertPresent(productBuy, product);
        this.productBuy = productBuy;
        this.product = product;
        productBuy.addProductUnit(this);
    }

    public TradesProductBuy getProductBuy() {
        return productBuy;
    }

    public TradesProduct getProduct() {
        return product;
    }

    void setProductSale(TradesProductSale tradesProductSale) {
        this.productSale = tradesProductSale;
    }

    public Optional<TradesProductSale> getProductSale() {
        return Optional.ofNullable(productSale);
    }

    public Optional<BigDecimal> getProfit() {
        return getProductSale()
                .map(sale -> sale.getSellPrice().subtract(productBuy.getUnitBuyPriceWithDelivery()));
    }

    public ProductUnitNotAvailabilityReason getNotAvailabityReason() {
        return notAvailabityReason;
    }

    public void setNotAvailabityReason(ProductUnitNotAvailabilityReason notAvailabityReason) {
        this.notAvailabityReason = notAvailabityReason;
    }
}
