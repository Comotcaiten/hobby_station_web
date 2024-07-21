package com.vanlang.hobby_station.service;

import com.vanlang.hobby_station.model.CartItem;
import com.vanlang.hobby_station.model.Order;
import com.vanlang.hobby_station.model.OrderDetail;
import com.vanlang.hobby_station.model.User;
import com.vanlang.hobby_station.model.Order.OrderStatus;
import com.vanlang.hobby_station.repository.OrderDetailRepository;
import com.vanlang.hobby_station.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

// import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CartService cartService; // Assuming you have a CartService
    @Autowired
    private UserService userService;


    @Transactional
    public Order createOrder(String customerName, String shippingAddress, String phoneNumber, String email, String orderNote, String paymentMethod, List<CartItem> cartItems) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                          .orElseThrow(() -> new UsernameNotFoundException("User not found"));;
        
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setShippingAddress(shippingAddress);
        order.setPhoneNumber(phoneNumber);
        order.setEmail(email);
        order.setOrderNote(orderNote);
        order.setPaymentMethod(paymentMethod);
        order.setUser(user);
        order = orderRepository.save(order);
        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            orderDetailRepository.save(detail);
        }
        // Optionally clear the cart after order placement
        cartService.clearCard();
        return order;
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // Phương thức lấy các đơn hàng theo trạng thái
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findOrdersByStatus(status);
    }

    public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }


    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));
        if (order.getStatus() == Order.OrderStatus.PENDING || order.getStatus() == Order.OrderStatus.PROCESSING) {
            order.setStatus(Order.OrderStatus.CANCELED);
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Cannot cancel order with status " + order.getStatus());
        }
    }

    // Trạng thái đơn hàng sau khi đã giao - Delivered
    // Trạng thái đơn hàng sau khi đã giao - Delivered
    public void doneOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));
        if (order.getStatus() == Order.OrderStatus.PENDING || order.getStatus() == Order.OrderStatus.PROCESSING) {
            order.setStatus(Order.OrderStatus.DELIVERED);
            order.setDeliveryDate(new Date()); // Cập nhật ngày giao hàng
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Cannot mark order as delivered with status " + order.getStatus());
        }
    }

    public void updateOrder(Long id, Order updatedOrder) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        existingOrder.setCustomerName(updatedOrder.getCustomerName());
        existingOrder.setEmail(updatedOrder.getEmail());
        existingOrder.setPhoneNumber(updatedOrder.getPhoneNumber());
        existingOrder.setShippingAddress(updatedOrder.getShippingAddress());
        existingOrder.setOrderNote(updatedOrder.getOrderNote());
        existingOrder.setPaymentMethod(updatedOrder.getPaymentMethod());
        existingOrder.setStatus(updatedOrder.getStatus());
        if(updatedOrder.getStatus() == OrderStatus.DELIVERED)
        {
            existingOrder.setDeliveryDate(new Date());
        }
        else {
            existingOrder.setDeliveryDate(null);
        }
        // Update other fields as necessary
        orderRepository.save(existingOrder);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public void deleteOrdersByStatus(OrderStatus ord) {
        orderRepository.deleteOrdersByStatus(ord);
    }

    // Lấy Tổng
    public double getTotalRevenue() {
        double total;
        try {
            OrderStatus ord = OrderStatus.DELIVERED;
            total = orderDetailRepository.findTotalRevenueByStatus(ord);
        } catch (Exception e) {
            // TODO: handle exception
            total = 0;
        }
        return total;
    }
    
    public double getTotalQuantityProduct() {
        double total;
        try {
            OrderStatus ord = OrderStatus.DELIVERED;
            total = orderDetailRepository.findTotalQuantityByStatus(ord);
        } catch (Exception e) {
            // TODO: handle exception
            total = 0;
        }
        return total;
    }
    
}
