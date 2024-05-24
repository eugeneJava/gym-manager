package ua.gym.ui.trades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.gym.domain.trades.*;
import ua.gym.ui.dtos.trades.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static ua.gym.domain.trades.SaleGroupType.RACKET;
import static ua.gym.utils.Assertions.assertGreaterThanZero;
import static ua.gym.utils.Assertions.assertState;
import static ua.gym.utils.NumberUtils.divide;
import static ua.gym.utils.NumberUtils.v;

@RestController
public class TradesProductSaleWebService {

    private final TradesProductSaleRepository tradesProductSaleRepository;
    private final TradesProductUnitRepository tradesProductUnitRepository;
    private final TradesProductSaleGroupRepository tradesProductSaleGroupRepository;
    private final TradesProductRepository tradesProductRepository;

    @Autowired
    public TradesProductSaleWebService(TradesProductSaleRepository tradesProductSaleRepository,
                                       TradesProductUnitRepository tradesProductUnitRepository,
                                       TradesProductSaleGroupRepository tradesProductSaleGroupRepository, TradesProductRepository tradesProductRepository) {
        this.tradesProductSaleRepository = tradesProductSaleRepository;
        this.tradesProductUnitRepository = tradesProductUnitRepository;
        this.tradesProductSaleGroupRepository = tradesProductSaleGroupRepository;
        this.tradesProductRepository = tradesProductRepository;
    }

