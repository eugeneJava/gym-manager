package ua.gym.domain.trades;

import ua.gym.persistense.Identifiable;
import ua.gym.utils.Assertions;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "trades_product")
public class TradesProduct extends Identifiable {

    @Column(nullable = false)
    private String name;

    private String comments;

    @Column
    private BigDecimal recommendedPrice;

    @Column
    @Enumerated(EnumType.STRING)
    private TradesProductCategory category;


    public BigDecimal getRecommendedPrice() {
        return recommendedPrice;
    }

    public void setRecommendedPrice(BigDecimal recommendedPrice) {
        this.recommendedPrice = recommendedPrice;
    }

    protected TradesProduct() {
    }

    public TradesProduct(String name, TradesProductCategory category) {
        Assertions.assertPresent(name,category);
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getComments() {
        return comments;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public TradesProductCategory getCategory() {
        return category;
    }
}
