package CaseStudy.OrderInventory.controller;

import CaseStudy.OrderInventory.model.Orders;
import CaseStudy.OrderInventory.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> ordersList = ordersService.getAllOrders();
        return new ResponseEntity<>(ordersList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Orders>> getOrderById(@PathVariable int id) {
        List<Orders> order = ordersService.getOrdersByStoreId(id);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if the order is not found
        }
        return new ResponseEntity<>(order, HttpStatus.OK); // Return 200 with the order if found
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Orders>> getOrdersByCustomerId(@PathVariable int customerId) {
        List<Orders> ordersList = ordersService.getOrdersByCustomerId(customerId);
        if (ordersList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ordersList, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Orders>> getOrdersByOrderStatus(@PathVariable String status) {
        List<Orders> ordersList = ordersService.getOrdersByOrderStatus(status);
        if (ordersList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ordersList, HttpStatus.OK);
    }

    @GetMapping("/status/count/{status}")
    public ResponseEntity<Long> getOrderCountByStatus(@PathVariable String status) {
        long count = ordersService.getOrderCountByStatus(status);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/date/{startDate}/{endDate}")
    public ResponseEntity<List<Orders>> getOrdersByDateRange(
            @PathVariable LocalDateTime startDate,
            @PathVariable LocalDateTime endDate) {
        List<Orders> ordersList = ordersService.getOrdersByDateRange(startDate, endDate);
        if (ordersList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ordersList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Orders order) {
        ordersService.createOrUpdateOrder(order);
        return new ResponseEntity<>("Order created successfully.", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updateOrder(@RequestBody Orders order) {
        ordersService.createOrUpdateOrder(order);
        return new ResponseEntity<>("Order updated successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable int id) {
        if (ordersService.deleteOrder(id)) {
            return new ResponseEntity<>("Order deleted successfully.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Order not found.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable int id) {
        String result = ordersService.cancelOrder(id);
        if ("Order not found.".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/customer/email/{email}")
    public ResponseEntity<List<Orders>> getOrdersByCustomerEmail(@PathVariable String email) {
        List<Orders> ordersList = ordersService.getOrdersByCustomerEmail(email);
        if (ordersList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ordersList, HttpStatus.OK);
    }

    @GetMapping("/store/{storeName}")
    public ResponseEntity<List<Orders>> getOrdersByStoreName(@PathVariable String storeName) {
        List<Orders> ordersList = ordersService.getOrdersByStoreName(storeName);
        if (ordersList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ordersList, HttpStatus.OK);
    }
}