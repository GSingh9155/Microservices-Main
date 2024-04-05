package com.SpringMicroservices.OrderService.Repository;

import com.SpringMicroservices.OrderService.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
