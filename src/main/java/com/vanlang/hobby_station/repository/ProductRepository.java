package com.vanlang.hobby_station.repository;

import com.vanlang.hobby_station.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsDeleted(boolean isDeleted);
}
