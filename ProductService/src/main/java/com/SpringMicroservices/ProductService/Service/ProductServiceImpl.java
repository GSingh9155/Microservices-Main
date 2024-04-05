package com.SpringMicroservices.ProductService.Service;

import com.SpringMicroservices.ProductService.Entity.Product;
import com.SpringMicroservices.ProductService.Exception.ProductServiceCustomException;
import com.SpringMicroservices.ProductService.Model.ProductRequest;
import com.SpringMicroservices.ProductService.Model.ProductResponse;
import com.SpringMicroservices.ProductService.Repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository repository;
    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding Product...");

        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();

        repository.save(product);

        log.info("Product Created!");

        return product.getProductId();
    }

    @Override
    public ProductResponse findProductById(long productId) {
        log.info("Finding product with given productId...");

        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException("Product with given id not found", "PRODUCT_NOT_FOUND"));

        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product,productResponse);

        return productResponse;
    }
}
