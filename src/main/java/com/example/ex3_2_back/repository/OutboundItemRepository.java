package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.OutboundItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "OutboundItemRepository")
@Tag(name = "数据库OutboundItem接口")
public interface OutboundItemRepository extends JpaRepository<OutboundItem, Long> {
    // 可以使用 Spring Data JPA 提供的默认方法进行数据库操作
    // 也可以根据需要添加自定义方法
}
