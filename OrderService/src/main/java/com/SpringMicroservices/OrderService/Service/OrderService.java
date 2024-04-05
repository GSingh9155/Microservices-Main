package com.SpringMicroservices.OrderService.Service;

import com.SpringMicroservices.OrderService.Model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
