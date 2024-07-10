package com.vanlang.hobby_station.model;

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

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
}
