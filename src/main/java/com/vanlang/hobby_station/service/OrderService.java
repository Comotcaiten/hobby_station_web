package com.vanlang.hobby_station.service;

import com.vanlang.hobby_station.model.CartItem;
import com.vanlang.hobby_station.model.Order;
import com.vanlang.hobby_station.model.OrderDetail;
import com.vanlang.hobby_station.repository.OrderDetailRepository;
import com.vanlang.hobby_station.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    public Order createOrder(String customerName, String shippingAddress, String phoneNumber, String email, String orderNote, String paymentMethod, List<CartItem> cartItems) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setShippingAddress(shippingAddress);
        order.setPhoneNumber(phoneNumber);
        order.setEmail(email);
        order.setOrderNote(orderNote);
        order.setPaymentMethod(paymentMethod);
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
}
