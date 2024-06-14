package com.snw.api.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.snw.api.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping("/add")
    public String addWarehouse(@RequestBody ObjectNode newWarehouse) {
        warehouseService.addWarehouse(newWarehouse);
        return "Warehouse added successfully!";
    }

    @PutMapping("/update/{warehouseId}")
    public String updateWarehouseCapacity(@PathVariable String warehouseId, @RequestBody ObjectNode payload) {
        int capacity = payload.get("availableSlots").asInt();
        ObjectNode dimensions = (ObjectNode) payload.get("dimensions");
        warehouseService.updateWarehouseCapacity(warehouseId, capacity, dimensions);
        return "Warehouse updated successfully!";
    }
    @GetMapping("/all")
    public ArrayNode getAllWarehouses() {
        return warehouseService.getAllWarehouses();
    }

    @DeleteMapping("/delete/{warehouseId}")
    public String deleteWarehouse(@PathVariable String warehouseId) {
        warehouseService.deleteWarehouse(warehouseId);
        return "Warehouse deleted successfully!";
    }
}
