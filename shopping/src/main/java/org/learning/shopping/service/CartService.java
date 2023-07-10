package org.learning.shopping.service;

import org.learning.shopping.entity.Cart;
import org.learning.shopping.entity.ProductInOrder;
import org.learning.shopping.entity.User;

import java.util.Collection;

public interface CartService {

    Cart getCart(User user);

    void mergeLocalCart(Collection<ProductInOrder> productInOrders,User user);

    void delete(String itemId,User user);

    void checkout(User user);
}
