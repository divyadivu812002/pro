package CaseStudy.OrderInventory.controller;

import CaseStudy.OrderInventory.model.Stores;
import CaseStudy.OrderInventory.service.StoresService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores") // Base URL for Stores endpoints
public class StoresController {

    @Autowired
    private StoresService storesService;

    // Get all stores
    @GetMapping
    public List<Stores> getAllStores() {
        return storesService.getAllStores();
    }

    // Get a specific store by ID
    @GetMapping("{id}")
    public Stores getStoreById(@PathVariable int id) {
        return storesService.getStoreById(id);
    }

    // Add a new store
    @PostMapping
    public Stores addStore(@RequestBody Stores store) {
        return storesService.addStore(store);
    }

    // Update an existing store
    @PutMapping("/{id}")
    public Stores updateStore(@PathVariable int id, @RequestBody Stores store) {
        return storesService.updateStore(id, store);
    }

    // Delete a store
    @DeleteMapping("/{id}")
    public void deleteStore(@PathVariable int id) {
        storesService.deleteStore(id);
    }
}
