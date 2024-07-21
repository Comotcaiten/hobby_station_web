package com.vanlang.hobby_station.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vanlang.hobby_station.service.OrderService;
import com.vanlang.hobby_station.service.UserService;

@Controller
@RequestMapping("/admin")
public class DashboardController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @GetMapping({"","/dashboard"})
    public String home(Model model) {
        model.addAttribute("khachHangs", userService.size());
        model.addAttribute("orderSize", orderService.getAllOrders().size());
        return "/dashboard/index";
    }
    @GetMapping("/reports")
    public String report(Model model) {
        model.addAttribute("khachHangs", userService.size());
        model.addAttribute("orderSize", orderService.getAllOrders().size());
        return "/dashboard/report";
    }
}
