package com.vanlang.hobby_station.controller;

import com.vanlang.hobby_station.model.CartItem;
import com.vanlang.hobby_station.service.CartService;
import com.vanlang.hobby_station.service.OrderService;
import com.vanlang.hobby_station.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("/checkout")
    public String checkout(Model model) {
        double total;
        try {
            total = cartService.calculateTotalPrice();
        } catch (Exception e) {
            // TODO: handle exception
            total = 0;
        }
        model.addAttribute("total", total);
        return "cart/checkout";
    }

    @PostMapping("/submit")
    public String submitOrder(String customerName, String shippingAddress, String phoneNumber, String email, String orderNote, String paymentMethod) {
        List<CartItem> cartItems = cartService.getCartItems();
        
        System.out.println(shippingAddress);
        System.out.println(phoneNumber);
        System.out.println(email);
        System.out.println(orderNote);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
        String username = authentication.getName();
        if (cartItems.isEmpty() || !userService.usernameExists(username)) {
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

    @GetMapping("/cancel")
    public String cancelOrder(@RequestParam("orderId") Long orderId, RedirectAttributes redirectAttributes) {
        try {
            orderService.cancelOrder(orderId);
            redirectAttributes.addFlashAttribute("message", "Đơn hàng đã được hủy thành công.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/orders";
    }
    
}
