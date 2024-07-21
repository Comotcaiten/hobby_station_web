package com.vanlang.hobby_station.controller.api;

import com.vanlang.hobby_station.model.Order;
import com.vanlang.hobby_station.model.Order.OrderStatus;
import com.vanlang.hobby_station.service.OrderService;
import com.vanlang.hobby_station.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/orders")
public class OrderApiController {
    @Autowired
    private OrderService orderService;

    @GetMapping()
    public List<Order> getAll() {
        return orderService.getAllOrders();
    }
    
    @GetMapping("/quantity/total")
    public double getTotalQuantity() {
        return orderService.getTotalQuantityProduct();
    }   

    @GetMapping("/revenue/total")
    public double getTotalRevenue() {
        return orderService.getTotalRevenue();
    }
    
    @GetMapping("/by-status")
    public List<Order> getOrdersByStatus(@RequestParam(value = "status", required = false) String status) {
        OrderStatus orderStatus;

        // Kiểm tra giá trị của status
        if (status == null || status.trim().isEmpty()) {
            return null;
        }
    
        try {
            // Chuyển đổi giá trị status thành OrderStatus
            orderStatus = OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }

        // Lấy danh sách đơn hàng theo trạng thái
        List<Order> orders = orderService.getOrdersByStatus(orderStatus);
        if (orders.isEmpty()) {
            return null;
        } else {
            return orders;
        }
    }

    
}
