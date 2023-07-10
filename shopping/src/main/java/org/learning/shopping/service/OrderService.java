package org.learning.shopping.service;


import org.learning.shopping.entity.OrderMain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderMain> findAll(Pageable pageable);
    Page<OrderMain> findByBuyerEmail(String email,Pageable pageable);

    OrderMain findOne(Long id);
    OrderMain finish(Long orderId);
    OrderMain cancel(Long orderId);

}
