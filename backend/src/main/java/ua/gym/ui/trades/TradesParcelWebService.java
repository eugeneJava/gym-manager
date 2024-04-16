package ua.gym.ui.trades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.gym.domain.trades.*;
import ua.gym.ui.dtos.trades.TradesParcelDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TradesParcelWebService {

    private final TradesParcelRepository tradesParcelRepository;
    private final TradesParcelGroupRepository tradesParcelGroupRepository;

    @Autowired
    public TradesParcelWebService(TradesParcelRepository tradesParcelRepository,
                                  TradesParcelGroupRepository tradesParcelGroupRepository) {
        this.tradesParcelRepository = tradesParcelRepository;
        this.tradesParcelGroupRepository = tradesParcelGroupRepository;
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
        List<TradesParcel> parcels = tradesParcelRepository.findAll();
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
