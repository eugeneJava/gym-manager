package ua.gym.ui.internal;

import ua.gym.domain.trades.TradesParcel;
import ua.gym.domain.trades.TradesParcelGroup;

public class ParcelContentItemDto {
    private String productName;
    private int quantity;

    public ParcelContentItemDto(TradesParcelGroup group) {
        this.productName = group.getName();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
