package ua.gym.ui.dtos.trades;

import ua.gym.domain.trades.TradesProduct;

public class TradesProductDto {
    private String id;
    private String code;
    private String name;
    private String comments;

    // Default constructor
    public TradesProductDto() {}

    // Constructor mapping entity to DTO
    public TradesProductDto(TradesProduct product) {
        this.id = product.getId();
        this.code = product.getCode();
        this.name = product.getName();
        this.comments = product.getComments();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getters and setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}