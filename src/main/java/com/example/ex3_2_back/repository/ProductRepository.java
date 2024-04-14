package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(path = "ProductRepository")
@Tag(name = "数据库Product接口")
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Operation(summary = "通过产品名称查找")
    @RestResource(path = "findByName")
    Optional<Product> findByName(String name);

    @Operation(summary = "通过产品ID删除产品")
    @RestResource(path = "deleteById")
    void deleteById(Long id);
}
