package com.SpringMicroservices.ProductService.Service;

import com.SpringMicroservices.ProductService.Model.ProductRequest;
import com.SpringMicroservices.ProductService.Model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse findProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
