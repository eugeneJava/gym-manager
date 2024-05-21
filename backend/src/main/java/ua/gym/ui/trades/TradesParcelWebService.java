package ua.gym.ui.trades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.gym.domain.trades.*;
import ua.gym.service.DeliveryDurationProvider;
import ua.gym.service.TStreamBotWevClient;
import ua.gym.ui.dtos.trades.TradesParcelDto;
import ua.gym.ui.internal.ParcelDeliveryInfoDto;
import ua.gym.ui.internal.ParcelSentApplicationEvent;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RestController
public class TradesParcelWebService {

    private final TradesParcelRepository tradesParcelRepository;
    private final TradesParcelGroupRepository tradesParcelGroupRepository;
    private final DeliveryDurationProvider deliveryDurationProvider;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public TradesParcelWebService(TradesParcelRepository tradesParcelRepository,
                                  TradesParcelGroupRepository tradesParcelGroupRepository, DeliveryDurationProvider deliveryDurationProvider, ApplicationEventPublisher applicationEventPublisher) {
        this.tradesParcelRepository = tradesParcelRepository;
        this.tradesParcelGroupRepository = tradesParcelGroupRepository;
        this.deliveryDurationProvider = deliveryDurationProvider;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @GetMapping("/trades/parcel/{id}")
    @Transactional(readOnly = true)
    public TradesParcelDto getTradesParcel(@PathVariable String id) {
        TradesParcel tradesParcel = tradesParcelRepository.findById(id).orElseThrow();
        return new TradesParcelDto(tradesParcel);
    }

    @GetMapping("/trades/parcel")
    @Transactional(readOnly = true)
    public List<TradesParcelDto> getAllTradesParcels() {
        List<TradesParcel> parcels = tradesParcelRepository
                .findAllByOrderByStartedDeliveryAtDesc()
                .stream()
                .sorted(Comparator.comparing((TradesParcel p) -> nonNull(p.getDeliveredAt()))
                        .thenComparing(TradesParcel::getStartedDeliveryAt, Comparator.reverseOrder())).collect(Collectors.toList());

                                return parcels.stream().map(TradesParcelDto::new).collect(Collectors.toList());
    }

    @PostMapping("/trades/parcel")
    @Transactional
    public TradesParcelDto createTradesParcel(@RequestBody TradesParcelDto tradesParcelDto) {
        final TradesParcel tradesParcel = new TradesParcel(
                tradesParcelDto.getWeight(),
                tradesParcelDto.getDeliveryPrice(),
                tradesParcelDto.getStartedDeliveryAt(),
                tradesParcelDto.getDeliveryType());
        tradesParcelDto.getParcelGroups().forEach(pgDto -> {
            TradesParcelGroup parcelGroup = tradesParcelGroupRepository.findById(pgDto.getId()).orElseThrow();
            parcelGroup.setWeight(pgDto.getWeight());
            tradesParcel.addParcelGroup(parcelGroup);
        });

        if (tradesParcel.getParcelGroups().isEmpty()) {
            throw new IllegalStateException("Parcel should contain at least one group");
        }
        TradesParcel savedTradesParcel = tradesParcelRepository.save(tradesParcel);
        savedTradesParcel.calculateTotalPriceForEveryProduct();

        if (nonNull(savedTradesParcel.getStartedDeliveryAt())) {
            applicationEventPublisher.publishEvent(new ParcelSentApplicationEvent(new ParcelDeliveryInfoDto(
                    savedTradesParcel, deliveryDurationProvider.calculateApproximateDeliveryDate(savedTradesParcel))));
        }
        return new TradesParcelDto(savedTradesParcel);
    }

    @PutMapping("/trades/parcel/{id}")
    @Transactional
    public TradesParcelDto updateTradesParcel(@PathVariable String id, @RequestBody TradesParcelDto tradesParcelDto) {
        TradesParcel tradesParcel = tradesParcelRepository.findById(id).orElseThrow();
        tradesParcel.setDeliveredAt(tradesParcelDto.getDeliveredAt());
        tradesParcel.setComments(tradesParcelDto.getComments());
        tradesParcel = tradesParcelRepository.save(tradesParcel);
        return new TradesParcelDto(tradesParcel);
    }
}
