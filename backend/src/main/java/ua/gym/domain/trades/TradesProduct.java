package ua.gym.domain.trades;

import ua.gym.persistense.Identifiable;
import ua.gym.utils.Assertions;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;

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

    @ManyToOne(fetch = LAZY)
    private TradesProduct parent;

    @OneToMany(fetch = LAZY, mappedBy = "parent")
    private Set<TradesProduct> childProducts = new HashSet<>();


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

    public Set<TradesProduct> getChildProducts() {
        return Collections.unmodifiableSet(childProducts);
    }
}
