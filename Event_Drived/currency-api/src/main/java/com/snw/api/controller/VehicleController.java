package com.snw.api.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.snw.api.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/add")
    public String addVehicle(@RequestBody ObjectNode newVehicle) {
        vehicleService.addVehicle(newVehicle);
        return "Vehicle added successfully!";
    }

    @DeleteMapping("/delete/{vehicleId}")
    public String deleteVehicle(@PathVariable String vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
        return "Vehicle deleted successfully!";
    }

    @PutMapping("/update/{vehicleId}")
    public String updateVehicle(@PathVariable String vehicleId, @RequestBody ObjectNode updatedVehicle) {
        vehicleService.updateVehicle(vehicleId, updatedVehicle);
        return "Vehicle updated successfully!";
    }

    @GetMapping("/all")
    public ArrayNode getAllVehicles() {
        return vehicleService.getAllVehicles();
    }
}
