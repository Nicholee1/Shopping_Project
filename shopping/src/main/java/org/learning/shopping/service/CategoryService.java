package org.learning.shopping.service;

import org.learning.shopping.entity.ProductCategory;

import java.util.List;

public interface CategoryService {

    List<ProductCategory> findAll();

    ProductCategory findAllByCategoryType(Integer categoryType);
}
