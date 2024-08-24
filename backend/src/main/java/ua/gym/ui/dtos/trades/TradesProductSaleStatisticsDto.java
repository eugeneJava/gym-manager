package ua.gym.ui.dtos.trades;

import ua.gym.domain.trades.TradesProductSale;
import ua.gym.domain.trades.TradesProductUnit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TradesProductSaleStatisticsDto {
    private String id;
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;
    private BigDecimal profitPerUnit;
    private BigDecimal totalSaleProfit;
    private TradesProductUnitDto productUnit;
    private TradesProductDto product;
    private TradesProductSaleGroupDto productSaleGroup;
    private String parcelGroupId;
    private LocalDateTime soldAt;
    private int amountToSell;
    private int soldAmount;
    private String comments;

    public TradesProductSaleStatisticsDto() {
    }

    public TradesProductSaleStatisticsDto(TradesProductSale tradesProductSale) {
        this.id = tradesProductSale.getId();
        this.sellPrice = tradesProductSale.getSellPrice();

        TradesProductUnit firstProductUnit = tradesProductSale.getProductUnits().get(0);
        this.productUnit = new TradesProductUnitDto(firstProductUnit);
        this.soldAmount = tradesProductSale.getProductUnits().size();
        this.product = new TradesProductDto(firstProductUnit.getProduct());
        this.buyPrice = firstProductUnit.getProductBuy().getUnitBuyPriceWithDelivery();
        firstProductUnit.getProfit().ifPresent(profit -> this.profitPerUnit = profit);
        tradesProductSale.calculateTotalProfit().ifPresent(profit -> this.totalSaleProfit = profit);

       // this.productSaleGroup = tradesProductSale.getProductSaleGroup().map(TradesProductSaleGroupDto::new).orElse(null);
        this.parcelGroupId = tradesProductSale.getProductSaleGroup().map(group -> group.getId()).orElse(null);
        this.soldAt = tradesProductSale.getSoldAt();
        this.comments = tradesProductSale.getComments();
    }

    public int getSoldAmount() {
        return soldAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public TradesProductUnitDto getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(TradesProductUnitDto productUnit) {
        this.productUnit = productUnit;
    }

    public TradesProductDto getProduct() {
        return product;
    }

    public void setProduct(TradesProductDto product) {
        this.product = product;
    }

    public TradesProductSaleGroupDto getProductSaleGroup() {
        return productSaleGroup;
    }

    public void setProductSaleGroup(TradesProductSaleGroupDto productSaleGroup) {
        this.productSaleGroup = productSaleGroup;
    }

    public LocalDateTime getSoldAt() {
        return soldAt;
    }

    public void setSoldAt(LocalDateTime soldAt) {
        this.soldAt = soldAt;
    }

    public int getAmountToSell() {
        return amountToSell;
    }

    public void setAmountToSell(int amountToSell) {
        this.amountToSell = amountToSell;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getParcelGroupId() {
        return parcelGroupId;
    }

    public void setParcelGroupId(String parcelGroupId) {
        this.parcelGroupId = parcelGroupId;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getProfitPerUnit() {
        return profitPerUnit;
    }

    public void setProfitPerUnit(BigDecimal profitPerUnit) {
        this.profitPerUnit = profitPerUnit;
    }

    public BigDecimal getTotalSaleProfit() {
        return totalSaleProfit;
    }

    public void setTotalSaleProfit(BigDecimal totalSaleProfit) {
        this.totalSaleProfit = totalSaleProfit;
    }

    public void setSoldAmount(int soldAmount) {
        this.soldAmount = soldAmount;
    }
}
