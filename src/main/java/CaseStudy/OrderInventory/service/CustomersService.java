package CaseStudy.OrderInventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

import CaseStudy.OrderInventory.DAO.CustomersDAO;
import CaseStudy.OrderInventory.DAO.OrdersDAO;
import CaseStudy.OrderInventory.DAO.ShipmentsDAO;
import CaseStudy.OrderInventory.model.Customers;
import CaseStudy.OrderInventory.model.Shipments;
import CaseStudy.OrderInventory.model.Orders;

@Service
public class CustomersService {
    @Autowired
    private CustomersDAO customersDAO;
    @Autowired
    private OrdersDAO ordersDAO;
    @Autowired
    private ShipmentsDAO shipmentsDAO;
    
    public void addCustomer(Customers customer) {
        customersDAO.save(customer);
    }

    public void updateCustomer(Customers customer) {	
		Customers customers = customersDAO.findById(customer.getCustomerId()).orElse(null);
		if (customers != null) {
		    customers.setFullName(customer.getFullName());
		    customers.setEmailAddress(customer.getEmailAddress());
		    customersDAO.save(customers);
	}
		}
    
    public void deleteCustomer(int customerId) {
        customersDAO.deleteById(customerId);
    }

    public long getCustomerCountByShipmentStatus(String status) {         
    	return customersDAO.countDistinctByShipments_ShipmentStatus(status); 
    	}
    public ResponseEntity<?> getAll() {
        List<Customers> customerList = customersDAO.findAll();
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllCustomersByName(String name) {
        List<Customers> customerList = customersDAO.findByFullName(name);
        if (customerList.isEmpty()) {
            return new ResponseEntity<>("Customer with the provided name not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllCustomersByEmailId(String emailId) {
        List<Customers> customerList = customersDAO.findByEmailAddress(emailId);
        if (customerList.isEmpty()) {
            return new ResponseEntity<>("Customer with the provided email Id not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    public ResponseEntity<?> getOrdersByCustomerId(Integer customerId) {
        List<Orders> orders =ordersDAO.findByCustomerId_CustomerId( customerId);
        		
        if (orders.isEmpty()) {
            return new ResponseEntity<>("Orders for the specified customer ID not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    public ResponseEntity<?> getShipmentHistoryByCustomerId(Integer customerId) {
        List<Shipments> shipments = shipmentsDAO.findByCustomer_CustomerId(customerId);
        		
        if (shipments.isEmpty()) {
            return new ResponseEntity<>("Shipment history for the specified customer ID not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(shipments, HttpStatus.OK);
    }

    public ResponseEntity<?> getCustomersWithPendingShipments() {
        try {
            List<Customers> customers = customersDAO.findByShipments_ShipmentStatus("PENDING");
            if (customers.isEmpty()) {
                return new ResponseEntity<>("No customers with pending shipments found.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An internal server error occurred while fetching customers with pending shipments.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getCustomersWithCompletedOrders() {
        try {
            List<Customers> customers = customersDAO.findByOrders_OrderStatus("COMPLETED");
            if (customers.isEmpty()) {
                return new ResponseEntity<>("No customers with completed orders found.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An internal server error occurred while fetching customers with completed orders.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getCustomersWithOverdueShipments() {
        try {
            List<Customers> customers = customersDAO.findByShipments_ShipmentStatus("OVERDUE");
            if (customers.isEmpty()) {
                return new ResponseEntity<>("No customers with overdue shipments found.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An internal server error occurred while fetching customers with overdue shipments.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getCustomersByOrderQuantity(Integer min, Integer max) {
        if (min < 0 || max < 0 || min > max) {
            return new ResponseEntity<>("Invalid request. Please provide valid minimum and maximum quantities for orders.", HttpStatus.BAD_REQUEST);
        }
        List<Customers> customers = customersDAO.findByOrders_OrderItems_QuantityBetween(min, max);
        if (customers.isEmpty()) {
            return new ResponseEntity<>("No customers found with order quantities between " + min + " and " + max, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}
