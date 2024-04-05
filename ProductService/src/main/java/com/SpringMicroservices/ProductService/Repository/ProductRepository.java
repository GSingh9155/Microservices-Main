package com.SpringMicroservices.ProductService.Repository;

import com.SpringMicroservices.ProductService.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
