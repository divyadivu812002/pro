package CaseStudy.OrderInventory.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import CaseStudy.OrderInventory.model.Customers;



@Repository
public interface CustomersDAO extends JpaRepository<Customers, Integer> {
    List<Customers> findByEmailAddress(String emailAddress);
    List<Customers> findByFullName(String fullName);
    List<Customers> findByShipments_ShipmentStatus(String shipmentStatus);
    List<Customers> findByOrders_OrderStatus(String orderStatus);
    List<Customers> findByOrders_OrderItems_QuantityBetween(int min, int max);
    int countDistinctByShipments_ShipmentStatus(String shipmentStatus);

}

	
      

