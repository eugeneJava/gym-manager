package ua.gym.ui.dtos.trades;

import ua.gym.domain.trades.SaleGroupType;
import ua.gym.domain.trades.TradesProductSaleGroup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TradesProductSaleGroupDto {
    private String id;
    private SaleGroupType type;
    private LocalDate soldAt;
    private String comments;
    private List<TradesProductSaleDto> productSales = new ArrayList<>();

    public TradesProductSaleGroupDto() {
    }

    // Constructor mapping fields from TradesProductSaleGroup entity
    public TradesProductSaleGroupDto(TradesProductSaleGroup productSaleGroup) {
        this.id = productSaleGroup.getId();
        this.type = productSaleGroup.getType();
        this.productSales = productSaleGroup.getProductSales().stream().map(TradesProductSaleDto::new).collect(Collectors.toList());
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

    public LocalDate getSoldAt() {
        return soldAt;
    }

    public void setSoldAt(LocalDate soldAt) {
        this.soldAt = soldAt;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
