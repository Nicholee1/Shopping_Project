package org.learning.shopping.service.impl;

import org.learning.shopping.Repository.OrderRepository;
import org.learning.shopping.Repository.ProductInfoRepository;
import org.learning.shopping.entity.OrderMain;
import org.learning.shopping.entity.ProductInOrder;
import org.learning.shopping.entity.ProductInfo;
import org.learning.shopping.enums.OrderStatusEnum;
import org.learning.shopping.enums.ResultEnum;
import org.learning.shopping.exception.MyException;
import org.learning.shopping.service.OrderService;
import org.learning.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    ProductService productService;

    @Override
    public Page<OrderMain> findAll(Pageable pageable) {
        return orderRepository.findAllByOrderByOrderStatusAscCreateTimeDesc(pageable);
    }

    @Override
    public Page<OrderMain> findByBuyerEmail(String email, Pageable pageable) {
        return orderRepository.findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(email, pageable);
    }

    @Override
    public OrderMain findOne(Long id) {
        OrderMain orderMain = orderRepository.findByOrderId(id);
        if (orderMain == null) {
            throw new MyException(ResultEnum.ORDER_NOT_FOUND);
        }
        return orderMain;
    }

    @Override
    public OrderMain finish(Long orderId) {
        OrderMain orderMain = findOne(orderId);
        if (!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
        }
        orderMain.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderRepository.save(orderMain);
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    public OrderMain cancel(Long orderId) {
        OrderMain orderMain = findOne(orderId);
        if (!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
        }
        orderMain.setOrderStatus(OrderStatusEnum.CANCELED.getCode());
        orderRepository.save(orderMain);

        //Restore Stock
        Set<ProductInOrder> products = orderMain.getProducts();
        for (ProductInOrder product : products) {
            ProductInfo productInfo = productInfoRepository.findByProductId(product.getProductId());
            if (productInfo != null) {
                productService.increaseStock(product.getProductId(), product.getCount());
            }
        }

        return orderRepository.findByOrderId(orderId);
    }
}
