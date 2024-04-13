package ua.gym.ui.dtos.trades;

import ua.gym.domain.trades.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class TradesParcelGroupDto {
    private String id;
    private BigDecimal weight;
    private String trackId;
    private String name;
    private String comments;
    private String productName;
    private int productAmount;
    private String parcelId;

    public TradesParcelGroupDto() {
    }

    public TradesParcelGroupDto(TradesParcelGroup parcelGroup) {
        this.id = parcelGroup.getId();
        this.weight = parcelGroup.getWeight();
        this.trackId = parcelGroup.getTrackId();
        this.name = parcelGroup.getName();
        this.comments = parcelGroup.getComments();
        List<TradesProductUnit> productUnits = parcelGroup.getProductBuy().getProductUnits();
        this.productName = productUnits.stream().findFirst().get().getProduct().getName();
        this.productAmount = productUnits.size();
        this.parcelId = nonNull(parcelGroup.getParcel()) ? parcelGroup.getParcel().getId() : null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
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

    public String getProductName() {
        return productName;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public String getParcelId() {
        return parcelId;
    }
}
