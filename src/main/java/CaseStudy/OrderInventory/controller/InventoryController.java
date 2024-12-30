package CaseStudy.OrderInventory.controller;

import CaseStudy.OrderInventory.DAO.OrdersDAO;
import CaseStudy.OrderInventory.model.Inventory;
import CaseStudy.OrderInventory.model.OrderItems;
import CaseStudy.OrderInventory.model.Orders;
import CaseStudy.OrderInventory.service.InventoryService;
import CaseStudy.OrderInventory.service.OrdersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    @Autowired 
    private OrdersService orderService;
    @Autowired
    private OrdersDAO ordersDAO;
    
//1.Fetch all inventory records
    
    @GetMapping("/all")
    public ResponseEntity<List<Inventory>> getAllInventory() {
        List<Inventory> inventoryList = inventoryService.getAllInventory();
        System.out.println(inventoryList);
        return new ResponseEntity<>(inventoryList, HttpStatus.OK);
    }
    
//2.Fetch productdata, storedata, orderstatus matching storid
    
    @GetMapping(params = "storeid")
    public ResponseEntity<List<Inventory>> getInventoryByStore(@RequestParam("storeid") int storeId) {
        List<Inventory> inventoryList = inventoryService.getInventoryByStore(storeId);
        if (inventoryList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(inventoryList, HttpStatus.OK);
    }
//3.Fetch inventories and matching shipments
    
    @GetMapping("/shipment")
    public ResponseEntity<?> getShipmentsWithInventories() {
        try {
            // Replace with the service call logic
            List<Inventory> inventoryRecords = inventoryService.getInventoriesWithShipments();
            if (inventoryRecords.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No inventories found matching shipments.");
            }
            return ResponseEntity.ok(inventoryRecords);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An internal server error occurred while fetching inventory records matching shipments.");
        }
    }
  
    //4.Fetch store, product and customer data matching order id
    
  /*  @GetMapping("/{orderId}/info")
    public ResponseEntity<?> getOrderItemsByOrderId(@PathVariable int orderId) {
        try {
            List<OrderItems> orderItems = orderService.getOrderItemsByOrderId(orderId);

            if (orderItems.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Store, product, and customer data for the specified order ID not found.");
            }

            return ResponseEntity.ok(orderItems);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request.");
        }
    }*/
  //4. Fetch store, product, and customer data matching order id

    @GetMapping("/order/{orderId}/details")
    public ResponseEntity<?> getStoreProductCustomerByOrderId(@PathVariable int orderId) {
        try {
            Orders order = ordersDAO.findById(orderId).orElse(null);

            if (order == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Store, product, and customer data for the specified order ID not found.");
            }

            Map<String, Object> response = new HashMap<>();

            // Extract store details
            response.put("storeName", order.getStoreId().getStoreName());
            response.put("storeAddress", order.getStoreId().getPhysicalAddress());

            // Extract customer details
            response.put("customerName", order.getCustomerId().getFullName());
            response.put("customerEmail", order.getCustomerId().getEmailAddress());

            // Extract product details
            List<OrderItems> orderItems = order.getOrderItems();
            List<Map<String, Object>> products = new ArrayList<>();

            for (OrderItems item : orderItems) {
                Map<String, Object> productDetails = new HashMap<>();
                productDetails.put("productName", item.getProduct().getProductName());
                productDetails.put("unitPrice", item.getProduct().getUnitPrice());
                productDetails.put("quantity", item.getQuantity());
              
                products.add(productDetails);
            }

            response.put("products", products);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching store, product, and customer details for the order ID.");
        }
    }

 //5.Count shipment status wise count of total products sold
    
    @GetMapping("/shipment/count/{storeId}")    
    public ResponseEntity<Map<String, Long>> getShipmentStatusCount(@PathVariable int storeId) {        
     Map<String, Long> shipmentStatusCount = inventoryService.getShipmentStatusCount(storeId);       
     return ResponseEntity.ok(shipmentStatusCount);    
    }
  //6.Retrieve inventory records for a specific product and store
    
    @GetMapping("/{orderId}/details")
    public ResponseEntity<?> getInventoryDetailsByOrderId(@PathVariable int orderId) {
        try {
            List<Map<String, Object>> inventoryDetails = inventoryService.getInventoryDetailsByOrderId(orderId);
            
            if (inventoryDetails.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("List of products in the specified order ID not found with store details, shipment status, and total amount.");
            }
            return ResponseEntity.ok(inventoryDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching inventory details.");
        }
    }

//7.Retrieve inventory records for a specific product and store
    @GetMapping("/product/{productId}/store/{storeId}")
    public ResponseEntity<List<Inventory>> getInventoryByStoreAndProduct(
            @PathVariable int productId, @PathVariable int storeId) {
        List<Inventory> inventoryList = inventoryService.getInventoryByStoreAndProduct(storeId, productId);
        if (inventoryList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(inventoryList, HttpStatus.OK);
    }

  
 
   //8.Retrieve inventory records for products in a specific category
    
    

        @PostMapping("/create")
        public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
            try {
                Inventory createdInventory = inventoryService.createInventory(inventory);
                return new ResponseEntity<>(createdInventory, HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        @PutMapping("/update/{inventoryId}")
        public ResponseEntity<Inventory> updateInventory(@PathVariable int inventoryId, @RequestBody Inventory updatedInventory) {
            try {
                Inventory existingInventory = inventoryService.getInventoryById(inventoryId);
                if (existingInventory == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                existingInventory.setProductInventory(updatedInventory.getProductInventory());
                existingInventory.setStoreId(updatedInventory.getStoreId());
                existingInventory.setProductId(updatedInventory.getProductId());
                Inventory updated = inventoryService.updateInventory(existingInventory);
                return new ResponseEntity<>(updated, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @DeleteMapping("/delete/{inventoryId}")
        public ResponseEntity<Void> deleteInventory(@PathVariable int inventoryId) {
            try {
                Inventory existingInventory = inventoryService.getInventoryById(inventoryId);
                if (existingInventory == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                inventoryService.deleteInventory(existingInventory);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    
 

}

