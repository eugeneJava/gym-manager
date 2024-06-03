package ua.gym.ui.internal;

import ua.gym.ui.dtos.trades.TradesProductSaleDto;

public class ProductSaleApplicationEvent {
    private TradesProductSaleDto sale;

    public ProductSaleApplicationEvent(TradesProductSaleDto sale) {
        this.sale = sale;
    }

    public TradesProductSaleDto getSale() {
        return sale;
    }
}
