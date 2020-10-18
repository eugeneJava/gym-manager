package ua.gym.ui;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.gym.domain.Product;
import ua.gym.domain.ProductRepository;
import ua.gym.ui.dtos.ProductDto;

@RestController
public class ProductWebService {
   private final ProductRepository productRepository;

   @Autowired
   public ProductWebService(ProductRepository productRepository) {
      this.productRepository = productRepository;
   }

   @GetMapping({"products"})
   @Transactional
   public List<ProductDto> getProducts() {
      List<Product> products = this.productRepository.getProducts();
      List<ProductDto> productDtos = (List)products.stream().map(ProductDto::new).collect(Collectors.toList());
      return productDtos;
   }
}
