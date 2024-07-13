package com.vanlang.demo.service;


import com.vanlang.demo.model.Brand;
import com.vanlang.demo.model.Category;
import com.vanlang.demo.repository.BrandRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandService {
    private final BrandRepository brandRepository;


    public List<Brand> getAllBrand(){
        return brandRepository.findAll();
    }

    public Optional<Brand> getBrandById(Long id){
        return brandRepository.findById(id);
    }

    public void addBrand(Brand brand){
        brandRepository.save(brand);
    }

    public void updateBrand(@NonNull Brand brand){
        Brand existingBrand = brandRepository.findById(brand.getId()).orElseThrow(() ->
                new IllegalStateException("Category with ID " + brand.getId() + "does not exist."));
        existingBrand.setName(brand.getName());
        brandRepository.save(existingBrand);
    }

    public void deleteBrand(Long id){
        if(!brandRepository.existsById(id)){
            throw new IllegalStateException("Brand with ID "+ id + "does not exist.");
        }
        brandRepository.deleteById(id);
    }
}