    @Transactional(readOnly = true)
    @GetMapping("/trades/productSale")
    public List<TradesProductSaleDto> getAllTradesProductSale() {
        return tradesProductSaleRepository.findAll().stream()
                .sorted(Comparator.comparing(TradesProductSale::getSoldAt).reversed()
                        .thenComparing(sale -> sale.getProductSaleGroup().map(TradesProductSaleGroup::getId).orElse(null), Comparator.nullsLast(Comparator.naturalOrder())))
                .map(TradesProductSaleDto::new)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    @GetMapping("/trades/productSale/{id}")
    public TradesProductSaleDto getTradesProductSale(@PathVariable String id) {
        TradesProductSale sale = tradesProductSaleRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Sale not found"));
        return new TradesProductSaleDto(sale);
    }

    @Transactional
    @PostMapping("/trades/productSale")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTradesProductSale(@RequestBody TradesProductSaleDto dto) {
        TradesProduct product = tradesProductRepository.findById(dto.getProduct().getId()).orElseThrow();
        List<TradesProductUnit> notSoldUnits = tradesProductUnitRepository.getAvailableForSaleProductUnits(product);
        List<TradesProductUnit> unitsToSell = notSoldUnits.subList(0, dto.getAmountToSell());

        TradesProductSale sale = new TradesProductSale(dto.getSellPrice(), dto.getSoldAt(), dto.getComments());
        unitsToSell.forEach(unit -> sale.addProductUnit(unit));

        assertState(!sale.getProductUnits().isEmpty(), "At least one product unit should be sold.");
        tradesProductSaleRepository.save(sale);
    }


    @Transactional
    @PutMapping("/trades/productSale/{id}")
    public TradesProductSaleDto updateTradesProductSale(@PathVariable String id, @RequestBody TradesProductSaleDto dto) {
        TradesProductSale sale = tradesProductSaleRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Sale not found"));


        //sale.setSellPrice(dto.getSellPrice());
        //sale.setSoldAt(dto.getSoldAt());

        return new TradesProductSaleDto(sale);
    }


    @Transactional
    @PostMapping("/trades/productSale/group")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTradesProductSaleGroup(@RequestBody TradesProductSaleGroupDto dto) {
        TradesProductSaleGroup group = new TradesProductSaleGroup(dto.getType());
        tradesProductSaleGroupRepository.save(group);

        dto.getProductSales().forEach(saleDto -> {
            TradesProduct product = tradesProductRepository.findById(saleDto.getProduct().getId()).orElseThrow();
            List<TradesProductUnit> notSoldUnits = tradesProductUnitRepository.getAvailableForSaleProductUnits(product);
            List<TradesProductUnit> unitsToSell = notSoldUnits.subList(0, saleDto.getAmountToSell());

            TradesProductSale sale = new TradesProductSale(group, saleDto.getSellPrice(), dto.getSoldAt(), dto.getComments());
            TradesProductSale savedSale = tradesProductSaleRepository.save(sale);
            unitsToSell.forEach(unit -> savedSale.addProductUnit(unit));
            tradesProductSaleRepository.flush();

            assertState(!savedSale.getProductUnits().isEmpty(), "At least one product unit should be sold.");
        });

        assertState(!group.getProductSales().isEmpty(), "At least one product sale should be added to the group.");
    }


    @GetMapping("/trades/productSale/statistics")
    @Transactional(readOnly = true)
    public TradesProductSaleTotalStatisticsDto getProductSaleStatistics() {
        List<TradesProductSale> allSales = tradesProductSaleRepository.findAllByOrderBySoldAtDesc();
        TradesProductSaleTotalStatisticsDto statistics =
                new TradesProductSaleTotalStatisticsDto(
                        allSales.isEmpty() ? null : allSales.get(allSales.size() - 1).getSoldAt(),
                        LocalDate.now());

        Map<TradesProduct, ProductSaleStatDto> productStats = new HashMap<>();
        BigDecimal totalProfit = BigDecimal.ZERO;
        BigDecimal totalSold = BigDecimal.ZERO;
        for (TradesProductSale sale : allSales) {
            TradesProduct product = sale.getProduct();
            productStats.putIfAbsent(product, new ProductSaleStatDto(product.getName()));

            ProductSaleStatDto productStat = productStats.get(product);
            productStat.setSoldAmount(productStat.getSoldAmount().add(v(sale.getProductUnits().size())));
            sale.calculateTotalProfit().ifPresent(profit -> productStat.setTotalProfit(productStat.getTotalProfit().add(profit)));

            totalProfit = totalProfit.add(sale.calculateTotalProfit().orElse(BigDecimal.ZERO));
            totalSold = totalSold.add(sale.getSellPrice());

            productStat.getSaleStatistics().add(new TradesProductSaleStatisticsDto(sale));
        }


        statistics.setTotalProfit(totalProfit);
        statistics.setTotalSold(totalSold);

        List<ProductSaleStatDto> productSaleStats = productStats
                .values().stream()
                .peek(stat -> {
                    stat.setAvgProfitPerUnit(divide(stat.getTotalProfit(), stat.getSoldAmount()));
                })
                .sorted(Comparator.comparing(ProductSaleStatDto::getAvgProfitPerUnit).reversed()).collect(toList());
        statistics.setProductStat(productSaleStats);

        return statistics;
    }


    @PostMapping("/trades/productSale/sellRacket")
    @ResponseStatus(HttpStatus.CREATED)
    public void sellRacket(@RequestBody RacketSellDto racketSellDto) {
        TradesProductSaleGroup group = new TradesProductSaleGroup(RACKET);
        tradesProductSaleGroupRepository.save(group);

        assertGreaterThanZero(racketSellDto.getSellPrice());

        TradesProduct blade = tradesProductRepository.findById(racketSellDto.getBlade().getId()).orElseThrow();
        TradesProduct rubber1 = tradesProductRepository.findById(racketSellDto.getRubber1().getId()).orElseThrow();
        TradesProduct rubber2 = tradesProductRepository.findById(racketSellDto.getRubber2().getId()).orElseThrow();

        BigDecimal sellPrice = racketSellDto.getSellPrice();
        BigDecimal recommendedSellPrice = blade.getRecommendedPrice()
                .add(rubber1.getRecommendedPrice())
                .add(rubber2.getRecommendedPrice());

        BigDecimal priceDiff = sellPrice.subtract(recommendedSellPrice);
        BigDecimal amountOfItemsInSale = v(3);
        BigDecimal diffForEachProduct = divide(priceDiff, amountOfItemsInSale);


        sellProduct(group, blade, diffForEachProduct);
        sellProduct(group, rubber1, diffForEachProduct);
        sellProduct(group, rubber2, diffForEachProduct);

        assertState(!group.getProductSales().isEmpty(), "At least one product sale should be added to the group.");

        throw new RuntimeException("Chamba mambe");
    }

    private void sellProduct(TradesProductSaleGroup group, TradesProduct product, BigDecimal diffForEachProduct) {
        TradesProductUnit notSoldBlade = tradesProductUnitRepository.getAvailableForSaleProductUnits(product).stream().findFirst().orElseThrow();
        BigDecimal finalSellPrice = product.getRecommendedPrice().add(diffForEachProduct);
        TradesProductSale sale = new TradesProductSale(group, finalSellPrice, LocalDate.now(), "Sell from bot");
        TradesProductSale savedSale = tradesProductSaleRepository.save(sale);
        savedSale.addProductUnit(notSoldBlade);
        assertState(!savedSale.getProductUnits().isEmpty(), "At least one product unit should be sold.");
        tradesProductSaleRepository.flush();
    }
}
