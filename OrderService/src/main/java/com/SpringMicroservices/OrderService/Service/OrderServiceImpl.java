package com.SpringMicroservices.OrderService.Service;

import com.SpringMicroservices.OrderService.Entity.OrderEntity;
import com.SpringMicroservices.OrderService.Model.OrderRequest;
import com.SpringMicroservices.OrderService.Repository.OrderRepository;
import com.netflix.discovery.converters.Auto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;


    @Override
    public long placeOrder(OrderRequest orderRequest) {

        //create order entity here and save the data with status order created
        //Product Service - Block Products(Reduce the Quantoty)
        //Call payment service to complete the order -> if SUCCESS -> complete, Else CANCEL

        log.info("Placing order request: {}", orderRequest);

        OrderEntity orderEntity = OrderEntity.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        orderRepository.save(orderEntity);

        log.info("Order placed successfully with order Id: {}", orderEntity
                .getId());

        return orderEntity.getId();
    }
}
