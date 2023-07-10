package org.learning.shopping.service;

import org.learning.shopping.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

     ProductInfo findOne(String productId);

     Page<ProductInfo> findAll(Pageable pageable);

     Page<ProductInfo> findAllInCategory(Integer categoryType,Pageable pageable);

     ProductInfo save(ProductInfo productInfo);

     ProductInfo update(ProductInfo productInfo);

     void delete(String id);

     public void increaseStock(String productId, int amount);

     public void decreaseStock(String productId, int amount);
}
