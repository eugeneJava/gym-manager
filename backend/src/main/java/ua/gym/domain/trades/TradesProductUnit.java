package ua.gym.domain.trades;

import jakarta.persistence.*;
import ua.gym.persistense.Identifiable;
import ua.gym.utils.Assertions;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static java.util.Objects.isNull;

@Entity
@Table(name = "trades_product_unit")
public class TradesProductUnit extends Identifiable {


    @ManyToOne(fetch = LAZY, optional = false)
    private TradesProductBuy productBuy;

    @ManyToOne(fetch = LAZY, optional = false)
    private TradesProduct product;

    @ManyToOne(fetch = LAZY)
    private TradesProductSale productSale;

    @Enumerated(STRING)
    public ProductUnitNotAvailabilityReason notAvailabityReason;

    @ManyToOne(fetch = LAZY)
    private TradesProductUnit parent;

    @OneToMany(fetch = LAZY, mappedBy = "parent")
    private Set<TradesProductUnit> childProductUnits = new HashSet<>();

    TradesProductUnit() {
    }

    TradesProductUnit(TradesProductBuy productBuy, TradesProduct product) {
        Assertions.assertPresent(productBuy, product);
        this.productBuy = productBuy;
        this.product = product;
        productBuy.addProductUnit(this);
        product.getChildProducts().forEach(child -> {
            new TradesProductUnit(productBuy, child, this);
        });
    }

    private TradesProductUnit(TradesProductBuy productBuy, TradesProduct product, TradesProductUnit parent) {
        Assertions.assertPresent(productBuy, product, parent);
        this.productBuy = productBuy;
        this.product = product;
        productBuy.addProductUnit(this);
        Assertions.assertState(isNull(parent.parent), "Only one level of relations is supported");
        Assertions.assertState(this.product.equals(parent.getProduct()), "Parent cannot be the same product as child");
        this.parent = parent;
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
