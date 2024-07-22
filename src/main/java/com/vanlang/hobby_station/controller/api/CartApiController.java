package com.vanlang.hobby_station.controller.api;
import com.vanlang.hobby_station.model.CartItem;
import com.vanlang.hobby_station.service.CartService;
import com.vanlang.hobby_station.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/cart")
public class CartApiController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<CartItem> getCartItems() {
        return cartService.getCartItems();
    }

    @PostMapping("/add")
    public void addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        if(productService.getProductByIdAndIsDeleted(productId, false).isPresent())
        {
            cartService.addToCart(productId, quantity);
        }
    }

    @PostMapping("/update")
    public void updateCartItem(@RequestParam Long productId, @RequestParam int quantity) {
        if(productService.getProductByIdAndIsDeleted(productId, false).isPresent())
        {
            cartService.updateCartItem(productId, quantity);
        }
    }

    @PostMapping("/remove")
    public void removeFromCart(@RequestParam Long productId) {
        cartService.removeFromCart(productId);
    }

    @PostMapping("/clear")
    public void clearCart() {
        cartService.clearCard();
    }

    @GetMapping("/total")
    public double getTotalPrice() {
        return cartService.calculateTotalPrice();
    }
    @GetMapping("/quanity")
    public long getTotalQuanity() {
        return cartService.totalQuanity();
    }
    
}
