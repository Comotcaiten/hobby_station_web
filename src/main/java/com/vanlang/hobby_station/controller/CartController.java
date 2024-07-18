package com.vanlang.hobby_station.controller;
import com.vanlang.hobby_station.model.CartItem;
import com.vanlang.hobby_station.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());

        model.addAttribute("content", "/cart/cart");
        return "layout";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        cartService.addToCart(productId, quantity);
        return "redirect:/cart";
    }
    
    @GetMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        cartService.removeFromCart(productId);
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clearCart() {
        cartService.clearCard();
        return "redirect:/cart";
    }

    @PostMapping("/update")
    @ResponseBody
    public void updateCartItem(@RequestParam Long productId, @RequestParam int quantity) {
        cartService.updateCartItem(productId, quantity);
    }
}
