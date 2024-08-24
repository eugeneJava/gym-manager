package ua.gym.domain.trades;

import ua.gym.persistense.Identifiable;
import ua.gym.utils.Assertions;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;
import static ua.gym.utils.Assertions.assertGreaterThanZero;
import static ua.gym.utils.NumberUtils.*;

@Entity
@Table(name = "trades_parcel")
public class TradesParcel extends Identifiable {
    @Column(nullable = false)
    private BigDecimal weight;

    @Column(nullable = false)
    private BigDecimal deliveryPrice;

    @Column(nullable = false)
    private LocalDateTime startedDeliveryAt;

    private LocalDate deliveredAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryType deliveryType;

    private String comments;

    @OneToMany(mappedBy = "parcel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TradesParcelGroup> parcelGroups = new ArrayList<>();


    public TradesParcel(BigDecimal weight, BigDecimal deliveryPrice, LocalDateTime startedDeliveryAt, DeliveryType deliveryType) {
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

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public LocalDateTime getStartedDeliveryAt() {
        return startedDeliveryAt;
    }

    public void setStartedDeliveryAt(LocalDateTime startedDeliveryAt) {
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
        assertGreaterThanZero(this.weight);
        assertGreaterThanZero(this.deliveryPrice);
        this.parcelGroups.forEach(parcelGroup -> {
            assertGreaterThanZero(parcelGroup.getWeight());


            BigDecimal groupWeightPart = divide(parcelGroup.getWeight(), this.weight);
            BigDecimal groupDeliveryPrice = this.deliveryPrice.multiply(groupWeightPart);
            parcelGroup.updateParcelCalculatedData(groupWeightPart, groupDeliveryPrice);

            parcelGroup.getProductBuy().forEach(buyGroup -> {
                assertGreaterThanZero(buyGroup.getUnitBuyPrice());
                BigDecimal buyGroupDeliveryPrice = multiply(groupDeliveryPrice, buyGroup.getWeightFraction());
                BigDecimal unitDeliveryPrice = divide(buyGroupDeliveryPrice, v(buyGroup.getProductUnits().size()));
                buyGroup.updateUnitPricesForBuyWithDelivery(unitDeliveryPrice);
            });
        });
    }

    public BigDecimal getTotalBuyPriceOfAllGroups() {
        return parcelGroups.stream()
                .map(TradesParcelGroup::getTotalBuyPriceInUah)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalPrice() {
        return getTotalBuyPriceOfAllGroups().add(deliveryPrice);
    }

    public String getDeliveryDurationFormatted() {
        LocalDate deliveryTo = Optional.ofNullable(getDeliveredAt()).orElse(LocalDate.now());
        Period period = Period.between(getStartedDeliveryAt().toLocalDate(), deliveryTo);
        String month = period.getMonths() > 0 ? period.getMonths() + " міс " : "";
        return month +  " " + period.getDays() + " днів";
    }
}
