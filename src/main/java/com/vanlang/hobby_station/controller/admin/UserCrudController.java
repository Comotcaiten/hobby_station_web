package com.vanlang.hobby_station.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.vanlang.hobby_station.model.User;
import com.vanlang.hobby_station.service.RoleService;
import com.vanlang.hobby_station.service.UserService;
import java.util.List;

@Controller
@RequestMapping("/admin/customers")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserCrudController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String getAll(Model model) {
        List<User> users = userService.getAllUser();
        model.addAttribute("users", users);
        return "/customers/customer-list";
    }
    
    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "customers/customer-edit";
    }
    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute User user) {
        User existingUser = userService.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        // Cập nhật thông tin người dùng từ form
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setRoles(user.getRoles()); // Cập nhật vai trò nếu cần
        userService.save(existingUser);
        return "redirect:/admin/customers";
    }

    // Xóa người dùng
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/customers";
    }
}
