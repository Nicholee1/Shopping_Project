package org.learning.shopping.service.impl;

import org.learning.shopping.Repository.ProductInOrderRepository;
import org.learning.shopping.entity.ProductInOrder;
import org.learning.shopping.entity.User;
import org.learning.shopping.service.ProductInOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ProductInOrderServiceImpl implements ProductInOrderService {
    @Autowired
    ProductInOrderRepository productInOrderRepository;

    @Override
    public ProductInOrder findOne(String id, User user) {
        var first = user.getCart().getProducts().stream().filter(x -> id.equals(x.getProductId())).findFirst();
        AtomicReference<ProductInOrder> res = new AtomicReference<>();
        first.ifPresent(res::set);
        return res.get();
    }

    @Override
    @Transactional
    public void update(String itemId, Integer quantity, User user) {
        var first = user.getCart().getProducts().stream().filter(x -> itemId.equals(x.getProductId())).findFirst();
        first.ifPresent(x->{
            x.setCount(quantity);
            productInOrderRepository.save(x);
        });
    }
}
