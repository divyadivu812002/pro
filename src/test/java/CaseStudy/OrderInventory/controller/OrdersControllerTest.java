package CaseStudy.OrderInventory.controller;

import CaseStudy.OrderInventory.model.Customers;
import CaseStudy.OrderInventory.model.OrderItems;
import CaseStudy.OrderInventory.model.Orders;
import CaseStudy.OrderInventory.model.Stores;
import CaseStudy.OrderInventory.service.OrdersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrdersControllerTest {

    @InjectMocks
    private OrdersController ordersController;

    @Mock
    private OrdersService ordersService;

    private Orders mockOrder;
    private List<Orders> mockOrdersList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockOrder = new Orders(1, new ArrayList<>(), LocalDateTime.now(), new Customers(), "PROCESSING", new Stores());
        mockOrdersList = new ArrayList<>();
        mockOrdersList.add(mockOrder);
    }

    @Test
    void testGetAllOrders() {
        when(ordersService.getAllOrders()).thenReturn(mockOrdersList);

        ResponseEntity<List<Orders>> response = ordersController.getAllOrders();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(ordersService, times(1)).getAllOrders();
    }

    @Test
    void testGetOrderById() {
        when(ordersService.getOrdersByStoreId(1)).thenReturn(mockOrdersList);

        ResponseEntity<List<Orders>> response = ordersController.getOrderById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(ordersService, times(1)).getOrdersByStoreId(1);
    }

    @Test
    void testGetOrdersByCustomerId() {
        when(ordersService.getOrdersByCustomerId(1)).thenReturn(mockOrdersList);

        ResponseEntity<List<Orders>> response = ordersController.getOrdersByCustomerId(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(ordersService, times(1)).getOrdersByCustomerId(1);
    }

    @Test
    void testGetOrdersByOrderStatus() {
        when(ordersService.getOrdersByOrderStatus("PROCESSING")).thenReturn(mockOrdersList);

        ResponseEntity<List<Orders>> response = ordersController.getOrdersByOrderStatus("PROCESSING");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(ordersService, times(1)).getOrdersByOrderStatus("PROCESSING");
    }

    @Test
    void testCreateOrder() {
        doNothing().when(ordersService).createOrUpdateOrder(mockOrder);

        ResponseEntity<String> response = ordersController.createOrder(mockOrder);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Order created successfully.", response.getBody());
        verify(ordersService, times(1)).createOrUpdateOrder(mockOrder);
    }

    @Test
    void testUpdateOrder() {
        doNothing().when(ordersService).createOrUpdateOrder(mockOrder);

        ResponseEntity<String> response = ordersController.updateOrder(mockOrder);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Order updated successfully.", response.getBody());
        verify(ordersService, times(1)).createOrUpdateOrder(mockOrder);
    }

    @Test
    void testDeleteOrder() {
        when(ordersService.deleteOrder(1)).thenReturn(true);

        ResponseEntity<String> response = ordersController.deleteOrder(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Order deleted successfully.", response.getBody());
        verify(ordersService, times(1)).deleteOrder(1);
    }

    @Test
    void testCancelOrder() {
        when(ordersService.cancelOrder(1)).thenReturn("Order cancelled successfully.");

        ResponseEntity<String> response = ordersController.cancelOrder(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Order cancelled successfully.", response.getBody());
        verify(ordersService, times(1)).cancelOrder(1);
    }
    
    @Test
    void testGetOrdersByCustomerEmailNotFound() {
        String email = "nonexistent@example.com";
        when(ordersService.getOrdersByCustomerEmail(email)).thenReturn(new ArrayList<>());

        ResponseEntity<List<Orders>> response = ordersController.getOrdersByCustomerEmail(email);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(ordersService, times(1)).getOrdersByCustomerEmail(email);
    }
    
    @Test
    void testGetOrdersByCustomerEmailFound() {
        String email = "customer@example.com";
        when(ordersService.getOrdersByCustomerEmail(email)).thenReturn(mockOrdersList);

        ResponseEntity<List<Orders>> response = ordersController.getOrdersByCustomerEmail(email);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(ordersService, times(1)).getOrdersByCustomerEmail(email);
    }
    
    @Test
    void testGetOrdersByStoreNameNotFound() {
        String storeName = "Nonexistent Store";
        when(ordersService.getOrdersByStoreName(storeName)).thenReturn(new ArrayList<>());

        ResponseEntity<List<Orders>> response = ordersController.getOrdersByStoreName(storeName);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(ordersService, times(1)).getOrdersByStoreName(storeName);
    }

    @Test
    void testGetOrdersByStoreNameFound() {
        String storeName = "Store A";
        when(ordersService.getOrdersByStoreName(storeName)).thenReturn(mockOrdersList);

        ResponseEntity<List<Orders>> response = ordersController.getOrdersByStoreName(storeName);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(ordersService, times(1)).getOrdersByStoreName(storeName);
    }
    
    @Test
    void testGetOrderCountByStatus() {
        String status = "PROCESSING";
        long expectedCount = 5;
        when(ordersService.getOrderCountByStatus(status)).thenReturn(expectedCount);

        ResponseEntity<Long> response = ordersController.getOrderCountByStatus(status);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedCount, response.getBody());
        verify(ordersService, times(1)).getOrderCountByStatus(status);
    }

}
