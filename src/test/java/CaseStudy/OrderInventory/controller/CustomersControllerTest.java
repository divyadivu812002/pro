package CaseStudy.OrderInventory.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import CaseStudy.OrderInventory.model.Customers;
import CaseStudy.OrderInventory.model.Orders;
import CaseStudy.OrderInventory.model.Shipments;
import CaseStudy.OrderInventory.service.CustomersService;

class CustomersControllerTest {

    @InjectMocks
    private CustomersController customersController;

    @Mock
    private CustomersService customersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCustomers() {
        List<Customers> mockCustomersList = Arrays.asList(new Customers(), new Customers());
        doReturn(ResponseEntity.ok(mockCustomersList)).when(customersService).getAll();

        ResponseEntity<?> response = customersController.getAllCustomers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCustomersList, response.getBody());
        verify(customersService, times(1)).getAll();
    }

    @Test
    void testAddCustomer() {
        Customers mockCustomer = new Customers();
        ResponseEntity<String> response = customersController.addCustomer(mockCustomer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Record created successfully.", response.getBody());
        verify(customersService, times(1)).addCustomer(mockCustomer);
    }

    @Test
    void testUpdateCustomer() {
        Customers mockCustomer = new Customers();
        ResponseEntity<String> response = customersController.updateCustomer(mockCustomer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Record updated successfully.", response.getBody());
        verify(customersService, times(1)).updateCustomer(mockCustomer);
    }

    @Test
    void testDeleteCustomer() {
        Integer customerId = 1;
        ResponseEntity<String> response = customersController.deleteCustomer(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Record deleted successfully.", response.getBody());
        verify(customersService, times(1)).deleteCustomer(customerId);
    }

    @Test
    void testGetAllCustomersByEmailId() {
        String emailId = "test@example.com";
        List<Customers> mockCustomersList = Arrays.asList(new Customers());

        doReturn(ResponseEntity.ok(mockCustomersList)).when(customersService).getAllCustomersByEmailId(emailId);

        ResponseEntity<?> response = customersController.getAllCustomersByEmailId(emailId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCustomersList, response.getBody());
        verify(customersService, times(1)).getAllCustomersByEmailId(emailId);
    }

    @Test
    void testGetAllCustomersByName() {
        String name = "John Doe";
        List<Customers> mockCustomersList = Arrays.asList(new Customers());

        doReturn(ResponseEntity.ok(mockCustomersList)).when(customersService).getAllCustomersByEmailId(name);

        ResponseEntity<?> response = customersController.getAllCustomersByName(name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCustomersList, response.getBody());
        verify(customersService, times(1)).getAllCustomersByEmailId(name);
    }

    @Test
    void testGetOrdersByCustomerId() {
        Integer custId = 1;
        List<Orders> mockOrdersList = Arrays.asList(new Orders());

        doReturn(ResponseEntity.ok(mockOrdersList)).when(customersService).getOrdersByCustomerId(custId);

        ResponseEntity<?> response = customersController.getOrdersByCustomerId(custId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockOrdersList, response.getBody());
        verify(customersService, times(1)).getOrdersByCustomerId(custId);
    }

    @Test
    void testGetShipmentHistoryByCustomerId() {
        Integer custId = 1;
        List<Shipments> mockShipmentsList = Arrays.asList(new Shipments());

        doReturn(ResponseEntity.ok(mockShipmentsList)).when(customersService).getShipmentHistoryByCustomerId(custId);

        ResponseEntity<?> response = customersController.getShipmentHistoryByCustomerId(custId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockShipmentsList, response.getBody());
        verify(customersService, times(1)).getShipmentHistoryByCustomerId(custId);
    }

    @Test
    void testGetCustomersWithPendingShipments() {
        List<Customers> mockPendingShipments = Arrays.asList(new Customers());

        doReturn(ResponseEntity.ok(mockPendingShipments)).when(customersService).getCustomersWithPendingShipments();

        ResponseEntity<?> response = customersController.getCustomersWithPendingShipments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPendingShipments, response.getBody());
        verify(customersService, times(1)).getCustomersWithPendingShipments();
    }

    @Test
    void testGetCustomersWithCompletedOrders() {
       List<Customers> mockCompletedOrders = Arrays.asList(new Customers());

       doReturn(ResponseEntity.ok(mockCompletedOrders)).when(customersService).getCustomersWithCompletedOrders();

       ResponseEntity<?> response = customersController.getCustomersWithCompletedOrders();

       assertEquals(HttpStatus.OK, response.getStatusCode());
       assertEquals(mockCompletedOrders, response.getBody());
       verify(customersService, times(1)).getCustomersWithCompletedOrders();
   }

   @Test
   void testGetCustomersWithOverdueShipments() {
       List<Customers> mockOverdueShipments = Arrays.asList(new Customers());

       doReturn(ResponseEntity.ok(mockOverdueShipments)).when(customersService).getCustomersWithOverdueShipments();

       ResponseEntity<?> response = customersController.getCustomersWithOverdueShipments();

       assertEquals(HttpStatus.OK, response.getStatusCode());
       assertEquals(mockOverdueShipments, response.getBody());
       verify(customersService, times(1)).getCustomersWithOverdueShipments();
   }

   @Test
   void testGetCustomerCountByShipmentStatus() {
       String status = "PENDING";
       long mockCount = 10;

       when(customersService.getCustomerCountByShipmentStatus(status)).thenReturn(mockCount);

       long countResponse = customersController.getCustomerCountByShipmentStatus(status);

       assertEquals(mockCount, countResponse);
       verify(customersService, times(1)).getCustomerCountByShipmentStatus(status);
   }

   @Test
   void testGetCustomersByOrderQuantity() {
       Integer minQuantity = 5;
       Integer maxQuantity = 10;
       List<Customers> mockQuantityList = Arrays.asList(new Customers());

       doReturn(ResponseEntity.ok(mockQuantityList)).when(customersService).getCustomersByOrderQuantity(minQuantity, maxQuantity);

       ResponseEntity<?> response = customersController.getCustomersByOrderQuantity(minQuantity, maxQuantity);

       assertEquals(HttpStatus.OK, response.getStatusCode());
       assertEquals(mockQuantityList, response.getBody());
       verify(customersService, times(1)).getCustomersByOrderQuantity(minQuantity, maxQuantity);
   }
}
