package ua.gym.domain.trades;

import ua.gym.persistense.Identifiable;
import ua.gym.utils.Assertions;
import ua.gym.utils.NumberUtils;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ua.gym.utils.Assertions.assertState;
import static ua.gym.utils.NumberUtils.isBetween;
import static ua.gym.utils.NumberUtils.v;

@Entity
@Table(name = "trades_parcel_group")
public class TradesParcelGroup extends Identifiable {

    @ManyToOne
    @JoinColumn
    private TradesParcel parcel;

    @Column
    private BigDecimal weight;

    @Column
    private String trackId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String comments;

    @OneToMany(mappedBy = "parcelGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TradesProductBuy> productBuys = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal totalBuyPriceInYuan;

    @Column(nullable = false)
    private BigDecimal totalBuyPriceInUah;

    BigDecimal groupWeightPart;
    BigDecimal groupDeliveryPrice;

    public TradesParcelGroup() {
    }

    public TradesParcelGroup(String name, String comments, BigDecimal totalBuyPriceInYuan, BigDecimal totalBuyPriceInUah) {
        this.name = name;
        this.comments = comments;
        Assertions.assertGreaterThanZero(totalBuyPriceInYuan);
        Assertions.assertGreaterThanZero(totalBuyPriceInUah);
        this.totalBuyPriceInYuan = totalBuyPriceInYuan;
        this.totalBuyPriceInUah = totalBuyPriceInUah;
    }

    void addProductBuy(TradesProductBuy tradesProductBuy) {
        this.productBuys.add(tradesProductBuy);
    }

    public TradesParcel getParcel() {
        return parcel;
    }

    public void setParcel(TradesParcel parcel) {
        assertState(Objects.isNull(this.parcel), "You cannot reassign parcel");
        if (Objects.nonNull(this.parcel) &&  (!this.parcel.equals(parcel))) {
            throw new IllegalArgumentException("You cannot change parcel on a parcel group");
        }
        this.parcel = parcel;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        assertState(Objects.isNull(this.weight), "You cannot reassign weight");
        assertState(NumberUtils.greaterOrEqualZero(weight), "Weight cannot be null");
        this.weight = weight;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        if (Objects.isNull(trackId) || trackId.isEmpty()) {
            return;
        }

        this.trackId = trackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<TradesProductBuy> getProductBuy() {
        return productBuys;
    }

    void updateParcelCalculatedData(BigDecimal groupWeightPart,
                                           BigDecimal groupDeliveryPrice) {
        Assertions.assertGreaterThanZero(groupWeightPart);
        Assertions.assertGreaterThanZero(groupDeliveryPrice);
        this.groupWeightPart = groupWeightPart;
        this.groupDeliveryPrice = groupDeliveryPrice;
    }

    public BigDecimal getGroupWeightPart() {
        return groupWeightPart;
    }

    public BigDecimal getGroupDeliveryPrice() {
        return groupDeliveryPrice;
    }

    public void performIntegrityCheck() {
        assertState(!productBuys.isEmpty(), "Parcel group should have at least one product buy");

        BigDecimal totalWeight = productBuys.stream()
                .map(TradesProductBuy::getWeightFraction)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertState(isBetween(0.99,  totalWeight, 1.01), "Total weight should be 1");

        BigDecimal totalBuyPriceInUah = productBuys.stream()
                .map(TradesProductBuy::getTotalBuyPriceInUah)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertState(totalBuyPriceInUah.compareTo(this.totalBuyPriceInUah) == 0, "Total buy price should be greater than 0");
    }

    public BigDecimal getTotalBuyPriceInYuan() {
        return totalBuyPriceInYuan;
    }


    public BigDecimal getTotalBuyPriceInUah() {
        return totalBuyPriceInUah;
    }

    public void updatePurchaseDate(LocalDateTime purchaseDate) {
        productBuys.forEach(productBuy -> productBuy.setPurchaseDate(purchaseDate));
    }
}
