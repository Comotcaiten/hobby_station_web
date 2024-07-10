package com.vanlang.hobby_station.controller.admin;

import com.vanlang.hobby_station.model.Product;
import com.vanlang.hobby_station.service.CategoryService;
import com.vanlang.hobby_station.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/dashboard/products")
public class ProductsCrudController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;// Đảm bảo bạn đã inject CategoryService

    // Display a list of all products
    @GetMapping
    public String showProducts(Model model) {
        model.addAttribute("products",productService.getProductsByIsDeleted(false));
        model.addAttribute("opt", new String("products"));
        return "/products/products-list";
    }

    //
    @GetMapping("/trash-can")
    public String trashCanProducts(Model model)
    {
        model.addAttribute("products",productService.getProductsByIsDeleted(true));
        model.addAttribute("opt", new String("products"));
        return "/products/trash-can-products";
    }

    // For adding a new product
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories",categoryService.getAllCategoriesByIsDelete(false));// Load categories
        return "/products/add-product";
    }
    // Process the form for adding a new product
    @PostMapping("/add")
    public String addProduct(@Valid Product product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "products/add-product";
        }
        productService.addProduct(product);
        return "redirect:/admin/dashboard/products";
    }
    // For editing a product
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        model.addAttribute("categories",productService.getProductsByIsDeleted(false));//Load categories
        return "/products/update-product";
    }
    // Process the form for updating a product
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, @Valid Product product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            product.setId(id);//// set id to keep it in the form in case of errors
            return "/products/update-product";
        }
        productService.updateProduct(product);
        return "redirect:/admin/dashboard/products";
    }

    // Handle request to soft delete a product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return "redirect:/admin/dashboard/products";
    }

    // Handle request to delete a product
    @GetMapping("/destroy/{id}")
    public String destroyProducts(@PathVariable("id") Long id) {
        productService.destroyProductById(id);
        return "redirect:/admin/dashboard/products/trash-can";
    }

    //
    @GetMapping("/restore/{id}")
    public String restoreProducts(@PathVariable("id") Long id) {
        productService.restoreProductById(id);
        return "redirect:/admin/dashboard/products/trash-can";
    }
}
