package ua.gym.ui.dtos.trades;

import ua.gym.domain.trades.TradesProductSale;
import ua.gym.domain.trades.TradesProductUnit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TradesProductSaleDto {
    private String id;
    private BigDecimal sellPrice;
    private TradesProductUnitDto productUnit;
    private TradesProductDto product;
    private TradesProductSaleGroupDto productSaleGroup;
    private String parcelGroupId;
    private LocalDateTime soldAt;
    private int amountToSell;
    private String comments;
    private String username;

    public TradesProductSaleDto() {
    }

    public TradesProductSaleDto(TradesProductSale tradesProductSale) {
        this.id = tradesProductSale.getId();
        this.sellPrice = tradesProductSale.getSellPrice();

        TradesProductUnit firstProductUnit = tradesProductSale.getProductUnits().get(0);
        this.productUnit = new TradesProductUnitDto(firstProductUnit);
        this.amountToSell = tradesProductSale.getProductUnits().size();
        this.product = new TradesProductDto(firstProductUnit.getProduct());
        this.parcelGroupId = tradesProductSale.getProductSaleGroup().map(group -> group.getId()).orElse(null);
        this.soldAt = tradesProductSale.getSoldAt();
        this.comments = tradesProductSale.getComments();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public TradesProductUnitDto getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(TradesProductUnitDto productUnit) {
        this.productUnit = productUnit;
    }

    public TradesProductDto getProduct() {
        return product;
    }

    public void setProduct(TradesProductDto product) {
        this.product = product;
    }

    public TradesProductSaleGroupDto getProductSaleGroup() {
        return productSaleGroup;
    }

    public void setProductSaleGroup(TradesProductSaleGroupDto productSaleGroup) {
        this.productSaleGroup = productSaleGroup;
    }

    public LocalDateTime getSoldAt() {
        return soldAt;
    }

    public void setSoldAt(LocalDateTime soldAt) {
        this.soldAt = soldAt;
    }

    public int getAmountToSell() {
        return amountToSell;
    }

    public void setAmountToSell(int amountToSell) {
        this.amountToSell = amountToSell;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getParcelGroupId() {
        return parcelGroupId;
    }

    public void setParcelGroupId(String parcelGroupId) {
        this.parcelGroupId = parcelGroupId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
