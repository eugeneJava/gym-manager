package ua.gym.domain.trades;

import ua.gym.domain.budget.BudgetTransaction;
import ua.gym.domain.budget.BudgetTransactionType;
import ua.gym.persistense.Identifiable;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Comparator.naturalOrder;
import static ua.gym.domain.trades.SaleGroupType.NONE;
import static ua.gym.domain.trades.SaleGroupType.RACKET;
import static ua.gym.domain.trades.TradesProductCategory.BLADE;
import static ua.gym.utils.NumberUtils.v;

@Entity
@Table(name = "trades_product_sale_group")
public class TradesProductSaleGroup extends Identifiable implements BudgetTransaction {
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

    @Override
    public BudgetTransactionType getTransactionType() {
        return BudgetTransactionType.INCOME;
    }

    @Override
    public BigDecimal spentAmount() {
        return calculateTotalSellPrice();
    }

    @Override
    public LocalDateTime getDate() {
        return getSoldAt();
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

    public int getTotalItems() {
        return productSales.stream()
                .mapToInt(sale -> sale.getProductUnits().size())
                .sum();
    }

    public List<String> getNames() {
        if (getType().equals(RACKET)) {
            return productSales.stream().flatMap(sale -> sale.getProductUnits().stream())
                    .sorted((unit1, unit2) -> {
                                 if (unit1.getProduct().getCategory().equals(BLADE) & !unit2.getProduct().getCategory().equals(BLADE)) {
                                     return -1;
                                 }

                                    if (!unit1.getProduct().getCategory().equals(BLADE) & unit2.getProduct().getCategory().equals(BLADE)) {
                                        return 1;
                                    }

                                    return unit1.getProduct().getName().compareTo(unit2.getProduct().getName());
                            })
                    .map(unit -> unit.getProduct().getName())
                    .toList();
        }

        return getDefaultNameSorting();
    }

    public List<String> getDefaultNameSorting() {
        return productSales.stream()
                .map(sale -> sale.getProduct().getName()).sorted(naturalOrder())
                .toList();
    }

    public LocalDateTime getSoldAt() {
        return productSales.stream().findFirst()
                .map(TradesProductSale::getSoldAt)
                .orElse(null);
    }

    public String getComments() {
        return productSales.stream().findFirst()
                .map(TradesProductSale::getComments)
                .orElse(null);
    }

    public static TradesProductSaleGroup createEmptyGroup() {
        return new TradesProductSaleGroup(NONE);
    }

    @Override
    public String comments() {
        return getComments();
    }

    @Override
    public long amount() {
        return getTotalItems();
    }
}
