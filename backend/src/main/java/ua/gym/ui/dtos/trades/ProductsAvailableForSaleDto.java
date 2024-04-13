package ua.gym.ui.dtos.trades;

public class ProductsAvailableForSaleDto {
    private TradesProductDto product;
    private long amountToSell;

    public ProductsAvailableForSaleDto(TradesProductDto product, long amountToSell) {
        this.product = product;
        this.amountToSell = amountToSell;
    }

    public TradesProductDto getProduct() {
        return product;
    }

    public long getAmountToSell() {
        return amountToSell;
    }
}
