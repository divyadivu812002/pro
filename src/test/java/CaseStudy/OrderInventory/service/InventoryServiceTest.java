package CaseStudy.OrderInventory.service;

import CaseStudy.OrderInventory.DAO.InventoryDAO;
import CaseStudy.OrderInventory.DAO.OrderItemsDAO;
import CaseStudy.OrderInventory.DAO.OrdersDAO;
import CaseStudy.OrderInventory.DAO.ShipmentsDAO;
import CaseStudy.OrderInventory.model.Inventory;
import CaseStudy.OrderInventory.model.Orders;
import CaseStudy.OrderInventory.model.Shipments;
import CaseStudy.OrderInventory.service.InventoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceTest {

    @InjectMocks
    private InventoryService inventoryService;

    @Mock
    private InventoryDAO inventoryDAO;

    @Mock
    private ShipmentsDAO shipmentsDAO;

    @Mock
    private OrdersDAO ordersDAO;

    @Mock
    private OrderItemsDAO orderItemsDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllInventory() {
        List<Inventory> mockInventories = new ArrayList<>();
        when(inventoryDAO.findAll()).thenReturn(mockInventories);

        List<Inventory> result = inventoryService.getAllInventory();

        assertNotNull(result);
        assertEquals(mockInventories, result);
        verify(inventoryDAO, times(1)).findAll();
    }

    @Test
    void testGetInventoryByStore() {
        int storeId = 1;
        List<Inventory> mockInventories = new ArrayList<>();
        when(inventoryDAO.findByStoreId_StoreId(storeId)).thenReturn(mockInventories);

        List<Inventory> result = inventoryService.getInventoryByStore(storeId);

        assertNotNull(result);
        assertEquals(mockInventories, result);
        verify(inventoryDAO, times(1)).findByStoreId_StoreId(storeId);
    }

    @Test
    void testGetInventoryByProduct() {
        int productId = 1;
        List<Inventory> mockInventories = new ArrayList<>();
        when(inventoryDAO.findByProductId_ProductId(productId)).thenReturn(mockInventories);

        List<Inventory> result = inventoryService.getInventoryByProduct(productId);

        assertNotNull(result);
        assertEquals(mockInventories, result);
        verify(inventoryDAO, times(1)).findByProductId_ProductId(productId);
    }

    @Test
    void testGetInventoryByStoreAndProduct() {
        int storeId = 1;
        int productId = 1;
        List<Inventory> mockInventories = new ArrayList<>();
        when(inventoryDAO.findByStoreId_StoreIdAndProductId_ProductId(storeId, productId)).thenReturn(mockInventories);

        List<Inventory> result = inventoryService.getInventoryByStoreAndProduct(storeId, productId);

        assertNotNull(result);
        assertEquals(mockInventories, result);
        verify(inventoryDAO, times(1)).findByStoreId_StoreIdAndProductId_ProductId(storeId, productId);
    }

    @Test
    void testGetShipmentStatusCount() {
        int storeId = 1;
        List<Inventory> mockInventories = new ArrayList<>();
        List<Shipments> mockShipments = new ArrayList<>();
        when(inventoryDAO.findByStoreId_StoreId(storeId)).thenReturn(mockInventories);
        when(shipmentsDAO.findByStoreId_StoreId(storeId)).thenReturn(mockShipments);

        Map<String, Long> result = inventoryService.getShipmentStatusCount(storeId);

        assertNotNull(result);
        verify(inventoryDAO, times(1)).findByStoreId_StoreId(storeId);
    }

    @Test
    void testGetInventoriesWithShipments() {
        List<Inventory> mockInventories = new ArrayList<>();
        when(inventoryDAO.findAll()).thenReturn(mockInventories);

        List<Inventory> result = inventoryService.getInventoriesWithShipments();

        assertNotNull(result);
        verify(inventoryDAO, times(1)).findAll();
    }

    @Test
    void testGetInventoryDetailsByOrderId() {
        int orderId = 1;
        Orders mockOrder = mock(Orders.class);
        List<Inventory> mockInventories = new ArrayList<>();
        List<Shipments> mockShipments = new ArrayList<>();

        when(ordersDAO.findById(orderId)).thenReturn(java.util.Optional.ofNullable(mockOrder));
        when(inventoryDAO.findByStoreId_StoreId(anyInt())).thenReturn(mockInventories);
        when(shipmentsDAO.findByStoreId_StoreId(anyInt())).thenReturn(mockShipments);

        List<Map<String, Object>> result = inventoryService.getInventoryDetailsByOrderId(orderId);

        assertNotNull(result);
        verify(ordersDAO, times(1)).findById(orderId);
    }


    @Test
    void testCreateInventory() {
        Inventory mockInventory = new Inventory();
        when(inventoryDAO.save(any(Inventory.class))).thenReturn(mockInventory);

        Inventory result = inventoryService.createInventory(mockInventory);

        assertNotNull(result);
        verify(inventoryDAO, times(1)).save(mockInventory);
    }

    @Test
    void testUpdateInventory() {
        Inventory mockInventory = new Inventory();
        when(inventoryDAO.save(any(Inventory.class))).thenReturn(mockInventory);

        Inventory result = inventoryService.updateInventory(mockInventory);

        assertNotNull(result);
        verify(inventoryDAO, times(1)).save(mockInventory);
    }

    @Test
    void testDeleteInventory() {
        Inventory mockInventory = new Inventory();

        doNothing().when(inventoryDAO).delete(mockInventory);

        inventoryService.deleteInventory(mockInventory);

        verify(inventoryDAO, times(1)).delete(mockInventory);
    }
}
