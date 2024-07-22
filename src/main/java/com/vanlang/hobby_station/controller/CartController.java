package com.vanlang.hobby_station.controller;

import com.vanlang.hobby_station.service.CartService;
import com.vanlang.hobby_station.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        return "/cart/cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        if (productService.getProductByIdAndIsDeleted(productId, false).isPresent()) {
            // Xử lý khi tìm thấy sản phẩm
            cartService.addToCart(productId, quantity);
        }
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
        if (productService.getProductByIdAndIsDeleted(productId, false).isPresent()) {
            // Xử lý khi tìm thấy sản phẩm
            cartService.updateCartItem(productId, quantity);
        }
    }
}
