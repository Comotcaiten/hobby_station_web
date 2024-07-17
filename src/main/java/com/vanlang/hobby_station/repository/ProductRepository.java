package com.vanlang.hobby_station.repository;

import com.vanlang.hobby_station.model.Product;

// Sử dụng import java.util.Optional; thay vì import org.apache.el.stream.Optional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsDeleted(boolean isDeleted);

    Optional<Product> findByIdAndIsDeleted(Long id, boolean isDeleted);

    List<Product> findByBrandId(Long brandId);
    List<Product> findByBrandIdAndIsDeleted(Long brandId, boolean isDeleted);

    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByCategoryIdAndIsDeleted(Long categoryId, boolean isDeleted);
    
    List<Product> findByPriceGreaterThan(Double price);
    List<Product> findByPriceLessThan(Double price);
    List<Product> findByPriceEquals(Double price);
}
