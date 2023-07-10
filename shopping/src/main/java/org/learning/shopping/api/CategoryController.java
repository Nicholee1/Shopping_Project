package org.learning.shopping.api;

import io.swagger.annotations.Api;
import org.learning.shopping.entity.ProductCategory;
import org.learning.shopping.entity.ProductInfo;
import org.learning.shopping.service.CategoryService;
import org.learning.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.learning.shopping.vo.response.CategoryPage;


@RestController //equals to @Controller+@ResponseBody , means will return json response body
@Api //for swagger ui
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @GetMapping("/category/{type}")
    public CategoryPage showOne(@PathVariable("type") Integer type,
                                @RequestParam(value = "page",defaultValue = "1") Integer page,
                                @RequestParam(value = "size",defaultValue = "3") Integer size){
        ProductCategory category = categoryService.findAllByCategoryType(type);
        PageRequest request=PageRequest.of(page-1,size);
        Page<ProductInfo> productInCategory = productService.findAllInCategory(type, request);
        var categoryPage=new CategoryPage(category.getCategoryName(),productInCategory);
        return  categoryPage;

    }
}
