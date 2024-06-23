package com.snw.api.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.snw.api.service.liftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lift")
public class LiftController {

    private final liftService liftService;

    @Autowired
    public LiftController(liftService liftService) {
        this.liftService = liftService;
    }

    @GetMapping("/all")
    public ArrayNode getAllVehicles() {
        return liftService.getAllLifts();
    }

    @PostMapping("/add")
    public String addVehicle(@RequestBody ObjectNode newVehicle) {
        liftService.addLift(newVehicle);
        return "Vehicle added successfully!";
    }

    @PostMapping("/store")
    public String storeLift(@RequestBody ObjectNode request) {
        String position = request.get("position").asText();
        liftService.storeLift(position);
        return "Lift stored at position " + position + " successfully!";
    }

    @DeleteMapping("/delete/{vehicleId}")
    public String deleteVehicle(@PathVariable String vehicleId) {
        liftService.deleteLift(vehicleId);
        return "Vehicle deleted successfully!";
    }
}
