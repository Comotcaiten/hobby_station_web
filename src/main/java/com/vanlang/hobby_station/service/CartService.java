package com.vanlang.hobby_station.service;

import com.vanlang.hobby_station.model.CartItem;
import com.vanlang.hobby_station.model.Product;
import com.vanlang.hobby_station.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {
    private List<CartItem> cartItems = new ArrayList<CartItem>();

    @Autowired
    private ProductRepository productRepository;

    public void addToCart(Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        // Kiểm tra nếu sản phẩm đã có trong giỏ hàng
        CartItem existingItem = cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        if (existingItem != null) {
            // Nếu sản phẩm đã tồn tại, tăng số lượng
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            // Nếu sản phẩm chưa tồn tại, thêm sản phẩm mới vào giỏ hàng
            cartItems.add(new CartItem(product, quantity));
        }
        // cartItems.add(new CartItem(product, quantity));
    }

    public void updateCartItem(Long productId, int quantity) {
        cartItems.stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst()
            .ifPresent(item -> item.setQuantity(quantity));
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void removeFromCart(Long productId) {
        cartItems.removeIf(cartItem -> cartItem.getProduct().getId().equals(productId));
    }

    public void clearCard() {
        cartItems.clear();
    }

    public double calculateTotalPrice() {
        return cartItems.stream()
                        .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                        .sum();
    }

    public long totalQuanity() {
        try {
            return cartItems.stream().mapToLong(item -> item.getQuantity()).sum();
        } catch (Exception e) {
            return -1;
        }
    }
}
