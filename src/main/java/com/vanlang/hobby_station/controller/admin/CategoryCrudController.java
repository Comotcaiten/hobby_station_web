package com.vanlang.hobby_station.controller.admin;

import com.vanlang.hobby_station.model.Category;
import com.vanlang.hobby_station.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/dashboard/categories")
public class CategoryCrudController {
    @Autowired
    private final CategoryService categoryService;

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "/categories/add-category";
    }

    @PostMapping("/add")
    public String addCategory(@Valid Category category, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/categories/add-category";
        }
        categoryService.addCategory(category);
        return "redirect:/admin/dashboard/categories";
    }

    // Hiển thị danh sách danh mục
    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.getAllCategoriesByIsDelete(false);
        model.addAttribute("categories", categories);
        model.addAttribute("opt",new String("categories"));
        return "/categories/categories-list";
    }

    @GetMapping ("/trash-can")
    public String trashCan(Model model) {
        List<Category> categories = categoryService.getAllCategoriesByIsDelete(true);
        model.addAttribute("categories", categories);
        model.addAttribute("opt",new String("categories"));
        return "/categories/trash-can-categories";
    }

    // GET request to show category edit form
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.getCategoryById(id).orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        model.addAttribute("category", category);
        return "/categories/update-category";
    }

    // POST request to update category
    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") Long id, @Valid Category category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            category.setId(id);
            return "/categories/update-category";
        }
        categoryService.updateCategory(category);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "redirect:/admin/dashboard/categories";
    }

    // GET request for deleting category
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.getCategoryById(id).orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        categoryService.deleteCategory(id);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "redirect:/admin/dashboard/categories";
    }

    // Handle request to delete a product
    @GetMapping("/destroy/{id}")
    public String destroyProducts(@PathVariable("id") Long id) {
        categoryService.destroyCategory(id);
        return "redirect:/admin/dashboard/categories/trash-can";
    }

    //
    @GetMapping("/restore/{id}")
    public String restoreProducts(@PathVariable("id") Long id) {
        categoryService.restoreCategory(id);
        return "redirect:/admin/dashboard/categories/trash-can";
    }
}
