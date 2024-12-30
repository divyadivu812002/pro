package CaseStudy.OrderInventory.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import CaseStudy.OrderInventory.service.OrderItemsService;
import CaseStudy.OrderInventory.model.OrderItems;
 
import java.util.List;
 
@Controller
@RequestMapping("/orderitems")
public class OrderItemsController {
 
    @Autowired
    private OrderItemsService orderItemsService;
 
    // Internal method to fetch all order items
    @GetMapping
    public List<OrderItems> fetchAllOrderItems() {
        return orderItemsService.getAllOrderItems();
    }
 
    // Internal method to fetch order item by ID
    @GetMapping("/{id}")
    public OrderItems fetchOrderItemById(int lineItemId) {
        return orderItemsService.getOrderItemById(lineItemId)
                .orElseThrow(() -> new IllegalArgumentException("OrderItem with ID " + lineItemId + " not found"));
    }
 
    // Internal method to save a new order item
    @PostMapping
    public ResponseEntity<String> saveOrderItem(OrderItems orderItem) {
    	
        orderItemsService.saveOrderItem(orderItem);
        return ResponseEntity.ok("Record created");
    }
    // Internal method to update an existing order item
    @PutMapping("/{id}")
    public OrderItems updateOrderItem(int lineItemId, OrderItems updatedOrderItem) {
        return orderItemsService.updateOrderItem(lineItemId, updatedOrderItem);
    }
    // Internal method to delete an order item by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderItem(int lineItemId) {
        orderItemsService.deleteOrderItem(lineItemId);
        return ResponseEntity.ok("Record deleted");
    }
}