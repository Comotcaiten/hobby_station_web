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
        cartItems.add(new CartItem(product, quantity));
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
}
