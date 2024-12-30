package CaseStudy.OrderInventory.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import CaseStudy.OrderInventory.DAO.OrdersDAO;
import CaseStudy.OrderInventory.model.*;
import CaseStudy.OrderInventory.service.InventoryService;
import CaseStudy.OrderInventory.service.OrdersService;

class InventoryControllerTest {

    @InjectMocks
    private InventoryController inventoryController;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private OrdersService ordersService;

    @Mock
    private OrdersDAO ordersDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllInventory() {
        List<Inventory> mockInventoryList = new ArrayList<>();
        mockInventoryList.add(new Inventory());

        when(inventoryService.getAllInventory()).thenReturn(mockInventoryList);

        ResponseEntity<List<Inventory>> response = inventoryController.getAllInventory();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockInventoryList, response.getBody());
        verify(inventoryService, times(1)).getAllInventory();
    }

    @Test
    void testGetInventoryByStore() {
        int storeId = 1;
        List<Inventory> mockInventoryList = new ArrayList<>();
        mockInventoryList.add(new Inventory());

        when(inventoryService.getInventoryByStore(storeId)).thenReturn(mockInventoryList);

        ResponseEntity<List<Inventory>> response = inventoryController.getInventoryByStore(storeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockInventoryList, response.getBody());
        verify(inventoryService, times(1)).getInventoryByStore(storeId);
    }

    @Test
    void testGetShipmentsWithInventories() {
        List<Inventory> mockInventoryList = new ArrayList<>();
        mockInventoryList.add(new Inventory());

        when(inventoryService.getInventoriesWithShipments()).thenReturn(mockInventoryList);

        ResponseEntity<?> response = inventoryController.getShipmentsWithInventories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockInventoryList, response.getBody());
        verify(inventoryService, times(1)).getInventoriesWithShipments();
    }

    @Test
    void testGetStoreProductCustomerByOrderId() {
        int orderId = 1;

        // Mocking Stores
        Stores mockStore = new Stores(1, "Test Store", "https://teststore.com", "123 Main St", null, null, null, null, null, null, null);

        // Mocking Customers
        Customers mockCustomer = new Customers();
        mockCustomer.setCustomerId(1);
        mockCustomer.setFullName("John Doe");
        mockCustomer.setEmailAddress("john.doe@example.com");

        // Mocking Products
        Products mockProduct = new Products();
        mockProduct.setProductId(1);
        mockProduct.setProductName("Test Product");
        mockProduct.setUnitPrice(100.0);
        mockProduct.setBrand("Test Brand");
        mockProduct.setColour("Blue");
        mockProduct.setSize("M");
        mockProduct.setRating(5);

        // Mocking OrderItems
        OrderItems mockOrderItem = new OrderItems();
        mockOrderItem.setLineItemId(1);
        mockOrderItem.setProduct(mockProduct);
        mockOrderItem.setQuantity(2);

        List<OrderItems> mockOrderItems = new ArrayList<>();
        mockOrderItems.add(mockOrderItem);

        // Mocking Orders
        Orders mockOrder = new Orders();
        mockOrder.setOrderId(orderId);
        mockOrder.setStoreId(mockStore);
        mockOrder.setCustomerId(mockCustomer);
        mockOrder.setOrderItems(mockOrderItems);

        when(ordersDAO.findById(orderId)).thenReturn(Optional.of(mockOrder));

        ResponseEntity<?> response = inventoryController.getStoreProductCustomerByOrderId(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertEquals("Test Store", responseBody.get("storeName"));
        assertEquals("John Doe", responseBody.get("customerName"));
        verify(ordersDAO, times(1)).findById(orderId);
    }

    @Test
    void testGetShipmentStatusCount() {
        int storeId = 1;
        Map<String, Long> mockStatusCount = new HashMap<>();
        mockStatusCount.put("Shipped", 10L);

        when(inventoryService.getShipmentStatusCount(storeId)).thenReturn(mockStatusCount);

        ResponseEntity<Map<String, Long>> response = inventoryController.getShipmentStatusCount(storeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockStatusCount, response.getBody());
        verify(inventoryService, times(1)).getShipmentStatusCount(storeId);
    }

    @Test
    void testGetInventoryDetailsByOrderId() {
        int orderId = 1;
        List<Map<String, Object>> mockDetails = new ArrayList<>();
        Map<String, Object> mockDetail = new HashMap<>();
        mockDetail.put("storeName", "Test Store");
        mockDetails.add(mockDetail);

        when(inventoryService.getInventoryDetailsByOrderId(orderId)).thenReturn(mockDetails);

        ResponseEntity<?> response = inventoryController.getInventoryDetailsByOrderId(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockDetails, response.getBody());
        verify(inventoryService, times(1)).getInventoryDetailsByOrderId(orderId);
    }

    @Test
    void testGetInventoryByStoreAndProduct() {
        int storeId = 1;
        int productId = 1;
        List<Inventory> mockInventoryList = new ArrayList<>();
        mockInventoryList.add(new Inventory());

        when(inventoryService.getInventoryByStoreAndProduct(storeId, productId)).thenReturn(mockInventoryList);

        ResponseEntity<List<Inventory>> response = inventoryController.getInventoryByStoreAndProduct(productId, storeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockInventoryList, response.getBody());
        verify(inventoryService, times(1)).getInventoryByStoreAndProduct(storeId, productId);
    }
    @Test
    void testCreateInventory() {
        Inventory mockInventory = new Inventory();
        when(inventoryService.createInventory(any(Inventory.class))).thenReturn(mockInventory);

        ResponseEntity<Inventory> response = inventoryController.createInventory(mockInventory);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdateInventory() {
        int inventoryId = 1;
        Inventory existingInventory = new Inventory();
        Inventory updatedInventory = new Inventory();

        when(inventoryService.getInventoryById(inventoryId)).thenReturn(existingInventory);
        when(inventoryService.updateInventory(existingInventory)).thenReturn(updatedInventory);

        ResponseEntity<Inventory> response = inventoryController.updateInventory(inventoryId, updatedInventory);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

   
    @Test
    void testDeleteInventory() {
        int inventoryId = 1;
        Inventory existingInventory = new Inventory();
        when(inventoryService.getInventoryById(inventoryId)).thenReturn(existingInventory);

        ResponseEntity<Void> response = inventoryController.deleteInventory(inventoryId);

        assertEquals(204, response.getStatusCodeValue());
        verify(inventoryService, times(1)).deleteInventory(existingInventory);
    }

   
}
