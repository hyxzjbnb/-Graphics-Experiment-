package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.entity.Product;
import com.example.ex3_2_back.service.ProductService;
import com.example.ex3_2_back.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Operation(summary = "添加产品信息", description = "添加新的产品信息到数据库，包括名称、描述和初始库存量。")
    public Result addProduct(@RequestBody Product product) {
        try {
            Product savedProduct = productService.updateProduct(product);
            return Result.success(savedProduct);
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @GetMapping("/{productId}")
    @Operation(summary = "获取产品信息", description = "根据产品ID获取产品详细信息。")
    public Result getProduct(@PathVariable Integer productId) {
        try {
            Product product = productService.getProductById(productId);
            return Result.success(product);
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @PatchMapping("/{productId}")
    @Operation(summary = "更新产品信息", description = "更新指定产品的信息，包括名称、描述等。")
    public Result updateProduct(@PathVariable Integer productId, @RequestBody Product product) {
        try {
            product.setId(productId);
            Product updatedProduct = productService.updateProduct(product);
            return Result.success(updatedProduct);
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "删除产品信息", description = "从数据库中删除指定的产品信息。")
    public Result deleteProduct(@PathVariable Integer productId) {
        try {
            productService.deleteProduct(productId);
            return Result.success("Product deleted successfully.");
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }
}
