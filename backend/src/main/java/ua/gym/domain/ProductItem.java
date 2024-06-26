package ua.gym.domain;

import ua.gym.persistense.Identifiable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static ua.gym.utils.Assertions.assertGreaterThan;
import static ua.gym.utils.Assertions.assertPresent;

@Entity
@Table
public class ProductItem extends Identifiable {
    @ManyToOne(optional = false)
    private Product product;
    @Column(
            nullable = false
    )
    private BigDecimal price;
    @Column
    private Integer tableNumber;
    @Column(
            nullable = false
    )
    private boolean paid;
    @Column(
            nullable = false
    )
    private LocalDateTime updateDate;
    @Column(
            nullable = false
    )
    private LocalDateTime createDate;

    protected ProductItem() {
    }

    public ProductItem(Product product, boolean paid, Integer tableNumber) {
        assertPresent(product, tableNumber);
        assertGreaterThan(tableNumber, 0);

        this.product = product;
        this.price = product.getPrice();
        this.paid = paid;
        this.tableNumber = tableNumber;
        this.createDate = LocalDateTime.now();
        this.updateDate = this.createDate;
    }

    public Product getProduct() {
        return this.product;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Integer getTableNumber() {
        return this.tableNumber;
    }

    public boolean isPaid() {
        return this.paid;
    }

    public LocalDateTime getUpdateDate() {
        return this.updateDate;
    }

    public LocalDateTime getCreateDate() {
        return this.createDate;
    }

    public void markPaid() {
        this.paid = true;
        this.updateDate = LocalDateTime.now();
    }
}
