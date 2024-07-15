package com.vanlang.hobby_station.service;

import com.vanlang.hobby_station.model.Category;
import com.vanlang.hobby_station.repository.CategoryRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * Retrieve all categories from the database.
     * @return a list of categories
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
//    public List<Category> getAllCategoriesByIsDelete(boolean isDelete)
//    {
//        return categoryRepository.findByIsDeleted(isDelete);
//    }
    /**
     * Retrieve a category by its id.
     * @param id the id of the category to retrieve
     * @return an Optional containing the found category or empty if not found
     */
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }
    /** * Add a new category to the database. * @param category the category to add */
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }
    /** * Update an existing category. * @param category the category with updated information */
    public void updateCategory(@NonNull Category category) {
        Category existingCategory = categoryRepository.findById(category.getId()).orElseThrow(() -> new IllegalStateException("Category with ID " + category.getId() + " does not exist."));
        existingCategory.setName(category.getName());
        categoryRepository.save(existingCategory);
    }
    /** *Delete a category by its id. * @param id the id of the category to delete */
    public void destroyCategory(Long id) {
        if(!categoryRepository.existsById(id)) {
            throw new IllegalStateException("Category with ID " + id + " does not exist.");
        }
        categoryRepository.deleteById(id);
    }

//    // Soft delete a product by its id
//    public void deleteCategory(Long id) {
//        Category category = categoryRepository.findById(id)
//                .orElseThrow(() -> new IllegalStateException("Category with ID " + id + " does not exist."));
//        category.setIsDeleted(true);
//        categoryRepository.save(category);
//    }
//
//    // Restore a soft deleted product by its id
//    public void restoreCategory(Long id) {
//        Category category = categoryRepository.findById(id)
//                .orElseThrow(() -> new IllegalStateException("Category with ID " + id + " does not exist."));
//        category.setIsDeleted(false);
//        categoryRepository.save(category);
//    }
}
