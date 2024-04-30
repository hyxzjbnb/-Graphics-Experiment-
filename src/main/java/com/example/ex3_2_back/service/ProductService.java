package com.example.ex3_2_back.service;

import com.example.ex3_2_back.entity.*;
import com.example.ex3_2_back.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    /**
     * Saves a new product to the database and caches the result.
     * @param product The product to save.
     * @return The saved product.
     */
    @CachePut(value = "products", key = "#product.productId")
    @Transactional
    public Product saveProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    /**
     * Retrieves a product by its id, caching the result.
     * @param productId The id of the product to retrieve.
     * @return The requested product.
     */
    @Cacheable(value = "products", key = "#product.productId")
    public Product getProductById(Integer productId) {
        return productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));
    }

    /**
     * Updates a product and caches the new result.
     * @param product The product to update.
     * @return The updated product.
     */
    @CachePut(value = "products", key = "#product.productId")
    @Transactional
    public Product updateProduct(Product product) {
        Product updatedProduct = productRepository.save(product);
        return updatedProduct;
    }

    /**
     * Evicts a specific product from the cache.
     * @param productId The id of the product to evict from the cache.
     */
    @CacheEvict(value = "products", key = "#product.productId")
    public void evictProductFromCache(Long productId) {
        // This method will automatically remove the item from cache.
    }

    /**
     * Deletes a product and its cache.
     * @param productId The id of the product to delete.
     */
    @CacheEvict(value = "products", key = "#product.productId")
    @Transactional
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }
}
