package ua.gym.ui.dtos.trades;

import ua.gym.domain.trades.TradeDirection;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductTradeHistoryItemDto {
    private String productName;
    private TradeDirection direction;
    private LocalDate date;
    private int amount;
    private BigDecimal price;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public TradeDirection getDirection() {
        return direction;
    }

    public void setDirection(TradeDirection direction) {
        this.direction = direction;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
