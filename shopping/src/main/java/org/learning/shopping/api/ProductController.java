package org.learning.shopping.api;

import io.swagger.annotations.Api;
import org.learning.shopping.entity.ProductInfo;
import org.learning.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@Api
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/product/{productId}")
    public ProductInfo showOne(@PathVariable("productId") String productId){
        return productService.findOne(productId);
    }

    @GetMapping("/product")
    public Page<ProductInfo> findAll(@RequestParam(value = "page",defaultValue="1") Integer page,
                                     @RequestParam(value = "size",defaultValue="3") Integer size){
        PageRequest pageRequest= PageRequest.of(page-1,size);

        return productService.findAll(pageRequest);
    }

    //@Valid : product数据需要验证，验证结果由bindingResult 返回
    @PostMapping("/seller/product/new")
    public ResponseEntity create(@Valid @RequestBody ProductInfo product, BindingResult bindingResult){
        ProductInfo productInfoExist = productService.findOne(product.getProductId());
        if(productInfoExist!=null){
            bindingResult.rejectValue("productId", "error.product",
                    "There is already a product with the code provided");
        }
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult);
        }
        return ResponseEntity.ok(productService.save(product));
    }
    @PutMapping("/seller/product/{id}/edit")
    public ResponseEntity edit(@PathVariable("id") String productId,
                               @Valid @RequestBody ProductInfo product,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult);
        }
        if(!productId.equals(product.getProductId())){
            return ResponseEntity.badRequest().body("id not match");
        }
        return ResponseEntity.ok(productService.update(product));

    }
    @DeleteMapping("/seller/product/{id}/delete")
    public ResponseEntity delete(@PathVariable("id") String productId){
        productService.delete(productId);
        return ResponseEntity.ok().build();
    }
}
