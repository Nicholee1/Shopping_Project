package org.learning.shopping.api;

import org.learning.shopping.entity.Cart;
import org.learning.shopping.entity.ProductInOrder;
import org.learning.shopping.entity.ProductInfo;
import org.learning.shopping.entity.User;
import org.learning.shopping.form.ItemForm;
import org.learning.shopping.service.CartService;
import org.learning.shopping.service.ProductInOrderService;
import org.learning.shopping.service.ProductService;
import org.learning.shopping.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {
    private static final Logger logger= LoggerFactory.getLogger(CartController.class);

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductInOrderService productInOrder;

    @GetMapping("")
    public Cart getCart(Principal principal) {
        User user = userService.findOne(principal.getName());
        return cartService.getCart(user);

    }

    @PostMapping("/add")
    public boolean addToCart(@RequestBody ItemForm form, Principal principal) {
        ProductInfo productInfo = productService.findOne(form.getProductId());
        try {
            mergeCart(Collections.singleton(new ProductInOrder(productInfo, form.getQuantity())), principal);
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    @PostMapping("")
    public ResponseEntity<Cart> mergeCart(@RequestBody Collection<ProductInOrder> productInOrders, Principal principal) {
        User user = userService.findOne(principal.getName());
        try {
            cartService.mergeLocalCart(productInOrders, user);
        } catch (Exception e) {
            ResponseEntity.badRequest().body("Merge failed");
        }
        return ResponseEntity.ok(cartService.getCart(user));
    }

    @PutMapping("/{itemId}")
    public ProductInOrder modifyItem(@PathVariable("itemId") String itemId, @RequestBody Integer quantity, Principal principal) {
        User user = userService.findOne(principal.getName());

        productInOrder.update(itemId,quantity,user);
        return productInOrder.findOne(itemId,user);
    }
    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable("itemId")String itemId,Principal principal){
        User user = userService.findOne(principal.getName());
        cartService.delete(itemId,user);
    }

    @PostMapping("/checkout")
    public ResponseEntity checkout(Principal principal){
        User user = userService.findOne(principal.getName());
        cartService.checkout(user);
        return ResponseEntity.ok(null);
    }
}
