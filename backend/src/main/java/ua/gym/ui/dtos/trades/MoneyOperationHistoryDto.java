package ua.gym.ui.dtos.trades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MoneyOperationHistoryDto {
    private BigDecimal totalSum;
    private List<MoneyOperationDto> moneyOperations = new ArrayList<>();

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }

    public List<MoneyOperationDto> getMoneyOperations() {
        return moneyOperations;
    }

    public void setMoneyOperations(List<MoneyOperationDto> moneyOperations) {
        this.moneyOperations = moneyOperations;
    }
}
