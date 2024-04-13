package ua.gym.ui.trades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import ua.gym.domain.trades.*;
import ua.gym.ui.dtos.trades.*;
import ua.gym.utils.Assertions;

import java.util.List;
import java.util.stream.Collectors;

import static ua.gym.utils.Assertions.assertState;

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
                .map(TradesProductSaleDto::new)
                .collect(Collectors.toList());
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
        List<TradesProductUnit> notSoldUnits = tradesProductUnitRepository.getNotSoldProductUnits(product);
        List<TradesProductUnit> unitsToSell = notSoldUnits.subList(0, dto.getAmountToSell());

        TradesProductSale sale = new TradesProductSale(dto.getSellPrice(), dto.getSoldAt());
        unitsToSell.forEach(unit -> sale.addProductUnit(unit));

        assertState(!sale.getProductUnits().isEmpty(), "At least one product unit should be sold.");
        tradesProductSaleRepository.save(sale);
    }

    @Transactional
    @PutMapping("/trades/productSale/{id}")
    public TradesProductSaleDto updateTradesProductSale(@PathVariable String id, @RequestBody TradesProductSaleDto dto) {
        TradesProductSale sale = tradesProductSaleRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Sale not found"));

        if (dto.getProductSaleGroup() != null) {
            TradesProductSaleGroup productSaleGroup = tradesProductSaleGroupRepository.findById(dto.getProductSaleGroup().getId())
                    .orElseThrow(() -> new IllegalStateException("ProductSaleGroup not found"));
            sale.setProductSaleGroup(productSaleGroup);
        }

        //sale.setSellPrice(dto.getSellPrice());
        //sale.setSoldAt(dto.getSoldAt());

        return new TradesProductSaleDto(sale);
    }
}
