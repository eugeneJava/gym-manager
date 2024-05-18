package ua.gym.ui.internal;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.gym.domain.trades.TradesParcel;
import ua.gym.domain.trades.TradesParcelRepository;
import ua.gym.service.DeliveryDurationProvider;
import ua.gym.service.ProductTradeHistoryService;
import ua.gym.ui.dtos.trades.ProductTradeStatisticsDto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/internal-api")
public class TradesProductTradeStatisticsInternalWebService {

    private final ProductTradeHistoryService productTradeHistoryService;
    private final TradesParcelRepository tradesParcelRepository;
    private final DeliveryDurationProvider deliveryDurationProvider;

    public TradesProductTradeStatisticsInternalWebService(
            ProductTradeHistoryService productTradeHistoryService, TradesParcelRepository tradesParcelRepository, DeliveryDurationProvider deliveryDurationProvider) {
        this.productTradeHistoryService = productTradeHistoryService;
        this.tradesParcelRepository = tradesParcelRepository;
        this.deliveryDurationProvider = deliveryDurationProvider;
    }

    @GetMapping("/trades/history")
    @Transactional(readOnly = true)
    public ProductTradeStatisticsDto getProductTradeStatistics() {
        return productTradeHistoryService.getProductTradeStatistics();
    }

    @GetMapping("/trades/parcel/delivering")
    @Transactional(readOnly = true)
    public List<ParcelDeliveryInfoDto> getAllTradesParcels() {
        List<TradesParcel> parcels = tradesParcelRepository
                .findAllDeliveringParcels()
                .stream()
                .sorted(Comparator.comparing((TradesParcel p) -> nonNull(p.getDeliveredAt()))
                        .thenComparing(TradesParcel::getStartedDeliveryAt, Comparator.reverseOrder())).collect(Collectors.toList());

        return parcels.stream().map(parcel -> {
            ParcelDeliveryInfoDto parcelDeliveryInfoDto = new ParcelDeliveryInfoDto(
                    parcel.getDeliveryType().name(),
                    parcel.getTotalPrice(),
                    parcel.getStartedDeliveryAt(),
                    deliveryDurationProvider.calculateApproximateDeliveryDate(parcel),
                    parcel.getDeliveryDurationFormatted());

            parcelDeliveryInfoDto.setContent(
                    parcel.getParcelGroups().stream().map(ParcelContentItemDto::new)
                    .sorted(Comparator.comparing(ParcelContentItemDto::getProductName))
                    .collect(Collectors.toList())
            );

            return parcelDeliveryInfoDto;
        }).collect(Collectors.toList());
    }


}