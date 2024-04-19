package ua.gym.ui.dtos.trades;

import ua.gym.domain.trades.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class TradesParcelGroupDto {
    private String id;
    private BigDecimal weight;
    private String trackId;
    private String name;
    private String comments;
    private String productName;
    private int productAmount;
    private boolean allProductsSameWeight;
    private String parcelId;
    private LocalDateTime purchaseDate;
    private BigDecimal totalBuyPriceInYuan;
    private BigDecimal totalBuyPriceInUah;

    private List<TradesProductBuyDto> productBuys = new ArrayList<>();

    public TradesParcelGroupDto() {
    }

    public TradesParcelGroupDto(TradesParcelGroup parcelGroup) {
        this.id = parcelGroup.getId();
        this.weight = parcelGroup.getWeight();
        this.trackId = parcelGroup.getTrackId();
        this.name = parcelGroup.getName();
        this.comments = parcelGroup.getComments();
        this.totalBuyPriceInUah = parcelGroup.getTotalBuyPriceInUah();
        totalBuyPriceInYuan = parcelGroup.getTotalBuyPriceInYuan();
        this.parcelId = nonNull(parcelGroup.getParcel()) ? parcelGroup.getParcel().getId() : null;
        this.productBuys = parcelGroup.getProductBuy().stream().map(TradesProductBuyDto::new).collect(Collectors.toList());
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

    public List<TradesProductBuyDto> getProductBuys() {
        return productBuys;
    }

    public void setAllProductsSameWeight(boolean allProductsSameWeight) {
        this.allProductsSameWeight = allProductsSameWeight;
    }

    public boolean isAllProductsSameWeight() {
        return allProductsSameWeight;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getTotalBuyPriceInYuan() {
        return totalBuyPriceInYuan;
    }

    public void setTotalBuyPriceInYuan(BigDecimal totalBuyPriceInYuan) {
        this.totalBuyPriceInYuan = totalBuyPriceInYuan;
    }

    public BigDecimal getTotalBuyPriceInUah() {
        return totalBuyPriceInUah;
    }

    public void setTotalBuyPriceInUah(BigDecimal totalBuyPriceInUah) {
        this.totalBuyPriceInUah = totalBuyPriceInUah;
    }
}
