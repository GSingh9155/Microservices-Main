package com.SpringMicroservices.ProductService.Controller;

import com.SpringMicroservices.ProductService.Model.ProductRequest;
import com.SpringMicroservices.ProductService.Model.ProductResponse;
import com.SpringMicroservices.ProductService.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping
     public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest){
         long productId = productService.addProduct(productRequest);
         return new ResponseEntity<>(productId, HttpStatus.CREATED);
     }

     @GetMapping("/{id}")
     public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId){
        ProductResponse productResponse = productService.findProductById(productId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
     }

     @PutMapping("/reduceQuantity/{id}")
     public ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId, @RequestParam long quantity){
        productService.reduceQuantity(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
     }

}
