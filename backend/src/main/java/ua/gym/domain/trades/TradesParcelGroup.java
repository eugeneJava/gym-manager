package ua.gym.domain.trades;

import ua.gym.persistense.Identifiable;
import ua.gym.utils.Assertions;
import ua.gym.utils.NumberUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    @OneToOne(mappedBy = "parcelGroup")
    private TradesProductBuy productBuy;

    public TradesParcelGroup() {
    }

    public TradesParcelGroup(String name, String comments) {
        this.name = name;
        this.comments = comments;
    }

    void setProductBuy(TradesProductBuy tradesProductBuy) {
        Assertions.assertState(Objects.isNull(this.productBuy), "You cannot reassign product buy");
        this.productBuy = tradesProductBuy;
    }

    public TradesParcel getParcel() {
        return parcel;
    }

    public void setParcel(TradesParcel parcel) {
        Assertions.assertState(Objects.isNull(this.parcel), "You cannot reassign parcel");
        if (Objects.nonNull(this.parcel) &&  (!this.parcel.equals(parcel))) {
            throw new IllegalArgumentException("You cannot change parcel on a parcel group");
        }
        this.parcel = parcel;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        Assertions.assertState(Objects.isNull(this.weight), "You cannot reassign weight");
        Assertions.assertState(NumberUtils.greaterOrEqualZero(weight), "Weight cannot be null");
        this.weight = weight;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        if (Objects.isNull(trackId) || trackId.isEmpty()) {
            return;
        }

        Assertions.assertState(Objects.isNull(this.trackId), "You cannot reassign track id");
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

    public TradesProductBuy getProductBuy() {
        return productBuy;
    }
}
