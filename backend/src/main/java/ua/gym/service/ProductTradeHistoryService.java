package ua.gym.service;

import org.springframework.stereotype.Service;
import ua.gym.domain.trades.*;
import ua.gym.ui.dtos.trades.ProductTradeHistoryItemDto;
import ua.gym.ui.dtos.trades.ProductTradeStatisticsDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;
import static ua.gym.domain.trades.TradeDirection.BUY;
import static ua.gym.domain.trades.TradesProductSaleGroup.createEmptyGroup;

@Service
public class ProductTradeHistoryService {

    private final TradesProductBuyRepository productBuyRepository;
    private final TradesProductSaleRepository tradesProductSaleRepository;
    private final TradesParcelRepository parcelRepository;

    private final BigDecimal INITIAL_SUM = new BigDecimal(32224);

    public ProductTradeHistoryService(
            TradesProductBuyRepository productBuyRepository,
            TradesProductSaleRepository tradesProductSaleRepository,
            TradesParcelRepository parcelRepository) {
        this.productBuyRepository = productBuyRepository;
        this.tradesProductSaleRepository = tradesProductSaleRepository;
        this.parcelRepository = parcelRepository;
    }

    public ProductTradeStatisticsDto getProductTradeStatistics() {
        ProductTradeStatisticsDto tradeStatistics = new ProductTradeStatisticsDto();
        List<ProductTradeHistoryItemDto> historyItems = new ArrayList<>();
        List<TradesProductBuy> allBuys = productBuyRepository.findAllByOrderByPurchaseDateDesc();
        for (TradesProductBuy buy : allBuys) {
            ProductTradeHistoryItemDto productBuy = new ProductTradeHistoryItemDto();
            productBuy.setProductNames(List.of(buy.getProduct().getName()));
            productBuy.setDirection(BUY);
            productBuy.setDate(buy.getPurchaseDate());
            productBuy.setAmount(buy.getProductUnits().size());
            productBuy.setPrice(buy.getTotalBuyPriceInUah());
            productBuy.setComments(buy.comments());

            historyItems.add(productBuy);
        }

        List<TradesParcel> allParcels = parcelRepository.findAllByOrderByStartedDeliveryAtDesc();
        for (TradesParcel parcel : allParcels) {
            ProductTradeHistoryItemDto delivery = new ProductTradeHistoryItemDto();
            delivery.setProductNames(List.of("Оплата доставки"));
            delivery.setDate(parcel.getStartedDeliveryAt());
            delivery.setDirection(BUY);
            delivery.setPrice(parcel.getDeliveryPrice());

            historyItems.add(delivery);
        }

        List<TradesProductSale> allSales = tradesProductSaleRepository.findAllByOrderBySoldAtDesc();
        Map<TradesProductSaleGroup, List<TradesProductSale>> salesByGroup = new HashMap<>();

        for (TradesProductSale sale : allSales) {
            TradesProductSaleGroup group = sale.getProductSaleGroup().orElseGet(() -> {
                TradesProductSaleGroup emptyGroup = createEmptyGroup();
                sale.setProductSaleGroup(emptyGroup);
                return emptyGroup;
            });

            salesByGroup.computeIfAbsent(group, k -> new ArrayList<>()).add(sale);
        }

        for (Map.Entry<TradesProductSaleGroup, List<TradesProductSale>> entry : salesByGroup.entrySet()) {
            TradesProductSaleGroup group = entry.getKey();
            ProductTradeHistoryItemDto productSale = new ProductTradeHistoryItemDto();
            productSale.setProductNames(group.getNames());
            productSale.setDirection(TradeDirection.SELL);
            productSale.setDate(group.getSoldAt());
            productSale.setAmount(group.getTotalItems());
            productSale.setPrice(group.calculateTotalSellPrice());
            productSale.setComments(group.getComments());

            historyItems.add(productSale);
        }


        List<ProductTradeHistoryItemDto> orderedHistory = historyItems.stream().sorted(comparing(ProductTradeHistoryItemDto::getDate)).collect(toList());
        BigDecimal totalBought = BigDecimal.ZERO;
        BigDecimal totalSold = BigDecimal.ZERO;
        BigDecimal totalProfit = BigDecimal.ZERO;

        BigDecimal currentAmount = INITIAL_SUM;
        for (ProductTradeHistoryItemDto item : orderedHistory) {
            if (item.getDirection() == BUY) {
                totalBought = totalBought.add(item.getPrice());
                currentAmount = currentAmount.subtract(item.getPrice());
            } else {
                totalSold = totalSold.add(item.getPrice());
                currentAmount = currentAmount.add(item.getPrice());
            }

            item.setCurrentAmountOfMoney(currentAmount);
        }

        totalProfit = totalSold.subtract(totalBought);

        tradeStatistics.setHistory(orderedHistory.stream().sorted(comparing(ProductTradeHistoryItemDto::getDate, reverseOrder())).collect(toList()));
        tradeStatistics.setTotalBought(totalBought);
        tradeStatistics.setTotalSold(totalSold);
        tradeStatistics.setCurrentTotalAmountOfMoney(INITIAL_SUM.add(totalProfit));
        tradeStatistics.setTotalProfit(totalProfit);

        return tradeStatistics;
    }
}