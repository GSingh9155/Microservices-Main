package com.SpringMicroservices.PaymentService.Service;

import com.SpringMicroservices.PaymentService.Model.PaymentRequest;
import com.SpringMicroservices.PaymentService.Model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(long orderId);
}
