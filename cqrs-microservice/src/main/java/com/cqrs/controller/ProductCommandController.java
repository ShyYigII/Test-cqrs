package com.cqrs.controller;

import com.cqrs.dto.ProductEvent;
import com.cqrs.entity.Product;
import com.cqrs.service.ProductCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductCommandController {
    @Autowired
    private ProductCommandService productCommandService;

    @PostMapping
    public Product createProduct(@RequestBody ProductEvent product){
        return productCommandService.createProduct(product);}

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable long id,@RequestBody ProductEvent product){
        return productCommandService.updateProduct(id,product);
    }

}
