package org.learning.shopping.Repository;

import org.learning.shopping.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    //all category
    List<ProductCategory> findAllByOrderByCategoryType();

    ProductCategory findAllByCategoryType(Integer categoryType);
}
