package ua.gym.ui.dtos.trades;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TradesProductSaleTotalStatisticsDto {
    private LocalDate from;
    private LocalDate to;
    private BigDecimal totalProfit = BigDecimal.ZERO;
    private BigDecimal totalSold = BigDecimal.ZERO;
    private List<ProductSaleStatDto> productStat = new ArrayList<>();

    public TradesProductSaleTotalStatisticsDto() {
    }

    public TradesProductSaleTotalStatisticsDto(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public BigDecimal getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(BigDecimal totalSold) {
        this.totalSold = totalSold;
    }

    public List<ProductSaleStatDto> getProductStat() {
        return productStat;
    }

    public void setProductStat(List<ProductSaleStatDto> productStat) {
        this.productStat = productStat;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }
}
