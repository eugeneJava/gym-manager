package ua.gym.ui.trades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.gym.domain.trades.*;
import ua.gym.ui.dtos.trades.ProductsAvailableForSaleDto;
import ua.gym.ui.dtos.trades.TradesProductDto;
import ua.gym.ui.dtos.trades.TradesProductUnitDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@RestController
public class TradesProductUnitWebService {

    private final TradesProductSaleRepository tradesProductSaleRepository;
    private final TradesProductUnitRepository tradesProductUnitRepository;
    private final TradesProductSaleGroupRepository tradesProductSaleGroupRepository;

    @Autowired
    public TradesProductUnitWebService(TradesProductSaleRepository tradesProductSaleRepository,
                                       TradesProductUnitRepository tradesProductUnitRepository,
                                       TradesProductSaleGroupRepository tradesProductSaleGroupRepository) {
        this.tradesProductSaleRepository = tradesProductSaleRepository;
        this.tradesProductUnitRepository = tradesProductUnitRepository;
        this.tradesProductSaleGroupRepository = tradesProductSaleGroupRepository;
    }

    @Transactional(readOnly = true)
    @GetMapping("/trades/productUnit/available")
    public List<TradesProductUnitDto> getAllTradesProductSale() {
        return tradesProductUnitRepository.getAvailableProductUnits().stream()
                .map(TradesProductUnitDto::new)
                .sorted(comparing(unit -> unit.getProduct().getName()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @GetMapping("/trades/productUnit/availableForSale")
    public List<ProductsAvailableForSaleDto> getAvailableForSaleProducts() {
        Map<TradesProduct, Long> availableForSaleProducts = tradesProductUnitRepository.getAvailableProductUnits().stream()
                .collect(groupingBy(TradesProductUnit::getProduct, counting()));

        List<ProductsAvailableForSaleDto> products = availableForSaleProducts
                .entrySet().stream()
                .map((entry) -> new ProductsAvailableForSaleDto(new TradesProductDto(entry.getKey()), entry.getValue()))
                .sorted(comparing(item -> item.getProduct().getName()))
                .collect(Collectors.toList());

        return products;

    }


}
