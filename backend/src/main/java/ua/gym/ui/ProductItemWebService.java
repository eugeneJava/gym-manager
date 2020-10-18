package ua.gym.ui;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.gym.domain.Product;
import ua.gym.domain.ProductDeleteLog;
import ua.gym.domain.ProductDeleteLogRepository;
import ua.gym.domain.ProductItem;
import ua.gym.domain.ProductItemRepository;
import ua.gym.domain.ProductRepository;
import ua.gym.ui.dtos.ProductItemDto;

@RestController
public class ProductItemWebService {
   private final ProductItemRepository productItemRepository;
   private final ProductDeleteLogRepository productDeleteLogRepository;
   private final ProductRepository productRepository;

   @Autowired
   public ProductItemWebService(ProductItemRepository productItemRepository, ProductDeleteLogRepository productDeleteLogRepository, ProductRepository productRepository) {
      this.productItemRepository = productItemRepository;
      this.productDeleteLogRepository = productDeleteLogRepository;
      this.productRepository = productRepository;
   }

   @GetMapping({"productItems/forToday"})
   @Transactional
   public List<ProductItemDto> getProductsForCurrentDay() {
      List<ProductItem> products = this.productItemRepository.getProductsForCurrentDay();
      List<ProductItemDto> productDtos = (List)products.stream().map(ProductItemDto::new).collect(Collectors.toList());
      return productDtos;
   }

   @PostMapping({"productItems"})
   @Transactional
   public ProductItemDto addProduct(@RequestBody ProductItemDto dto) {
      Product product = this.productRepository.getById(dto.getProduct().getId());
      ProductItem productItem = new ProductItem(product, dto.isPaid(), dto.getTableNumber());
      this.productItemRepository.add(productItem);
      return new ProductItemDto(productItem);
   }

   @PutMapping({"productItems/{id}/markPaid"})
   @Transactional
   public ProductItemDto markPaid(@PathVariable String id) {
      ProductItem productItem = this.productItemRepository.getById(id);
      productItem.markPaid();
      return new ProductItemDto(productItem);
   }

   @DeleteMapping({"productItems/{id}"})
   @Transactional
   public void deleteProduct(@PathVariable String id) {
      ProductItem productItem = this.productItemRepository.getById(id);
      this.productDeleteLogRepository.add(new ProductDeleteLog(productItem));
      this.productItemRepository.delete(productItem);
   }
}
