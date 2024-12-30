package CaseStudy.OrderInventory.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import CaseStudy.OrderInventory.DAO.CustomersDAO;
import CaseStudy.OrderInventory.DAO.OrdersDAO;
import CaseStudy.OrderInventory.DAO.ShipmentsDAO;
import CaseStudy.OrderInventory.model.Customers;
import CaseStudy.OrderInventory.model.Orders;
import CaseStudy.OrderInventory.model.Shipments;

class CustomersServiceTest {

    @InjectMocks
    private CustomersService customersService;

    @Mock
    private CustomersDAO customersDAO;

    @Mock
    private OrdersDAO ordersDAO;

    @Mock
    private ShipmentsDAO shipmentsDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCustomer() {
        Customers customer = new Customers(1, "johndoe@example.com", "John Doe");
        customersService.addCustomer(customer);
        verify(customersDAO, times(1)).save(customer);
    }

    @Test
    void testUpdateCustomer() {
        Customers existingCustomer = new Customers(1, "johndoe@example.com", "John Doe");
        Customers updatedCustomer = new Customers(1, "john.new@example.com", "John New");

        when(customersDAO.findById(1)).thenReturn(Optional.of(existingCustomer));

        customersService.updateCustomer(updatedCustomer);

        verify(customersDAO, times(1)).save(existingCustomer);
        assertEquals("John New", existingCustomer.getFullName());
        assertEquals("john.new@example.com", existingCustomer.getEmailAddress());
    }

    @Test
    void testDeleteCustomer() {
        customersService.deleteCustomer(1);
        verify(customersDAO, times(1)).deleteById(1);
    }

    @Test
    void testGetAllCustomers() {
        List<Customers> customersList = Arrays.asList(
            new Customers(1, "johndoe@example.com", "John Doe"),
            new Customers(2, "janedoe@example.com", "Jane Doe")
        );

        when(customersDAO.findAll()).thenReturn(customersList);

        ResponseEntity<?> response = customersService.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customersList, response.getBody());
    }

    @Test
    void testGetAllCustomersByName() {
        List<Customers> customersList = Collections.singletonList(
            new Customers(1, "johndoe@example.com", "John Doe")
        );

        when(customersDAO.findByFullName("John Doe")).thenReturn(customersList);

        ResponseEntity<?> response = customersService.getAllCustomersByName("John Doe");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customersList, response.getBody());
    }

    @Test
    void testGetAllCustomersByEmailId() {
        List<Customers> customersList = Collections.singletonList(
            new Customers(1, "johndoe@example.com", "John Doe")
        );

        when(customersDAO.findByEmailAddress("johndoe@example.com")).thenReturn(customersList);

        ResponseEntity<?> response = customersService.getAllCustomersByEmailId("johndoe@example.com");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customersList, response.getBody());
    }

    @Test
    void testGetOrdersByCustomerId() {
        List<Orders> ordersList = Collections.singletonList(new Orders());
        when(ordersDAO.findByCustomerId_CustomerId(1)).thenReturn(ordersList);

        ResponseEntity<?> response = customersService.getOrdersByCustomerId(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ordersList, response.getBody());
    }

    @Test
    void testGetShipmentHistoryByCustomerId() {
        List<Shipments> shipmentsList = Collections.singletonList(new Shipments());
        when(shipmentsDAO.findByCustomer_CustomerId(1)).thenReturn(shipmentsList);

        ResponseEntity<?> response = customersService.getShipmentHistoryByCustomerId(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shipmentsList, response.getBody());
    }

    @Test
    void testGetCustomersWithPendingShipments() {
        List<Customers> customersList = Collections.singletonList(
            new Customers(1, "johndoe@example.com", "John Doe")
        );

        when(customersDAO.findByShipments_ShipmentStatus("PENDING")).thenReturn(customersList);

        ResponseEntity<?> response = customersService.getCustomersWithPendingShipments();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customersList, response.getBody());
    }

    @Test
    void testGetCustomersWithCompletedOrders() {
        List<Customers> customersList = Collections.singletonList(
            new Customers(1, "johndoe@example.com", "John Doe")
        );

        when(customersDAO.findByOrders_OrderStatus("COMPLETED")).thenReturn(customersList);

        ResponseEntity<?> response = customersService.getCustomersWithCompletedOrders();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customersList, response.getBody());
    }

    @Test
    void testGetCustomersWithOverdueShipments() {
        List<Customers> customersList = Collections.singletonList(
            new Customers(1, "johndoe@example.com", "John Doe")
        );

        when(customersDAO.findByShipments_ShipmentStatus("OVERDUE")).thenReturn(customersList);

        ResponseEntity<?> response = customersService.getCustomersWithOverdueShipments();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customersList, response.getBody());
    }

    @Test
    void testGetCustomersByOrderQuantity() {
        List<Customers> customersList = Collections.singletonList(
            new Customers(1, "johndoe@example.com", "John Doe")
        );

        when(customersDAO.findByOrders_OrderItems_QuantityBetween(5, 10)).thenReturn(customersList);

        ResponseEntity<?> response = customersService.getCustomersByOrderQuantity(5, 10);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customersList, response.getBody());
    }
}
