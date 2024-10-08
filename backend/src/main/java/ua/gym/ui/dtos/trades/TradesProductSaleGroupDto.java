package ua.gym.ui.dtos.trades;

import ua.gym.domain.trades.SaleGroupType;
import ua.gym.domain.trades.TradesProductSaleGroup;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TradesProductSaleGroupDto {
    private String id;
    private SaleGroupType type;
    private LocalDateTime soldAt;
    private String comments;
    private List<TradesProductSaleDto> productSales = new ArrayList<>();
    private BigDecimal totalSellPrice;
    private String username;

    public TradesProductSaleGroupDto() {
    }

    // Constructor mapping fields from TradesProductSaleGroup entity
    public TradesProductSaleGroupDto(TradesProductSaleGroup productSaleGroup) {
        this.id = productSaleGroup.getId();
        this.type = productSaleGroup.getType();
        this.productSales = productSaleGroup.getProductSales().stream().map(TradesProductSaleDto::new).collect(Collectors.toList());
        this.totalSellPrice = productSaleGroup.calculateTotalSellPrice();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SaleGroupType getType() {
        return type;
    }

    public void setType(SaleGroupType type) {
        this.type = type;
    }

    public List<TradesProductSaleDto> getProductSales() {
        return productSales;
    }

    public void setProductSales(List<TradesProductSaleDto> productSales) {
        this.productSales = productSales;
    }

    public LocalDateTime getSoldAt() {
        return soldAt;
    }

    public void setSoldAt(LocalDateTime soldAt) {
        this.soldAt = soldAt;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getTotalSellPrice() {
        return totalSellPrice;
    }

    public void setTotalSellPrice(BigDecimal totalSellPrice) {
        this.totalSellPrice = totalSellPrice;
    }
}
