package ua.gym.ui.dtos.trades;

import ua.gym.domain.trades.TradesProductCategory;

public class TradesProductCategoryDto {
    private TradesProductCategory id;
    private String name;

    public TradesProductCategoryDto() {
    }

    public TradesProductCategoryDto(TradesProductCategory category) {
        this.id = category;
        this.name = category.getName();
    }

    public TradesProductCategory getId() {
        return id;
    }

    public void setId(TradesProductCategory id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
