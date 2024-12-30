package CaseStudy.OrderInventory.controller;
 
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import CaseStudy.OrderInventory.model.*;
import CaseStudy.OrderInventory.service.ShipmentsService;

import java.util.List;
 
@RestController
@RequestMapping("/shipments") // Base URL for Shipments endpoints
public class ShipmentsController {
 
    @Autowired
    private ShipmentsService shipmentsService;
 
    // Get all shipments
    @GetMapping
    public List<Shipments> getAllShipments() {
        return shipmentsService.getAllShipments();
    }
 
    // Get a shipment by ID
    @GetMapping("/{id}")
    public Shipments getShipmentById(@PathVariable Long id) {
        return shipmentsService.getShipmentById(id);
    }
 
    // Add a new shipment
    @PostMapping
    public ResponseEntity<String> addShipment(@RequestBody Shipments shipment) {
    	shipmentsService.addShipment(shipment);
    	return ResponseEntity.ok("Record created");
    }
 
    // Update a shipment
    @PutMapping("/{id}")
    public Shipments updateShipment(@PathVariable Long id, @RequestBody Shipments shipment) {
        
    	return shipmentsService.updateShipment(id, shipment);
    }
 
    // Delete a shipment
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShipment(@PathVariable Long id) {
        shipmentsService.deleteShipment(id);
        
    	return ResponseEntity.ok("Record deleted");
    }
}