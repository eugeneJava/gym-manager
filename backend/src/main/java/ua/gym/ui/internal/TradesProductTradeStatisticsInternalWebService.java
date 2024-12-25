package ua.gym.ui.internal;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.gym.domain.trades.SalesManualMoneyOperation;
import ua.gym.domain.trades.SalesManualMoneyOperationRepository;
import ua.gym.domain.trades.TradesParcel;
import ua.gym.domain.trades.TradesParcelRepository;
import ua.gym.service.DeliveryDurationProvider;
import ua.gym.service.ProductTradeHistoryService;
import ua.gym.ui.dtos.trades.MoneyOperationDto;
import ua.gym.ui.dtos.trades.MoneyOperationHistoryDto;
import ua.gym.ui.dtos.trades.ProductTradeHistoryItemDto;
import ua.gym.ui.dtos.trades.ProductTradeStatisticsDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.util.Comparator.comparing;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/internal-api")
public class TradesProductTradeStatisticsInternalWebService {

    private final ProductTradeHistoryService productTradeHistoryService;
    private final TradesParcelRepository tradesParcelRepository;
    private final DeliveryDurationProvider deliveryDurationProvider;
    private final SalesManualMoneyOperationRepository manualMoneyOperationRepository;

    public TradesProductTradeStatisticsInternalWebService(
            ProductTradeHistoryService productTradeHistoryService, TradesParcelRepository tradesParcelRepository, DeliveryDurationProvider deliveryDurationProvider, SalesManualMoneyOperationRepository manualMoneyOperationRepository) {
        this.productTradeHistoryService = productTradeHistoryService;
        this.tradesParcelRepository = tradesParcelRepository;
        this.deliveryDurationProvider = deliveryDurationProvider;
        this.manualMoneyOperationRepository = manualMoneyOperationRepository;
    }

    @GetMapping("/trades/history")
    @Transactional(readOnly = true)
    public ProductTradeStatisticsDto getProductTradeStatistics() {
        ProductTradeStatisticsDto productTradeStatistics = productTradeHistoryService.getProductTradeStatistics();
        List<ProductTradeHistoryItemDto> items = productTradeStatistics.getHistory().stream().limit(15).collect(toList());
        productTradeStatistics.setHistory(items);
        return productTradeStatistics;
    }

    @GetMapping("/trades/parcel/delivering")
    @Transactional(readOnly = true)
    public List<ParcelDeliveryInfoDto> getAllTradesParcels() {
        List<TradesParcel> parcels = tradesParcelRepository
                .findAllDeliveringParcels()
                .stream()
                .sorted(comparing((TradesParcel p) -> nonNull(p.getDeliveredAt()))
                        .thenComparing(TradesParcel::getStartedDeliveryAt, Comparator.reverseOrder())).collect(toList());

        return parcels.stream().map(parcel -> new ParcelDeliveryInfoDto(
                parcel, deliveryDurationProvider.calculateApproximateDeliveryDate(parcel)
        )).collect(toList());
    }

    @PostMapping("/trades/sale/moneyOperation")
    @Transactional
    public MoneyOperationHistoryDto createMoneyOperation(@RequestBody MoneyOperationDto moneyOperationDto) {
        SalesManualMoneyOperation manualOperation = new SalesManualMoneyOperation(
                moneyOperationDto.getMessage(),
                moneyOperationDto.getSpentAmount(),
                moneyOperationDto.getAddedBy());
        manualMoneyOperationRepository.save(manualOperation);
        return mapMoneyOperationHistory();
    }

    @GetMapping("/trades/sale/moneyOperation")
    @Transactional(readOnly = true)
    public MoneyOperationHistoryDto getMoneyOperationHistory() {
        return mapMoneyOperationHistory();
    }

    private MoneyOperationHistoryDto mapMoneyOperationHistory() {
        List<SalesManualMoneyOperation> records = manualMoneyOperationRepository.findAllByOrderByCreatedAtAsc();

        MoneyOperationHistoryDto moneyOperationHistoryDto = new MoneyOperationHistoryDto();

        List<MoneyOperationDto> dtos = new ArrayList<>();
        BigDecimal current = ZERO;
        for (SalesManualMoneyOperation record : records) {
            BigDecimal currentTotal = current.add(record.getPaid());
            MoneyOperationDto dto = new MoneyOperationDto(record.getPaid(), record.getDescription(), record.getCreatedAt(), record.getAddedBy());
            dto.setCurrentTotal(currentTotal);
            dtos.add(dto);

            current = currentTotal;
        }

        moneyOperationHistoryDto.setTotalSum(current);

        List<MoneyOperationDto> historyOrderedDesc = dtos.stream()
                .sorted(comparing(MoneyOperationDto::getCreatedAt).reversed())
                .limit(15).collect(toList());

        moneyOperationHistoryDto.setMoneyOperations(historyOrderedDesc);
        return moneyOperationHistoryDto;
    }


}