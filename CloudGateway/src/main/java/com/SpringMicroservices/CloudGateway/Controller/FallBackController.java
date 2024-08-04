package com.SpringMicroservices.CloudGateway.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    @GetMapping("/orderServiceFallBack")
    public String orderServiceFallBack(){
        return "Order Service is not available at this time, Please try again later";

    }

    @GetMapping("/paymentServiceFallBack")
    public String paymentServiceFallBack(){
        return "Payment Service is not available at this time, Please try again later";

    }

    @GetMapping("/productServiceFallBack")
    public String productServiceFallBack(){
        return "Product Service is not available at this time, Please try again later";

    }
}
