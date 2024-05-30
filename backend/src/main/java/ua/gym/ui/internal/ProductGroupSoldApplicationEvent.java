package ua.gym.ui.internal;

import ua.gym.ui.dtos.trades.TradesProductSaleGroupDto;

public class ProductGroupSoldApplicationEvent {
    private TradesProductSaleGroupDto group;

    public ProductGroupSoldApplicationEvent(TradesProductSaleGroupDto group) {
        this.group = group;
    }

    public TradesProductSaleGroupDto getGroup() {
        return group;
    }
}
