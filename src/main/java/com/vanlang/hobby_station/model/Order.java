package com.vanlang.hobby_station.model;

import java.util.ArrayList;
import java.util.Date;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;

    private String shippingAddress;
    private String phoneNumber;
    private String email;
    private String orderNote;
    private String paymentMethod;   

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        status = OrderStatus.PENDING;
    }




    public double getTotalAmount() {
        return orderDetails.stream()
            .mapToDouble(detail -> detail.getProduct().getPrice() * detail.getQuantity())
            .sum();
    }



    public enum OrderStatus {
        PENDING,       // Đang chờ xử lý
        PROCESSING,    // Đang xử lý
        DELIVERED,     // Đã giao hàng
        CANCELED       // Đã hủy
    }
    
}

