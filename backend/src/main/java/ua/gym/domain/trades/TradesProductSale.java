package ua.gym.domain.trades;

import ua.gym.persistense.Identifiable;
import ua.gym.utils.Assertions;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static ua.gym.utils.Assertions.assertState;

@Entity
@Table(name = "trades_product_sale")
public class TradesProductSale extends Identifiable {

    @Column(nullable = false)
    private BigDecimal sellPrice;

    @OneToMany(mappedBy = "productSale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TradesProductUnit> productUnits = new ArrayList<>();

    @ManyToOne
    @JoinColumn
    private TradesProductSaleGroup productSaleGroup;

    @Column(nullable = false)
    private LocalDateTime soldAt;

    private String comments;

    TradesProductSale() {
    }

    public TradesProductSale(BigDecimal sellPrice, LocalDateTime soldAt, String comments) {
        Assertions.assertPresent(sellPrice, soldAt);
        this.sellPrice = sellPrice;
        this.soldAt = soldAt;
        this.comments = comments;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public List<TradesProductUnit> getProductUnits() {
        return Collections.unmodifiableList(productUnits);
    }

    public void addProductUnit(TradesProductUnit productUnit) {
        Assertions.assertState(isNull(productUnit.getProductSale()), "Product unit is already sold");
        productUnits.add(productUnit);
        productUnit.setProductSale(this);
    }

    public Optional<TradesProductSaleGroup> getProductSaleGroup() {
        return Optional.ofNullable(productSaleGroup);
    }

    public LocalDateTime getSoldAt() {
        return soldAt;
    }

    public void setProductSaleGroup(TradesProductSaleGroup productSaleGroup) {
        assertState(isNull(this.productSaleGroup), "You cannot reassingn a sale group");
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
