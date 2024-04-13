package ua.gym.domain.trades;

import ua.gym.persistense.Identifiable;
import ua.gym.utils.Assertions;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;
import static ua.gym.utils.NumberUtils.divide;
import static ua.gym.utils.NumberUtils.v;

@Entity
@Table(name = "trades_parcel")
public class TradesParcel extends Identifiable {
    @Column(nullable = false)
    private BigDecimal weight;

    @Column(nullable = false)
    private BigDecimal deliveryPrice;

    @Column(nullable = false)
    private LocalDate startedDeliveryAt;

    private LocalDate deliveredAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryType deliveryType;

    private String comments;

    @OneToMany(mappedBy = "parcel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TradesParcelGroup> parcelGroups = new ArrayList<>();

    
    public TradesParcel(BigDecimal weight, BigDecimal deliveryPrice, LocalDate startedDeliveryAt, DeliveryType deliveryType) {
        this.weight = weight;
        this.deliveryPrice = deliveryPrice;
        this.startedDeliveryAt = startedDeliveryAt;
        this.deliveryType = deliveryType;
    }

    protected TradesParcel() {

    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public LocalDate getStartedDeliveryAt() {
        return startedDeliveryAt;
    }

    public void setStartedDeliveryAt(LocalDate startedDeliveryAt) {
        this.startedDeliveryAt = startedDeliveryAt;
    }

    public LocalDate getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDate deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<TradesParcelGroup> getParcelGroups() {
        return Collections.unmodifiableList(parcelGroups);
    }


    public void addParcelGroup(TradesParcelGroup parcelGroup) {
        Assertions.assertState(isNull(parcelGroup.getParcel()), "Parcel group already assigned");
        Assertions.assertState(nonNull(parcelGroup.getTrackId()), "Parcel must have a track number");

        this.parcelGroups.add(parcelGroup);
        parcelGroup.setParcel(this);
    }

    public String getName() {
        return parcelGroups.stream().map(TradesParcelGroup::getName).collect(joining(""));
    }

    public void calculateTotalPriceForEveryProduct() {
        this.parcelGroups.forEach(parcelGroup -> {
            if (isNull(this.weight)) {
                return;
            }

            if (isNull(this.deliveryPrice)) {
                return;
            }

            if (isNull(parcelGroup.getWeight())) {
                return;
            }

            if (isNull(parcelGroup.getProductBuy().getUnitBuyPrice())) {
                return;
            }


            BigDecimal groupWeightPart = divide(parcelGroup.getWeight(), this.weight);

            BigDecimal groupDeliveryPrice = this.deliveryPrice.multiply(groupWeightPart);
            BigDecimal unitDeliveryPrice = divide(groupDeliveryPrice, v(parcelGroup.getProductBuy().getProductUnits().size()));
            BigDecimal unitBuyPrice = parcelGroup.getProductBuy().getUnitBuyPrice().add(unitDeliveryPrice);
            parcelGroup.getProductBuy().getProductUnits()
                    .forEach(productUnit -> productUnit.setTotalBuyPrice(unitBuyPrice));
        });
    }
}
