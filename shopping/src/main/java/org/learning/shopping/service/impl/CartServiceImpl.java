package org.learning.shopping.service.impl;

import org.learning.shopping.Repository.CartRepository;
import org.learning.shopping.Repository.OrderRepository;
import org.learning.shopping.Repository.ProductInOrderRepository;
import org.learning.shopping.entity.Cart;
import org.learning.shopping.entity.OrderMain;
import org.learning.shopping.entity.ProductInOrder;
import org.learning.shopping.entity.User;
import org.learning.shopping.enums.ResultEnum;
import org.learning.shopping.exception.MyException;
import org.learning.shopping.service.CartService;
import org.learning.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductInOrderRepository productInOrderRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductService productService;

    @Override
    public Cart getCart(User user) {
        return user.getCart();
    }

    @Override
    @Transactional
    public void delete(String itemId, User user) {

        if(itemId.equals("")||user==null){
            throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
        }
        Optional<ProductInOrder> first = user.getCart().getProducts().stream().filter(x ->
            itemId.equals(x.getProductId())
        ).findFirst();
        first.ifPresent(x->{
            x.setCart(null);
            productInOrderRepository.deleteById(x.getId());
        });
    }

    @Override
    @Transactional
    public void checkout(User user) {
        OrderMain order = new OrderMain(user);
        orderRepository.save(order);

        // clear cart's foreign key & set order's foreign key& decrease stock
        user.getCart().getProducts().forEach(productInOrder -> {
            productInOrder.setCart(null);
            productInOrder.setOrderMain(order);
            productService.decreaseStock(productInOrder.getProductId(), productInOrder.getCount());
            productInOrderRepository.save(productInOrder);
        });

    }

    @Override
    @Transactional
    public void mergeLocalCart(Collection<ProductInOrder> productInOrders, User user) {
        Cart cart = user.getCart();
        productInOrders.forEach(productInOrder -> {
            Set<ProductInOrder> products = cart.getProducts();
            Optional<ProductInOrder> old = products.stream().filter(e -> e.getProductId().equals(productInOrder.getProductId())).findFirst();
            ProductInOrder prod;
            if (old.isPresent()) {
                prod = old.get();
                prod.setCount(prod.getCount() + productInOrder.getCount());
            } else {
                prod = productInOrder;
                prod.setCart(cart);
                cart.getProducts().add(prod);
            }
            productInOrderRepository.save(prod);
        });
        cartRepository.save(cart);
    }
}
