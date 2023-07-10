package org.learning.shopping.service.impl;

import org.learning.shopping.Repository.ProductCategoryRepository;
import org.learning.shopping.entity.ProductCategory;
import org.learning.shopping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAllByOrderByCategoryType() ;
    }

    @Override
    public ProductCategory findAllByCategoryType(Integer categoryType) {
        return productCategoryRepository.findAllByCategoryType(categoryType);

    }
}
