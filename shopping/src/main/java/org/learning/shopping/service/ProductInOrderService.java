package org.learning.shopping.service;

import org.learning.shopping.entity.ProductInOrder;
import org.learning.shopping.entity.User;

public interface ProductInOrderService {

    ProductInOrder findOne(String id, User user);

    void update(String itemId, Integer quantity, User user);
}
