package ua.gym.ui.dtos.trades;

import ua.gym.domain.trades.*;

public class TradesProductUnitDto {
    private TradesProductDto product;
    private String id;

    public TradesProductUnitDto() {
    }

    public TradesProductUnitDto(TradesProductUnit productUnit) {
        this.id = productUnit.getId();
        this.product = new TradesProductDto(productUnit.getProduct());
    }

    public TradesProductDto getProduct() {
        return product;
    }

    public void setProduct(TradesProductDto product) {
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
