package ua.gym.ui.dtos.trades;

import java.math.BigDecimal;

public class RacketSellDto {
    private TradesProductDto blade;
    private TradesProductDto rubber1;
    private TradesProductDto rubber2;
    private BigDecimal sellPrice;

    public TradesProductDto getBlade() {
        return blade;
    }

    public void setBlade(TradesProductDto blade) {
        this.blade = blade;
    }

    public TradesProductDto getRubber1() {
        return rubber1;
    }

    public void setRubber1(TradesProductDto rubber1) {
        this.rubber1 = rubber1;
    }

    public TradesProductDto getRubber2() {
        return rubber2;
    }

    public void setRubber2(TradesProductDto rubber2) {
        this.rubber2 = rubber2;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }
}