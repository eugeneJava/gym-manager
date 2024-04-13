package ua.gym.domain.trades;

import ua.gym.persistense.Identifiable;
import ua.gym.utils.Assertions;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;
import static ua.gym.utils.Assertions.*;
import static ua.gym.utils.NumberUtils.*;

@Entity
@Table(name = "trades_product_buy")
public class TradesProductBuy extends Identifiable {

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    private TradesParcelGroup parcelGroup;

    @Column(nullable = false)
    private BigDecimal totalBuyPriceInYuan;

    @Column(nullable = false)
    private BigDecimal totalBuyPriceInUah;

    @OneToMany(mappedBy = "productBuy", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TradesProductUnit> productUnits = new ArrayList<>();

    private LocalDateTime purchaseDate;

    TradesProductBuy() {}

    public TradesProductBuy(TradesParcelGroup parcelGroup,
                            BigDecimal totalBuyPriceInYuan,
                            BigDecimal totalBuyPriceInUah,
                            TradesProduct product,
                            LocalDateTime purchaseDate, int amount) {
        assertPresent(parcelGroup, product, purchaseDate);
        assertNonNegative(totalBuyPriceInUah);
        assertNonNegative(totalBuyPriceInYuan);
        assertGreaterThan(amount,0);
        this.parcelGroup = parcelGroup;
        this.parcelGroup.setProductBuy(this);
        this.totalBuyPriceInYuan = totalBuyPriceInYuan;
        this.totalBuyPriceInUah = totalBuyPriceInUah;
        this.purchaseDate = purchaseDate;

        for (int i = 0; i < amount; i++) {
            new TradesProductUnit(this, product);
        }
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
        return divide(totalBuyPriceInUah, v(productUnits.size()));
    }

    public TradesParcelGroup getParcelGroup() {
        return parcelGroup;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }
}