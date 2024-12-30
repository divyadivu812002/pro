package CaseStudy.OrderInventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import CaseStudy.OrderInventory.model.Customers;
import CaseStudy.OrderInventory.service.CustomersService;


@RestController
@RequestMapping("/customers")
public class CustomersController {
    @Autowired
    private CustomersService customersService;
    
    
    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        return customersService.getAll();
    }
    @PostMapping
    public ResponseEntity<String> addCustomer(@RequestBody Customers customer) {
         customersService.addCustomer(customer);
         return ResponseEntity.ok("Record created successfully.");
         
    }
   @PutMapping
   public ResponseEntity<String> updateCustomer(@RequestBody Customers customer) {
       customersService.updateCustomer(customer);
      
       return ResponseEntity.ok("Record updated successfully.");
       
  }
   @DeleteMapping("/{customerId}")
   public ResponseEntity<String> deleteCustomer(@PathVariable Integer customerId) {
            customersService.deleteCustomer(customerId);
            return ResponseEntity.ok("Record deleted successfully.");
   }
    
    @GetMapping("/email/{emailId}")
    public ResponseEntity<?> getAllCustomersByEmailId(@PathVariable String emailId){
    	return customersService.getAllCustomersByEmailId(emailId);
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getAllCustomersByName(@PathVariable String name){
    	return customersService.getAllCustomersByEmailId(name);
    }

    @GetMapping("/{custId}/order")
    public ResponseEntity<?> getOrdersByCustomerId(@PathVariable Integer custId) {
        return customersService.getOrdersByCustomerId(custId);
    }

   
    @GetMapping("/{custId}/shipment")
    public ResponseEntity<?> getShipmentHistoryByCustomerId(@PathVariable Integer custId) {
        return customersService.getShipmentHistoryByCustomerId(custId);
        		
    }

    
    @GetMapping("/shipments/pending")
    public ResponseEntity<?> getCustomersWithPendingShipments() {
        return customersService.getCustomersWithPendingShipments();
        		 }

    
    @GetMapping("/orders/completed")
    public ResponseEntity<?> getCustomersWithCompletedOrders() {
        return customersService.getCustomersWithCompletedOrders();
    }

    
    @GetMapping("/shipments/overdue")
    public ResponseEntity<?> getCustomersWithOverdueShipments() {
        return customersService.getCustomersWithOverdueShipments();
    }
    
    @GetMapping("/shipment/{status}")
    public long getCustomerCountByShipmentStatus(@PathVariable String status) {         
    return customersService.getCustomerCountByShipmentStatus(status);
     }
  
    @GetMapping("/orders/quantity/{min}/{max}")
    public ResponseEntity<?> getCustomersByOrderQuantity(
            @PathVariable Integer min, 
            @PathVariable Integer max) {
        return customersService.getCustomersByOrderQuantity(min, max);
    }
    
}
    

