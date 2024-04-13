package ua.gym.ui.trades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.gym.domain.trades.*;
import ua.gym.ui.dtos.trades.TradesProductBuyDto;
import ua.gym.ui.dtos.trades.TradesProductDto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsFirst;

@RestController
public class TradesProductBuyWebService {

    @Autowired
    private TradesProductRepository productRepository;

    @Autowired
    private TradesProductBuyRepository productBuyRepository;
    @Autowired
    private TradesParcelGroupRepository parcelGroupRepository;

    @GetMapping("/trades/productBuy")
    @Transactional(readOnly = true)
    public List<TradesProductBuyDto> getTradesProductBuy() {
        List<TradesProductBuy> productBuy = productBuyRepository.findAllByOrderByPurchaseDateDesc();
        return productBuy.stream()
                .map(TradesProductBuyDto::new)
                .sorted(comparing(TradesProductBuyDto::getTrackId, nullsFirst(String::compareTo))
                        .thenComparing(buy -> buy.getParcelGroup().getParcelId(), nullsFirst(String::compareTo)))
                .collect(Collectors.toList());
    }

    @GetMapping("/trades/productBuy/{id}")
    @Transactional(readOnly = true)
    public TradesProductBuyDto getTradesProductBuy(@PathVariable String id) {
        TradesProductBuy productBuy = productBuyRepository.findById(id).orElseThrow();
        return new TradesProductBuyDto(productBuy);
    }

    @PostMapping("/trades/productBuy")
    @Transactional
    public TradesProductBuyDto createTradesProductBuy(@RequestBody TradesProductBuyDto productBuyDto) {
        TradesProduct product = productRepository.findById(productBuyDto.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        TradesParcelGroup parcelGroup = new TradesParcelGroup(
                productBuyDto.getName(),
                productBuyDto.getComments());
        parcelGroup.setTrackId(productBuyDto.getTrackId());

        TradesProductBuy productBuy = new TradesProductBuy(
                parcelGroup,
                productBuyDto.getTotalBuyPriceInYuan(),
                productBuyDto.getTotalBuyPriceInUah(),
                product,
                productBuyDto.getPurchaseDate(),
                productBuyDto.getAmount()
        );

        productBuyRepository.save(productBuy);

        return new TradesProductBuyDto(productBuy);
    }

    @PutMapping("/trades/productBuy/{id}")
    @Transactional
    public TradesProductBuyDto updateTradesProductBuy(@PathVariable String id, @RequestBody TradesProductBuyDto productBuyDto) {
        TradesProductBuy productBuy = productBuyRepository.findById(id).orElseThrow();
        productBuy.getParcelGroup().setName(productBuyDto.getName());
        productBuy.getParcelGroup().setComments(productBuyDto.getComments());
        productBuy.getParcelGroup().setTrackId(productBuyDto.getTrackId());
        return new TradesProductBuyDto(productBuy);
    }
}
