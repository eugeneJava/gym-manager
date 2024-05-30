package ua.gym.domain.trades;

import ua.gym.persistense.Identifiable;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ua.gym.utils.NumberUtils.v;

@Entity
@Table(name = "trades_product_sale_group")
public class TradesProductSaleGroup extends Identifiable {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SaleGroupType type;

    @OneToMany(mappedBy = "productSaleGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TradesProductSale> productSales = new ArrayList<>();
    
    public TradesProductSaleGroup(SaleGroupType type) {
        this.type = type;
    }

    protected TradesProductSaleGroup() {

    }

    public List<TradesProductSale> getProductSales() {
        return Collections.unmodifiableList(productSales);
    }

    public SaleGroupType getType() {
        return type;
    }

    void addProductSale(TradesProductSale tradesProductSale) {
        productSales.add(tradesProductSale);
    }

    public BigDecimal calculateTotalSellPrice() {
        BigDecimal totalSellPrice = productSales.stream()
                .map(TradesProductSale::calculateTotalSellPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalSellPrice.setScale(0, RoundingMode.DOWN);
    }
}
