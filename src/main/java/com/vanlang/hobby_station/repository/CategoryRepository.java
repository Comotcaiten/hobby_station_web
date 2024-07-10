package com.vanlang.hobby_station.repository;

import com.vanlang.hobby_station.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public List<Category> findByIsDeleted(boolean isDeleted);
}
