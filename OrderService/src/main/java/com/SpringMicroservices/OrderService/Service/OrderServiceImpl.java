package com.SpringMicroservices.OrderService.Service;

import com.SpringMicroservices.OrderService.Entity.OrderEntity;
import com.SpringMicroservices.OrderService.Exception.CustomException;
import com.SpringMicroservices.OrderService.External.Client.PaymentService;
import com.SpringMicroservices.OrderService.External.Client.ProductService;
import com.SpringMicroservices.OrderService.External.Request.PaymentRequest;
import com.SpringMicroservices.OrderService.External.Response.PaymentResponse;
import com.SpringMicroservices.OrderService.Model.OrderRequest;
import com.SpringMicroservices.OrderService.Model.OrderResponse;
import com.SpringMicroservices.OrderService.Repository.OrderRepository;
import com.SpringMicroservices.ProductService.Model.ProductResponse;
import com.netflix.discovery.converters.Auto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public long placeOrder(OrderRequest orderRequest) {

        //create order entity here and save the data with status order created
        //Product Service - Block Products(Reduce the Quantity)
        //Call payment service to complete the order -> if SUCCESS -> complete, Else CANCEL

        log.info("Placing order request: {}", orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());
        log.info("Order quantity updated in DB");

        log.info("Creating order with status CREATED");
        OrderEntity orderEntity = OrderEntity.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        orderEntity = orderRepository.save(orderEntity);

        log.info("Calling Payment service to complete the Payment");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                        .orderId(orderEntity.getId())
                                .paymentMode(orderRequest.getPaymentMode())
                                        .amount(orderRequest.getTotalAmount())
                                                .build();

        String orderStatus = null;

        try{
            paymentService.doPayment(paymentRequest);
            log.info("Payment done successfully, Changing payment status to PLACED");
            orderStatus = "PLACED";
        }
        catch (Exception e){
            log.error("Error occurred in payment, Changing order status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }

        orderEntity.setOrderStatus(orderStatus);

        orderRepository.save(orderEntity);

        log.info("Order placed successfully with order Id: {}", orderEntity
                .getId());

        return orderEntity.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {

        log.info("Get Order details for Order ID: {}", orderId);

        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException("Order not found with Order ID: " + orderId, "NOT_FOUND", 404));

        log.info("Invoking Product Service to get product details for Id: {}", orderEntity.getProductId());

        ProductResponse productResponse = restTemplate.getForObject(
                "http://PRODUCT-SERVICE/products/" + orderEntity.getProductId(),
                ProductResponse.class);

        OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails
                .builder()
                .productName(productResponse.getProductName())
                .productId(productResponse.getProductId())
                .price(productResponse.getPrice())
                .quantity(productResponse.getQuantity())
                .build();

        log.info("Invoking Payment Service to get payment details for Id: {}", orderEntity.getProductId());

        PaymentResponse paymentResponse = restTemplate.getForObject(
                "http://PAYMENT-SERVICE/payment/order/" + orderEntity.getId(),
                PaymentResponse.class);

        OrderResponse.PaymentDetails paymentDetails = OrderResponse.PaymentDetails
                .builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentStatus(paymentResponse.getStatus())
                .paymentMode(paymentResponse.getPaymentMode())
                .paymentDate(paymentResponse.getPaymentDate())
                .build();



        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(orderEntity.getId())
                .orderStatus(orderEntity.getOrderStatus())
                .amount(orderEntity.getAmount())
                .orderDate(orderEntity.getOrderDate())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();

        return orderResponse;
    }
}
