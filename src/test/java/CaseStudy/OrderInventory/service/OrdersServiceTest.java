package CaseStudy.OrderInventory.service;

import CaseStudy.OrderInventory.DAO.CustomersDAO;
import CaseStudy.OrderInventory.DAO.OrdersDAO;
import CaseStudy.OrderInventory.DAO.StoresDAO;
import CaseStudy.OrderInventory.model.Customers;
import CaseStudy.OrderInventory.model.Orders;
import CaseStudy.OrderInventory.model.Stores;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrdersServiceTest {

    @InjectMocks
    private OrdersService ordersService;

    @Mock
    private OrdersDAO ordersDAO;

    @Mock
    private CustomersDAO customersDAO;

    @Mock
    private StoresDAO storesDAO;

    @Test
    public void testGetAllOrders() {
        List<Orders> mockOrders = new ArrayList<>();
        mockOrders.add(new Orders());
        mockOrders.add(new Orders());

        when(ordersDAO.findAll()).thenReturn(mockOrders);

        List<Orders> allOrders = ordersService.getAllOrders();

        assertEquals(2, allOrders.size());
        verify(ordersDAO, times(1)).findAll();
    }

    @Test
    public void testGetOrdersByStoreId() {
        int storeId = 1;
        List<Orders> mockOrders = new ArrayList<>();
        mockOrders.add(new Orders());

        when(ordersDAO.findByStoreId_StoreId(storeId)).thenReturn(mockOrders);

        List<Orders> ordersByStore = ordersService.getOrdersByStoreId(storeId);

        assertEquals(1, ordersByStore.size());
        verify(ordersDAO, times(1)).findByStoreId_StoreId(storeId);
    }

    @Test
    public void testGetOrdersByCustomerId() {
        int customerId = 1;
        List<Orders> mockOrders = new ArrayList<>();
        mockOrders.add(new Orders());

        when(ordersDAO.findByCustomerId_CustomerId(customerId)).thenReturn(mockOrders);

        List<Orders> ordersByCustomer = ordersService.getOrdersByCustomerId(customerId);

        assertEquals(1, ordersByCustomer.size());
        verify(ordersDAO, times(1)).findByCustomerId_CustomerId(customerId);
    }

    @Test
    public void testGetOrdersByOrderStatus() {
        String orderStatus = "Pending";
        List<Orders> mockOrders = new ArrayList<>();
        mockOrders.add(new Orders());

        when(ordersDAO.findByOrderStatus(orderStatus)).thenReturn(mockOrders);

        List<Orders> ordersByStatus = ordersService.getOrdersByOrderStatus(orderStatus);

        assertEquals(1, ordersByStatus.size());
        verify(ordersDAO, times(1)).findByOrderStatus(orderStatus);
    }

    @Test
    public void testGetOrderCountByStatus() {
        String orderStatus = "Pending";
        List<Orders> mockOrders = new ArrayList<>();
        mockOrders.add(new Orders());
        mockOrders.add(new Orders());

        when(ordersDAO.findByOrderStatus(orderStatus)).thenReturn(mockOrders);

        long orderCount = ordersService.getOrderCountByStatus(orderStatus);

        assertEquals(2, orderCount);
        verify(ordersDAO, times(1)).findByOrderStatus(orderStatus);
    }

    @Test
    public void testGetOrdersByDateRange() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();
        List<Orders> mockOrders = new ArrayList<>();
        mockOrders.add(new Orders());

        when(ordersDAO.findByOrderTmsBetween(startDate, endDate)).thenReturn(mockOrders);

        List<Orders> ordersByDateRange = ordersService.getOrdersByDateRange(startDate, endDate);

        assertEquals(1, ordersByDateRange.size());
        verify(ordersDAO, times(1)).findByOrderTmsBetween(startDate, endDate);
    }

    @Test
    public void testCancelOrder_Success() {
        int orderId = 1;
        Orders mockOrder = new Orders();
        mockOrder.setOrderId(orderId);

        when(ordersDAO.findById(orderId)).thenReturn(Optional.of(mockOrder));

        String result = ordersService.cancelOrder(orderId);

        assertEquals("Order canceled successfully.", result);
        verify(ordersDAO, times(1)).findById(orderId);
        verify(ordersDAO, times(1)).save(mockOrder);
    }

    @Test
    public void testCancelOrder_NotFound() {
        int orderId = 1;

        when(ordersDAO.findById(orderId)).thenReturn(Optional.empty());

        String result = ordersService.cancelOrder(orderId);

        assertEquals("Order not found.", result);
        verify(ordersDAO, times(1)).findById(orderId);
        verify(ordersDAO, times(0)).save(any(Orders.class));
    }

    @Test
    public void testCreateOrUpdateOrder() {
        Orders order = new Orders();
        order.setCustomerId(new Customers());
        order.setStoreId(new Stores());

        when(customersDAO.findById(order.getCustomerId().getCustomerId())).thenReturn(Optional.of(order.getCustomerId()));
        when(storesDAO.findById(order.getStoreId().getStoreId())).thenReturn(Optional.of(order.getStoreId()));

        ordersService.createOrUpdateOrder(order);

        verify(customersDAO, times(1)).findById(order.getCustomerId().getCustomerId());
        verify(storesDAO, times(1)).findById(order.getStoreId().getStoreId());
        verify(ordersDAO, times(1)).save(order);
    }

    @Test
    public void testDeleteOrder_Success() {
        int orderId = 1;

        when(ordersDAO.existsById(orderId)).thenReturn(true);

        boolean result = ordersService.deleteOrder(orderId);

        assertTrue(result);
        verify(ordersDAO, times(1)).existsById(orderId);
        verify(ordersDAO, times(1)).deleteById(orderId);
    }

    @Test
    public void testDeleteOrder_NotFound() {
        int orderId = 1;

        when(ordersDAO.existsById(orderId)).thenReturn(false);

        boolean result = ordersService.deleteOrder(orderId);

        assertFalse(result);
        verify(ordersDAO, times(1)).existsById(orderId);
        verify(ordersDAO, times(0)).deleteById(orderId);
    }

    @Test
    public void testGetOrdersByCustomerEmail() {
        String email = "test@example.com";
        List<Orders> mockOrders = new ArrayList<>();
        mockOrders.add(new Orders());

        when(ordersDAO.findByCustomerId_EmailAddress(email)).thenReturn(mockOrders);

        List<Orders> ordersByCustomerEmail = ordersService.getOrdersByCustomerEmail(email);

        assertEquals(1, ordersByCustomerEmail.size());
        verify(ordersDAO, times(1)).findByCustomerId_EmailAddress(email);
    }

    @Test
    public void testGetOrdersByStoreName() {
        String storeName = "Test Store";
        List<Orders> mockOrders = new ArrayList<>();
        mockOrders.add(new Orders());

        when(ordersDAO.findByStoreId_StoreName(storeName)).thenReturn(mockOrders);

        List<Orders> ordersByStoreName = ordersService.getOrdersByStoreName(storeName);

        assertEquals(1, ordersByStoreName.size());
        verify(ordersDAO, times(1)).findByStoreId_StoreName(storeName);
    }

    @Test
    public void testGetOrderById() {
        int orderId = 1;
        List<Orders> mockOrders = new ArrayList<>();
        mockOrders.add(new Orders());

        when(ordersDAO.findByOrderId(orderId)).thenReturn(mockOrders);

        List<Orders> orderById = ordersService.getOrderById(orderId);

        assertEquals(1, orderById.size());
        verify(ordersDAO, times(1)).findByOrderId(orderId);
    }
}