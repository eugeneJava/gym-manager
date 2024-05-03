package ua.gym.ui.dtos.trades;

import ua.gym.domain.trades.TradesProduct;

import java.math.BigDecimal;

public class TradesProductDto {
    private String id;
    private String name;
    private String comments;

    public TradesProductDto() {
    }

    private BigDecimal recommendedPrice;

    public TradesProductDto(TradesProduct product) {
        this.id = product.getId();
        this.name = product.getName();
        this.comments = product.getComments();
        this.recommendedPrice = product.getRecommendedPrice();
    }

    public BigDecimal getRecommendedPrice() {
        return recommendedPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}