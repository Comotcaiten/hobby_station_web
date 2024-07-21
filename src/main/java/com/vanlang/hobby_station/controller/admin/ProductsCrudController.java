package com.vanlang.hobby_station.controller.admin;

import com.vanlang.hobby_station.model.Product;
import com.vanlang.hobby_station.service.BrandService;
import com.vanlang.hobby_station.service.CategoryService;
import com.vanlang.hobby_station.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products")
@PreAuthorize("hasAuthority('ADMIN')")
public class ProductsCrudController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    // Display a list of all products
    @GetMapping
    public String showProducts(Model model) {
        model.addAttribute("products", productService.getProductsByIsDeleted(false));
        return "/products/products-list";
    }

    // Display a list of all products with soft delete
    @GetMapping("/trash-can")
    public String trashCanProducts(Model model) {
        model.addAttribute("products", productService.getProductsByIsDeleted(true));
        return "/products/trash-can-products";
    }

    // For adding a new product
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("brands", brandService.getAllBrands());
        return "/products/add-product";
    }

    // Process the form for adding a new product
    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model model) {
        if (product.getImg().isEmpty() || product.getImg().equalsIgnoreCase(null)) {
            product.setImg("/image/default.png");
        }
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("brands", brandService.getAllBrands());
            return "/products/add-product";
        }
        productService.addProduct(product);
        return "redirect:/admin/products";
    }

    // For editing a product
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("brands", brandService.getAllBrands());
        return "/products/update-product";
    }

    // Process the form for updating a product
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, @Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            product.setId(id);
            return "/products/update-product";
        }
        productService.updateProduct(product);
        return "redirect:/admin/products";
    }

    // Handle request to soft delete a product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return "redirect:/admin/products";
    }

    // Handle request to permanently delete a product
    @GetMapping("/destroy/{id}")
    public String destroyProducts(@PathVariable("id") Long id) {
        productService.destroyProductById(id);
        return "redirect:/admin/products/trash-can";
    }

    // Handle request to restore a product
    @GetMapping("/restore/{id}")
    public String restoreProducts(@PathVariable("id") Long id) {
        productService.restoreProductById(id);
        return "redirect:/admin/products/trash-can";
    }
}
