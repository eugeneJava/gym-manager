package ua.gym.domain.trades;

import ua.gym.persistense.Identifiable;
import ua.gym.utils.Assertions;
import ua.gym.utils.NumberUtils;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "trades_product_unit")
public class TradesProductUnit extends Identifiable {


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TradesProductBuy productBuy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TradesProduct product;

    @ManyToOne(fetch = FetchType.LAZY)
    private TradesProductSale productSale;

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

    public TradesProductSale getProductSale() {
        return productSale;
    }
}
