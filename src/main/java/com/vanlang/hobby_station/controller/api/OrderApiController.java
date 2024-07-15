package com.vanlang.hobby_station.controller.api;

import com.vanlang.hobby_station.model.Product;
import com.vanlang.hobby_station.service.OrderService;
import com.vanlang.hobby_station.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrderApiController {
    @Autowired
    private OrderService orderService;
}
