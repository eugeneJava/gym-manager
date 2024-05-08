package ua.gym.domain.trades;

import ua.gym.persistense.Identifiable;
import ua.gym.utils.Assertions;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static ua.gym.utils.Assertions.assertGreaterThanZero;
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
    private LocalDate soldAt;

    private String comments;


    TradesProductSale() {
    }

    public TradesProductSale(BigDecimal sellPrice, LocalDate soldAt, String comments) {
        Assertions.assertPresent(sellPrice, soldAt);
        assertGreaterThanZero(sellPrice);
        this.sellPrice = sellPrice;
        this.soldAt = soldAt;
        this.comments = comments;
    }

    public TradesProductSale(TradesProductSaleGroup group, BigDecimal sellPrice, LocalDate soldAt, String comments) {
        this(sellPrice, soldAt, comments);
        this.setProductSaleGroup(group);
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public List<TradesProductUnit> getProductUnits() {
        return Collections.unmodifiableList(productUnits);
    }

    public void addProductUnit(TradesProductUnit productUnit) {
        assertState(!productUnit.getProductSale().isPresent(), "Product unit is already sold");
        assertState(assertUnitIsAvailableForSale(productUnit), "Product unit is not available for sale. It has empty parcel delivery date.");

        getLastProductUnit().ifPresent(lastUnit -> {
            assertState(lastUnit.getProduct().equals(productUnit.getProduct()), "Single product sale should contain units of the same product.");
        });

        productUnits.add(productUnit);
        productUnit.setProductSale(this);
    }

    private Optional<TradesProductUnit> getLastProductUnit() {
        if(productUnits.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(productUnits.get(productUnits.size() - 1));
    }

    public TradesProduct getProduct() {
        return productUnits.stream().findFirst().map(TradesProductUnit::getProduct).get();
    }

    private boolean assertUnitIsAvailableForSale(TradesProductUnit productUnit) {
        boolean availableForSale = productUnit.getProductBuy()
                .getParcelGroup()
                .map(group -> nonNull(group.getParcel()) && nonNull(group.getParcel().getDeliveredAt()))

                .orElse(true);
        return availableForSale;
    }


    public Optional<TradesProductSaleGroup> getProductSaleGroup() {
        return Optional.ofNullable(productSaleGroup);
    }


    public LocalDate getSoldAt() {
        return soldAt;
    }

    private void setProductSaleGroup(TradesProductSaleGroup productSaleGroup) {
        Assertions.assertPresent(productSaleGroup);
        assertState(isNull(this.productSaleGroup), "You cannot reassign a sale group");
        this.productSaleGroup = productSaleGroup;
        productSaleGroup.addProductSale(this);
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public Optional<BigDecimal> calculateTotalProfit() {
         Optional<BigDecimal> profit = this.getProductUnits().stream().flatMap(unit -> unit.getProfit().stream()).reduce(BigDecimal::add);
        return profit;
    }

}
