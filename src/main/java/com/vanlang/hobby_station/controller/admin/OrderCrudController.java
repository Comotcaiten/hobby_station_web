package com.vanlang.hobby_station.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vanlang.hobby_station.model.Order;
import com.vanlang.hobby_station.model.Order.OrderStatus;
import com.vanlang.hobby_station.service.OrderService;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class OrderCrudController {
    @Autowired
    private OrderService orderService
    ;
    @GetMapping
    public String home(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders/orders-list";
    }

    @GetMapping("/{id}")
    public String viewOrder(@PathVariable("id") Long id, Model model) {
        Order order;
        try {
            order = orderService.getOrderById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        } catch (Exception e) {
            // TODO: handle exception
            order = null;
        }
        model.addAttribute("order", order);
        return "orders/order-view"; // Chỉ định tên view
    }

    @GetMapping("/edit/{id}")
    public String editOrderForm(@PathVariable("id") Long id, Model model) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        model.addAttribute("order", order);
        return "orders/order-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateOrder(@PathVariable("id") Long id, @ModelAttribute Order order) {
        orderService.updateOrder(id, order);
        return "redirect:/admin/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id, Model model) {
        try {
            Order order = orderService.getOrderById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
            
            orderService.deleteOrder(id);
        }
        catch(Exception e) {

        }
        return "redirect:/admin/orders";
    }

    @GetMapping("/delete-all")
    public String deleteAllOrderCanceled(Model model) {
        try {            
            orderService.deleteOrdersByStatus(OrderStatus.CANCELED);
            System.out.println("PASSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSs");
        }
        catch(Exception e) {
            System.out.println("Lỗi");
        }
        return "redirect:/admin/orders";
    }
}
