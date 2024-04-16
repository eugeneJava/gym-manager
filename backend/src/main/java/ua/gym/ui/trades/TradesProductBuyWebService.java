package ua.gym.ui.trades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.gym.domain.trades.*;
import ua.gym.ui.dtos.trades.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
        List<TradesProductBuy> productBuy = productBuyRepository.findAllByOrderByPurchaseDateDesc();
        return productBuy.stream()
                .map(TradesProductBuyDto::new)
                .sorted(comparing(TradesProductBuyDto::getTrackId, nullsFirst(String::compareTo))
                        .thenComparing(buy -> buy.getParcelId(), nullsFirst(String::compareTo)))
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
    public TradesParcelGroupDto createTradesProductBuy(@RequestBody TradesParcelGroupDto parcelGroupDto) {
        TradesParcelGroup parcelGroup = new TradesParcelGroup(
                parcelGroupDto.getName(),
                parcelGroupDto.getComments(), parcelGroupDto.getTotalBuyPriceInYuan(), parcelGroupDto.getTotalBuyPriceInUah());
        parcelGroup.setTrackId(parcelGroupDto.getTrackId());

        //TODO: add explicit weight fraction
        int totalBought = 0;
        if (parcelGroupDto.isAllProductsSameWeight()) {
          for(TradesProductBuyDto buy : parcelGroupDto.getProductBuys()) {
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

    @PutMapping("/trades/productBuy/{id}")
    @Transactional
    public TradesProductBuyDto updateTradesProductBuy(@PathVariable String id, @RequestBody TradesProductBuyDto productBuyDto) {
        TradesProductBuy productBuy = productBuyRepository.findById(id).orElseThrow();
        productBuy.getParcelGroup().setName(productBuyDto.getName());
        productBuy.getParcelGroup().setComments(productBuyDto.getComments());
        productBuy.getParcelGroup().setTrackId(productBuyDto.getTrackId());
        return new TradesProductBuyDto(productBuy);
    }


    @GetMapping("/trades/productBuy/statistics")
    @Transactional(readOnly = true)
    public List<ProductBuyStatisticsDto> generateProductBuyStatistics() {
        List<ProductBuyStatisticsDto> statisticsDtos = new ArrayList<>();
        productRepository.findAll().forEach(product -> {
            List<TradesProductBuy> productBuys = productBuyRepository.findAllByProduct(product);
            List<TradesProductBuy> deliveredProductBuys = productBuys.stream()
                    .filter(buy -> nonNull(buy.getParcelGroup().getParcel()))
                    .collect(Collectors.toList());

            if (deliveredProductBuys.isEmpty()) {
                return;
            }

            ProductBuyStatisticsDto dto = new ProductBuyStatisticsDto(product);

            deliveredProductBuys.forEach(buy -> {
                ProductBuyInParcelDto productBuyInParcel
                        = new ProductBuyInParcelDto(
                                buy.getUnitBuyPrice(),
                        buy.getParcelGroup().getParcel().getStartedDeliveryAt(),
                        buy.getParcelGroup().getParcel().getDeliveryType());
                dto.getProductsBuyInParcel().add(productBuyInParcel);
            });

            statisticsDtos.add(dto);
        });

        return statisticsDtos;
    }
}
