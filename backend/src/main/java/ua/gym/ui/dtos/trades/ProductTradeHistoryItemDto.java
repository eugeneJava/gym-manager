package ua.gym.ui.dtos.trades;

import ua.gym.domain.trades.TradeDirection;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ProductTradeHistoryItemDto {
    private String productName;
    private List<String> productNames;
    private TradeDirection direction;
    private LocalDateTime date;
    private int amount;
    private BigDecimal price;
    private BigDecimal currentAmountOfMoney = BigDecimal.ZERO;
    private String comments;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }

    public TradeDirection getDirection() {
        return direction;
    }

    public void setDirection(TradeDirection direction) {
        this.direction = direction;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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

    public BigDecimal getCurrentAmountOfMoney() {
        return currentAmountOfMoney;
    }

    public void setCurrentAmountOfMoney(BigDecimal currentAmountOfMoney) {
        this.currentAmountOfMoney = currentAmountOfMoney;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
