package ua.gym.ui.dtos.trades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductTradeStatisticsDto {
    private List<ProductTradeHistoryItemDto> history = new ArrayList<>();
    private BigDecimal totalBought = BigDecimal.ZERO;
    private BigDecimal totalSold = BigDecimal.ZERO;
    private BigDecimal totalProfit = BigDecimal.ZERO;

    public List<ProductTradeHistoryItemDto> getHistory() {
        return history;
    }

    public void setHistory(List<ProductTradeHistoryItemDto> history) {
        this.history = history;
    }

    public BigDecimal getTotalBought() {
        return totalBought;
    }

    public void setTotalBought(BigDecimal totalBought) {
        this.totalBought = totalBought;
    }

    public BigDecimal getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(BigDecimal totalSold) {
        this.totalSold = totalSold;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }
}
