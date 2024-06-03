package ua.gym.domain.trades;

import jakarta.persistence.*;
import ua.gym.persistense.Identifiable;
import ua.gym.utils.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static jakarta.persistence.FetchType.LAZY;
import static ua.gym.utils.Assertions.*;
import static ua.gym.utils.NumberUtils.divide;
import static ua.gym.utils.NumberUtils.v;

@Entity
@Table(name = "trades_product_buy")
public class TradesProductBuy extends Identifiable implements TradeOperation {

    @ManyToOne(fetch = LAZY)
    private TradesParcelGroup parcelGroup;

    @Column
    private BigDecimal totalBuyPriceInYuan;

    @Column(nullable = false)
    private BigDecimal totalBuyPriceInUah;

    @OneToMany(mappedBy = "productBuy", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TradesProductUnit> productUnits = new ArrayList<>();

    BigDecimal weightFraction;
    BigDecimal unitBuyPrice;
    BigDecimal unitDeliveryPrice;
    BigDecimal unitBuyPriceWithDelivery;

    @ManyToOne
    @JoinColumn
    private TradesProduct product;

    private LocalDate purchaseDate;

    private Integer amount;

    TradesProductBuy() {
    }

    public TradesProductBuy(TradesParcelGroup parcelGroup,
                            BigDecimal totalBuyPriceInYuan,
                            BigDecimal totalBuyPriceInUah,
                            TradesProduct product,
                            LocalDate purchaseDate, int amount) {
        assertPresent(parcelGroup, product, purchaseDate);
        assertGreaterThanZero(totalBuyPriceInUah);
        assertGreaterThan(amount, 0);
        this.parcelGroup = parcelGroup;
        this.parcelGroup.addProductBuy(this);
        this.totalBuyPriceInYuan = totalBuyPriceInYuan;
        this.totalBuyPriceInUah = totalBuyPriceInUah;
        this.purchaseDate = purchaseDate;
        this.product = product;
        this.amount = amount;
        this.unitBuyPrice = divide(totalBuyPriceInUah, v(amount));

        for (int i = 0; i < amount; i++) {
            new TradesProductUnit(this, product);
        }
    }

    public TradesProductBuy(TradesProduct product,
                            LocalDate purchaseDate, int amount, BigDecimal unitBuyPrice) {
        assertPresent(product, purchaseDate);
        assertGreaterThanZero(unitBuyPrice);
        assertGreaterThan(amount, 0);
        this.purchaseDate = purchaseDate;
        this.product = product;
        this.amount = amount;

        for (int i = 0; i < amount; i++) {
            new TradesProductUnit(this, product);
        }
        setWeightFraction(BigDecimal.ONE);
        setUnitBuyPriceForStandaloneBuy(unitBuyPrice);
        this.totalBuyPriceInUah = unitBuyPrice.multiply(v(amount));
    }


    public void setWeightFraction(BigDecimal weightFraction) {
        assertGreaterThanZero(weightFraction);
        Assertions.assertState(weightFraction.compareTo(BigDecimal.ONE) <= 0, "Weight fraction should be less than 1");
        this.weightFraction = weightFraction;
    }

    void addProductUnit(TradesProductUnit productUnit) {
        productUnits.add(productUnit);
    }

    public BigDecimal getTotalBuyPriceInYuan() {
        return totalBuyPriceInYuan;
    }

    public BigDecimal getTotalBuyPriceInUah() {
        return totalBuyPriceInUah;
    }

    public List<TradesProductUnit> getProductUnits() {
        return Collections.unmodifiableList(productUnits);
    }

    public BigDecimal getUnitBuyPrice() {
        return unitBuyPrice;
    }

    public Optional<TradesParcelGroup> getParcelGroup() {
        return Optional.ofNullable(parcelGroup);
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public TradesProduct getProduct() {
        return product;
    }

    void updateUnitPricesForBuyWithDelivery(BigDecimal unitDeliveryPrice) {
        assertGreaterThanZero(unitDeliveryPrice);
        this.unitDeliveryPrice = unitDeliveryPrice;
        this.unitBuyPriceWithDelivery = getUnitBuyPrice().add(unitDeliveryPrice);
    }

    private void setUnitBuyPriceForStandaloneBuy(BigDecimal unitBuyPrice) {
        assertGreaterThanZero(unitBuyPrice);
        this.unitBuyPrice = unitBuyPrice;
        this.unitBuyPriceWithDelivery = this.unitBuyPrice;
    }

    public BigDecimal getWeightFraction() {
        return weightFraction;
    }

    public BigDecimal getUnitBuyPriceWithDelivery() {
        return unitBuyPriceWithDelivery;
    }

    public BigDecimal getUnitDeliveryPrice() {
        return unitDeliveryPrice;
    }

    @Override
    public TradeDirection getDirection() {
        return TradeDirection.BUY;
    }

    @Override
    public BigDecimal spentAmount() {
        return getTotalBuyPriceInUah();
    }

    @Override
    public LocalDate getDate() {
        return getPurchaseDate();
    }

    void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}