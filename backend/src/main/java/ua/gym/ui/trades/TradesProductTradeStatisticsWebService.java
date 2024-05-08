package ua.gym.ui.trades;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.gym.domain.trades.*;
import ua.gym.ui.dtos.trades.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.*;
import static java.util.Objects.nonNull;
import static ua.gym.utils.NumberUtils.divide;
import static ua.gym.utils.NumberUtils.v;

@RestController
public class TradesProductTradeStatisticsWebService {

    private final TradesProductBuyRepository productBuyRepository;
    private final TradesProductSaleRepository tradesProductSaleRepository;
    private final TradesParcelRepository parcelRepository;

    private final BigDecimal INITIAL_SUM = new BigDecimal(32224);

    public TradesProductTradeStatisticsWebService(
                                                  TradesProductBuyRepository productBuyRepository,
                                                  TradesProductSaleRepository tradesProductSaleRepository, TradesParcelRepository parcelRepository) {
        this.productBuyRepository = productBuyRepository;
        this.tradesProductSaleRepository = tradesProductSaleRepository;
        this.parcelRepository = parcelRepository;
    }

    @GetMapping("/trades/history")
    @Transactional(readOnly = true)
    public ProductTradeStatisticsDto getProductTradeStatistics() {
        ProductTradeStatisticsDto tradeStatistics = new ProductTradeStatisticsDto();
        List<ProductTradeHistoryItemDto> historyItems = new ArrayList<>();
        List<TradesProductBuy> allBuys = productBuyRepository.findAllByOrderByPurchaseDateDesc();
        for (TradesProductBuy buy : allBuys) {
            ProductTradeHistoryItemDto productBuy = new ProductTradeHistoryItemDto();
            productBuy.setProductName(buy.getProduct().getName());
            productBuy.setDirection(TradeDirection.BUY);
            productBuy.setDate(buy.getPurchaseDate());
            productBuy.setAmount(buy.getProductUnits().size());
            productBuy.setPrice(buy.getTotalBuyPriceInUah());

            historyItems.add(productBuy);
        }

        List<TradesParcel> allParcels = parcelRepository.findAllByOrderByStartedDeliveryAtDesc();
        for (TradesParcel parcel : allParcels) {
            ProductTradeHistoryItemDto delivery = new ProductTradeHistoryItemDto();
            delivery.setProductName("Оплата доставки");
            delivery.setDate(parcel.getStartedDeliveryAt());
            delivery.setDirection(TradeDirection.BUY);
            delivery.setPrice(parcel.getDeliveryPrice());

            historyItems.add(delivery);
        }

        List<TradesProductSale> allSales = tradesProductSaleRepository.findAllByOrderBySoldAtDesc();
        for (TradesProductSale sale : allSales) {
            ProductTradeHistoryItemDto productSale = new ProductTradeHistoryItemDto();
            productSale.setProductName(sale.getProduct().getName());
            productSale.setDirection(TradeDirection.SELL);
            productSale.setDate(sale.getSoldAt());
            productSale.setAmount(sale.getProductUnits().size());
            productSale.setPrice(sale.getSellPrice());

            historyItems.add(productSale);
        }

        List<ProductTradeHistoryItemDto> orderedHistory = historyItems.stream().sorted(comparing(ProductTradeHistoryItemDto::getDate, reverseOrder())).collect(Collectors.toList());
        BigDecimal totalBought = BigDecimal.ZERO;
        BigDecimal totalSold = BigDecimal.ZERO;
        BigDecimal totalProfit = BigDecimal.ZERO;

        //TODO: add collector
        for (ProductTradeHistoryItemDto item : orderedHistory) {
            if (item.getDirection() == TradeDirection.BUY) {
                totalBought = totalBought.add(item.getPrice());
            } else {
                totalSold = totalSold.add(item.getPrice());
            }
        }

        totalProfit = totalSold.subtract(totalBought);

        tradeStatistics.setHistory(orderedHistory);
        tradeStatistics.setTotalBought(totalBought);
        tradeStatistics.setTotalSold(totalSold);
        tradeStatistics.setTotalProfit(INITIAL_SUM.add(totalProfit));

        return tradeStatistics;
    }
}
