package ua.gym.ui.trades;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.gym.service.ProductTradeHistoryService;
import ua.gym.ui.dtos.trades.ProductTradeStatisticsDto;

@RestController
public class TradesProductTradeStatisticsWebService {

    private final ProductTradeHistoryService productTradeHistoryService;

    public TradesProductTradeStatisticsWebService(ProductTradeHistoryService productTradeHistoryService) {
        this.productTradeHistoryService = productTradeHistoryService;
    }

    @GetMapping("/trades/history")
    @Transactional(readOnly = true)
    public ProductTradeStatisticsDto getProductTradeStatistics() {
        return productTradeHistoryService.getProductTradeStatistics();
    }
}
