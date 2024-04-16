package ua.gym.domain.trades;

import ua.gym.persistense.Identifiable;
import ua.gym.utils.Assertions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "trades_product")
public class TradesProduct extends Identifiable {

    @Column(nullable = false)
    private String name;

    private String comments;


    protected TradesProduct() {
    }

    public TradesProduct(String name) {
        Assertions.assertPresent(name);
        this.name = name;
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
}
