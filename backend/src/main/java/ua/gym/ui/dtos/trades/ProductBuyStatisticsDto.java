package ua.gym.ui.dtos.trades;

import ua.gym.domain.trades.TradesProduct;

import java.util.ArrayList;
import java.util.List;

public class ProductBuyStatisticsDto {

    private TradesProductDto product;
    private List<ProductBuyInParcelDto> productsBuyInParcel = new ArrayList<>();

    public ProductBuyStatisticsDto() {
    }

    public ProductBuyStatisticsDto(TradesProduct product) {
        this.product = new TradesProductDto(product);
    }

    public TradesProductDto getProduct() {
        return product;
    }

    public void setProduct(TradesProductDto product) {
        this.product = product;
    }

    public List<ProductBuyInParcelDto> getProductsBuyInParcel() {
        return productsBuyInParcel;
    }

    public void setProductsBuyInParcel(List<ProductBuyInParcelDto> productsBuyInParcel) {
        this.productsBuyInParcel = productsBuyInParcel;
    }
}