package com.example.ex3_2_back.controller;

/**
 * @author hyxzjbnb
 * @create 2024-04-29-20:12
 */
import com.example.ex3_2_back.service.InventoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ex3_2_back.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ex3_2_back.entity.Inventory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@Tag(name = "InventoryController", description = "库存管理接口")
public class InventoryController implements ErrorController {

    @Autowired
    private InventoryService inventoryService;

    @PatchMapping("/inventory/{productId}")
    @Operation(summary = "更新库存", description = "更新指定产品的库存数量。")
    public Result updateInventory(@PathVariable Integer productId, @RequestParam int changeInQuantity) {
        try {
            Inventory updatedInventory = inventoryService.updateInventory(productId, changeInQuantity);
            return Result.success(updatedInventory);
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @GetMapping("/inventory")
    @Operation(summary = "查询库存", description = "查询特定产品的当前库存量。")
    public Result getInventory(@RequestParam Integer productId) {
        try {
            return Result.success(inventoryService.getInventoryQuantity(productId));
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @GetMapping("/inventory/warnings")
    @Operation(summary = "库存警告", description = "查询库存量低于安全水平的所有产品。")
    public Result getLowInventoryWarnings() {
        List<Inventory> lowInventoryItems = inventoryService.checkLowInventory();
        return Result.success(lowInventoryItems);
    }

}



