package com.cqrs.service;

import com.cqrs.dto.ProductEvent;
import com.cqrs.entity.Product;
import com.cqrs.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductQueryService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProducts(){
        return productRepository.findAll();
    }


    @KafkaListener(topics = "product-event-topic", groupId ="product-event-group")
    public void processProductEvent(ProductEvent productEvent){
        if(productEvent.getEventType().equals("CreateProduct")){
            productRepository.save(productEvent.getProduct());
        }else if (productEvent.getEventType().equals("UpdateProduct")){
            Product existingProduct = productRepository.findById(productEvent.getProduct().getId()).get();
            Product newProduct = productEvent.getProduct();
            existingProduct.setDescription(newProduct.getDescription());;
            existingProduct.setName(newProduct.getName());
            existingProduct.setPrice(newProduct.getPrice());
            productRepository.save(existingProduct);
        }
    }
}
