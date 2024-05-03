package ua.gym.domain.trades;

public enum TradesProductCategory {
    BLADE("Основа"),
    RUBBER("Накладка"),
    BALL("М'яч"),
    CASE("Чохол"),
    OTHER("Інше");

    private final String name;

    TradesProductCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
