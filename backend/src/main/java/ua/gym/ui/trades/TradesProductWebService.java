package ua.gym.ui.trades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.gym.domain.trades.TradesProduct;
import ua.gym.domain.trades.TradesProductRepository;
import ua.gym.ui.dtos.trades.TradesProductDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TradesProductWebService {

    @Autowired
    private TradesProductRepository repository;


    @GetMapping("/trades/product")
    public List<TradesProductDto> getAllTradesProductsOrderedByName() {
        List<TradesProduct> products = repository.findAllByOrderByNameAsc();
        return products.stream()
                .map(TradesProductDto::new) // Convert each product to a DTO
                .collect(Collectors.toList());
    }
    // GET method
    @GetMapping("/trades/product/{id}")
    @Transactional(readOnly = true)
    public TradesProductDto getTradesProduct(@PathVariable String id) {
        TradesProduct product = repository.findById(id).orElseThrow();
        return new TradesProductDto(product);
    }

    // POST method for creation
    @PostMapping("/trades/product")
    @Transactional
    public TradesProductDto createTradesProduct(@RequestBody TradesProductDto productDto) {
        TradesProduct product = new TradesProduct(productDto.getCode(), productDto.getName());
        product.setComments(productDto.getComments());
        product = repository.save(product);
        return new TradesProductDto(product);
    }

    // PUT method for updates
    @PutMapping("/trades/product/{id}")
    @Transactional
    public TradesProductDto updateTradesProduct(@PathVariable String id, @RequestBody TradesProductDto productDto) {
        TradesProduct product = repository.findById(id).orElseThrow();
        product.setCode(productDto.getCode());
        product.setName(productDto.getName());
        product.setComments(productDto.getComments());
        return new TradesProductDto(product);
    }
}
