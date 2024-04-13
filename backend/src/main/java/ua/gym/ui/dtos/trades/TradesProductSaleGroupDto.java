package ua.gym.ui.dtos.trades;

import ua.gym.domain.trades.TradesProductSaleGroup;

public class TradesProductSaleGroupDto {
    private String id;
    // Assuming additional fields exist in TradesProductSaleGroup that should be mapped

    public TradesProductSaleGroupDto() {
    }

    // Constructor mapping fields from TradesProductSaleGroup entity
    public TradesProductSaleGroupDto(TradesProductSaleGroup productSaleGroup) {
        this.id = productSaleGroup.getId();
        // Map other fields as needed
    }

    public String getId() {
        return id;
    }

    // Getters and Setters
}
