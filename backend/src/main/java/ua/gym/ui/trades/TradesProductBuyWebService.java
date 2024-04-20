package ua.gym.ui.trades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.gym.domain.trades.*;
import ua.gym.ui.dtos.trades.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.*;
import static java.util.Objects.nonNull;
import static ua.gym.utils.NumberUtils.divide;
import static ua.gym.utils.NumberUtils.v;

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
    List<TradesProductBuy> productBuy = productBuyRepository.findAll();
    return productBuy.stream()
            .sorted(Comparator.comparing((TradesProductBuy b) -> !b.getParcelGroup().isPresent())
                    .thenComparing(b -> b.getParcelGroup().isPresent() && b.getParcelGroup().get().getParcel() != null)

                    .thenComparing(TradesProductBuy::getPurchaseDate, reverseOrder())
                    .thenComparing(b -> b.getParcelGroup().map(TradesParcelGroup::getTrackId).orElse(null), nullsLast(naturalOrder())))
            .map(TradesProductBuyDto::new)
            .collect(Collectors.toList());
}

    @GetMapping("/trades/productBuy/{id}")
    @Transactional(readOnly = true)
    public TradesProductBuyDto getTradesProductBuy(@PathVariable String id) {
        TradesProductBuy productBuy = productBuyRepository.findById(id).orElseThrow();
        return new TradesProductBuyDto(productBuy);
    }

    @PostMapping("/trades/productBuy/withParcel")
    @Transactional
    public TradesParcelGroupDto createTradesProductBuyWithParcel(@RequestBody TradesParcelGroupDto parcelGroupDto) {
        TradesParcelGroup parcelGroup = new TradesParcelGroup(
                parcelGroupDto.getName(),
                parcelGroupDto.getComments(), parcelGroupDto.getTotalBuyPriceInYuan(), parcelGroupDto.getTotalBuyPriceInUah());
        parcelGroup.setTrackId(parcelGroupDto.getTrackId());

        //TODO: add explicit weight fraction
        int totalBought = 0;
        if (parcelGroupDto.isAllProductsSameWeight()) {
            for (TradesProductBuyDto buy : parcelGroupDto.getProductBuys()) {
                totalBought += buy.getAmount();
            }
        }

        BigDecimal total = v(totalBought);
        parcelGroupDto.getProductBuys().forEach(productBuyDto -> {
            TradesProduct product = productRepository.findById(productBuyDto.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            TradesProductBuy productBuyEntity = new TradesProductBuy(
                    parcelGroup,
                    productBuyDto.getTotalBuyPriceInYuan(),
                    productBuyDto.getTotalBuyPriceInUah(),
                    product,
                    parcelGroupDto.getPurchaseDate(),
                    productBuyDto.getAmount()
            );

            //TODO: add explicit weight fraction
            productBuyEntity.setWeightFraction(divide(v(productBuyDto.getAmount()), total));
        });

        parcelGroup.performIntegrityCheck();
        parcelGroupRepository.save(parcelGroup);
        return new TradesParcelGroupDto(parcelGroup);
    }

    @PostMapping("/trades/productBuy")
    @Transactional
    public TradesProductBuyDto createTradesProductBuy(@RequestBody TradesProductBuyDto productBuyDto) {
        TradesProduct product = productRepository.findById(productBuyDto.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        TradesProductBuy productBuyEntity = new TradesProductBuy(
                product,
                productBuyDto.getPurchaseDate(),
                productBuyDto.getAmount(),
                productBuyDto.getUnitPrice()
        );

        productBuyRepository.save(productBuyEntity);
        return new TradesProductBuyDto(productBuyEntity);
    }


    @PutMapping("/trades/productBuy/{id}")
    @Transactional
    public TradesProductBuyDto updateTradesProductBuy(@PathVariable String id, @RequestBody TradesProductBuyDto productBuyDto) {
        TradesProductBuy productBuy = productBuyRepository.findById(id).orElseThrow();
        //productBuy.getParcelGroup().setName(productBuyDto.getName());
        //productBuy.getParcelGroup().setComments(productBuyDto.getComments());
        //productBuy.getParcelGroup().setTrackId(productBuyDto.getTrackId());
        return new TradesProductBuyDto(productBuy);
    }


    @GetMapping("/trades/productBuy/statistics")
    @Transactional(readOnly = true)
    public List<ProductBuyStatisticsDto> generateProductBuyStatistics() {
        List<ProductBuyStatisticsDto> statisticsDtos = new ArrayList<>();
        productRepository.findAll().forEach(product -> {
            List<TradesProductBuy> productBuys = productBuyRepository.findAllByProduct(product);
            List<TradesProductBuy> deliveredProductBuys = productBuys.stream()
                    .filter(buy -> nonNull(buy.getUnitBuyPriceWithDelivery()))
                    .collect(Collectors.toList());

            if (deliveredProductBuys.isEmpty()) {
                return;
            }

            ProductBuyStatisticsDto dto = new ProductBuyStatisticsDto(product);

            deliveredProductBuys.forEach(buy -> {
                ProductBuyInParcelDto productBuyInParcel
                        = new ProductBuyInParcelDto(
                        buy.getUnitBuyPriceWithDelivery(),
                        buy.getUnitDeliveryPrice(),
                        buy.getPurchaseDate(),
                        buy.getParcelGroup().map(TradesParcelGroup::getParcel).map(TradesParcel::getDeliveryType).orElse(null));
                dto.getProductsBuyInParcel().add(productBuyInParcel);
            });

            dto.getProductsBuyInParcel().sort(comparing(ProductBuyInParcelDto::getParcelFormedDate, nullsFirst(reverseOrder())));
            statisticsDtos.add(dto);
        });

        return statisticsDtos;
    }
}
