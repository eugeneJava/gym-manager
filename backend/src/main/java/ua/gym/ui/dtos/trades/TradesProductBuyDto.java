package ua.gym.ui.dtos.trades;

import ua.gym.domain.trades.TradesProductBuy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TradesProductBuyDto {
    private String id;
    private BigDecimal totalBuyPriceInYuan;
    private BigDecimal totalBuyPriceInUah;
    private TradesParcelGroupDto parcelGroup;
    private TradesProductDto product;
    private int amount;
    private BigDecimal unitPrice;
    private BigDecimal weight;
    private String trackId;
    private String name;
    private String comments;
    private LocalDateTime purchaseDate;

    // Default public constructor
    public TradesProductBuyDto() {}

    // Constructor mapping from entity
    public TradesProductBuyDto(TradesProductBuy productBuy) {
        this.id = productBuy.getId();
        this.totalBuyPriceInYuan = productBuy.getTotalBuyPriceInYuan();
        this.totalBuyPriceInUah = productBuy.getTotalBuyPriceInUah();
        this.parcelGroup =  new TradesParcelGroupDto(productBuy.getParcelGroup());
        this.amount = productBuy.getProductUnits().size();
        this.product = new TradesProductDto(productBuy.getProductUnits().stream().findFirst().get().getProduct());
        this.unitPrice = productBuy.getUnitBuyPrice();
        this.weight = productBuy.getParcelGroup().getWeight();
        this.trackId = productBuy.getParcelGroup().getTrackId();
        this.name = productBuy.getParcelGroup().getName();
        this.comments = productBuy.getParcelGroup().getComments();
        this.purchaseDate = productBuy.getPurchaseDate();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public TradesParcelGroupDto getParcelGroup() {
        return parcelGroup;
    }

    public void setParcelGroup(TradesParcelGroupDto parcelGroup) {
        this.parcelGroup = parcelGroup;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public TradesProductDto getProduct() {
        return product;
    }

    public void setProduct(TradesProductDto product) {
        this.product = product;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
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

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }
}
