package com.snw.api.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.snw.api.service.VehicleService;
import com.snw.api.service.liftService;
import com.snw.api.service.liftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hyxzjbnb
 * @create 2024-06-16-11:58
 */
@RestController
@RequestMapping("/api/lift")
public class LiftController {
    private final liftService LiftService;

    @Autowired
    public LiftController(liftService vehicleService) {
        this.LiftService = vehicleService;
    }

    @GetMapping("/all")
    public ArrayNode getAllVehicles() {
        return LiftService.getAlllifts();
    }

    @PostMapping("/add")
    public String addVehicle(@RequestBody ObjectNode newVehicle) {
        LiftService.addLift(newVehicle);
        return "Vehicle added successfully!";
    }

    @DeleteMapping("/delete/{vehicleId}")
    public String deleteVehicle(@PathVariable String vehicleId) {
        LiftService.deleteLift(vehicleId);
        return "Vehicle deleted successfully!";
    }
}
