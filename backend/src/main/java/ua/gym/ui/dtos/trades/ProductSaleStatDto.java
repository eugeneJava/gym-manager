package ua.gym.ui.dtos.trades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductSaleStatDto {
    private String productName;
    private BigDecimal soldAmount = BigDecimal.ZERO;
    private BigDecimal totalProfit = BigDecimal.ZERO;
    private BigDecimal avgBuyPrice = BigDecimal.ZERO;
    private BigDecimal avgProfitPerUnit = BigDecimal.ZERO;

    private List<TradesProductSaleStatisticsDto> saleStatistics = new ArrayList<>();

    public ProductSaleStatDto() {
    }

    public ProductSaleStatDto(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }


    public BigDecimal getSoldAmount() {
        return soldAmount;
    }

    public void setSoldAmount(BigDecimal soldAmount) {
        this.soldAmount = soldAmount;
    }

    public BigDecimal getAvgBuyPrice() {
        return avgBuyPrice;
    }

    public void setAvgBuyPrice(BigDecimal avgBuyPrice) {
        this.avgBuyPrice = avgBuyPrice;
    }

    public BigDecimal getAvgProfitPerUnit() {
        return avgProfitPerUnit;
    }

    public void setAvgProfitPerUnit(BigDecimal avgProfitPerUnit) {
        this.avgProfitPerUnit = avgProfitPerUnit;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<TradesProductSaleStatisticsDto> getSaleStatistics() {
        return saleStatistics;
    }

    public void setSaleStatistics(List<TradesProductSaleStatisticsDto> saleStatistics) {
        this.saleStatistics = saleStatistics;
    }
}
