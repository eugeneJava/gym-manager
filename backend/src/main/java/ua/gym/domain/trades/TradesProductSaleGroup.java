package ua.gym.domain.trades;

import ua.gym.persistense.Identifiable;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
}
