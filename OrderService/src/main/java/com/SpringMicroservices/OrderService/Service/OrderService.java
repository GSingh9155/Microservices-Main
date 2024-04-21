package com.SpringMicroservices.OrderService.Service;

import com.SpringMicroservices.OrderService.Model.OrderRequest;
import com.SpringMicroservices.OrderService.Model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
