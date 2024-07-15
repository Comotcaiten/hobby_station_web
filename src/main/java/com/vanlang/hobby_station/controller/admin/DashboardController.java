package com.vanlang.hobby_station.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class DashboardController {
    @GetMapping
    public String home(Model model) {
        model.addAttribute("content", "dashboard/index");
        return "dashboard-layout";
    }

    @GetMapping("/dashboard")
    public String reHome() {
        return "redirect:/admin";
    }
    
}
