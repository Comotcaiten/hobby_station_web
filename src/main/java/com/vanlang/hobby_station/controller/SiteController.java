package com.vanlang.hobby_station.controller;

import com.vanlang.hobby_station.model.Order;
import com.vanlang.hobby_station.model.OrderDetail;
import com.vanlang.hobby_station.model.Product;
import com.vanlang.hobby_station.service.CategoryService;
import com.vanlang.hobby_station.service.OrderService;
import com.vanlang.hobby_station.service.ProductService;
import com.vanlang.hobby_station.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping
public class SiteController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String home(Model model){
        model.addAttribute("products", productService.getNewestProducts(4));
        model.addAttribute("productsSelling", productService.getTopSellingProducts(4));
        return "site/index";
    }

    @GetMapping ("/shop")
    public String shop(Model model){
        model.addAttribute("products", productService.getProductsByIsDeleted(false));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "site/shop";
    }

    @GetMapping ("/detail/{id}")
    public String detail(@PathVariable Long id, Model model){
        if (!productService.getProductByIdAndIsDeleted(id, false).isPresent()) {
            return "redirect:/shop";
        }
        Product product = productService.getProductByIdAndIsDeletedOrThrow(id, false);
        model.addAttribute("product", product);
        return "site/detail-product";
    }

    @GetMapping("/search")
    public String getMethodName(@RequestParam(value = "name", required = false) String name, Model model) {
        List<Product> products;
        if (name == null || name.trim().isEmpty()) {
            products = productService.getAllProducts();
        } else {
            products = productService.searchProductsByName(name);
        }

        model.addAttribute("products", products);
        return "site/search";
    }

    @GetMapping("/orders")
    public String listOrders(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = userService.getIdByUsername(username);
        List<Order> orders = orderService.getOrdersByUserId(userId);

        for (Order order : orders) {
            List<OrderDetail> orderDetails = orderService.getOrderDetailsByOrderId(order.getId());
            order.setOrderDetails(orderDetails); // Giả sử bạn đã có setter này trong Order model
        }

        model.addAttribute("orders", orders);
        return "site/orders"; // Tên file HTML là orders.html
    }
    
}
