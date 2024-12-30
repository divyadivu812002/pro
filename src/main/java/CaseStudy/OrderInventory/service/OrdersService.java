package CaseStudy.OrderInventory.service;

import CaseStudy.OrderInventory.DAO.CustomersDAO;
import CaseStudy.OrderInventory.DAO.OrdersDAO;
import CaseStudy.OrderInventory.DAO.StoresDAO;
import CaseStudy.OrderInventory.model.Customers;
import CaseStudy.OrderInventory.model.OrderItems;
import CaseStudy.OrderInventory.model.Orders;
import CaseStudy.OrderInventory.model.Stores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdersService {

    @Autowired
    private OrdersDAO ordersDAO;
    
    @Autowired
    private CustomersDAO customersDAO;
    
    
    @Autowired
    private StoresDAO storesDAO;

    public List<Orders> getAllOrders() {
        return ordersDAO.findAll();
    }

    public List<Orders> getOrdersByStoreId(int storeId) {
        return ordersDAO.findByStoreId_StoreId(storeId); 
    }

    public List<Orders> getOrdersByCustomerId(int customerId) {
        return ordersDAO.findByCustomerId_CustomerId(customerId);  
    }

    public List<Orders> getOrdersByOrderStatus(String orderStatus) {
        return ordersDAO.findByOrderStatus(orderStatus);
    }

    public long getOrderCountByStatus(String orderStatus) {
        return ordersDAO.findByOrderStatus(orderStatus).size();
    }

    public List<Orders> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return ordersDAO.findByOrderTmsBetween(startDate, endDate);
    }

    public String cancelOrder(int orderId) {
        return ordersDAO.findById(orderId).map(order -> {
            order.setOrderStatus("Canceled");
            ordersDAO.save(order);
            return "Order canceled successfully.";
        }).orElse("Order not found.");
    }
    
    public void createOrUpdateOrder(Orders order) {
    	Customers customer = customersDAO.findById(order.getCustomerId().getCustomerId()).get();
         order.setCustomerId(customer);
         
        Stores stores = storesDAO.findById(order.getStoreId().getStoreId()).get();
        order.setStoreId(stores);
         
         ordersDAO.save(order);
         
}
    public boolean deleteOrder(int orderId) {
        if (ordersDAO.existsById(orderId)) {
            ordersDAO.deleteById(orderId);
            return true;
        }
        return false;
    }

    public List<Orders> getOrdersByCustomerEmail(String email) {
        return ordersDAO.findByCustomerId_EmailAddress(email);
    }

    public List<Orders> getOrdersByStoreName(String storeName) {
        return ordersDAO.findByStoreId_StoreName(storeName); 
    }

    public List<Orders> getOrderById(int id) {
        return ordersDAO.findByOrderId(id);
    }
}