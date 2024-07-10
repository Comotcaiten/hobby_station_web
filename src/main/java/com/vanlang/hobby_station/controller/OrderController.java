package com.vanlang.hobby_station.controller;

import com.vanlang.hobby_station.model.CartItem;
import com.vanlang.hobby_station.service.CartService;
import com.vanlang.hobby_station.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @GetMapping("/checkout")
    public String checkout() {
        return "cart/checkout";
    }

    @PostMapping("/submit")
    public String submitOrder(String customerName, String shippingAddress, String phoneNumber, String email, String orderNote, String paymentMethod) {
        List<CartItem> cartItems = cartService.getCartItems();
        System.out.println(shippingAddress);
        System.out.println(phoneNumber);
        System.out.println(email);
        System.out.println(orderNote);

        if (cartItems.isEmpty()) {
            return "redirect:/cart"; // Redirect if cart is empty
        }

        orderService.createOrder(customerName, shippingAddress, phoneNumber, email, orderNote, paymentMethod, cartItems);
        return "redirect:/order/confirmation";
    }

    @GetMapping("/confirmation")
    public String orderConfirmation(Model model) {
        model.addAttribute("message", "Your order has been successfully placed.");
        return "cart/order-confirmation";
    }
}
