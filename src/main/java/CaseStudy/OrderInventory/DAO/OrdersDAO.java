package CaseStudy.OrderInventory.DAO;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import CaseStudy.OrderInventory.model.*;

@Repository
public interface OrdersDAO extends JpaRepository<Orders, Integer> {
    List<Orders> findByStoreId_StoreId(int storeId); 
    List<Orders> findByOrderId(int orderId);  
    List<Orders> findByCustomerId_CustomerId(int customerId);  
    List<Orders> findByOrderStatus(String orderStatus); 
    List<Orders> findByOrderTmsBetween(LocalDateTime startDate, LocalDateTime endDate);  
    List<Orders> findByCustomerId_EmailAddress(String email);  
    List<Orders> findByStoreId_StoreName(String storeName);  
    //List<OrderItems> findByOrderId_Order(int orderId);

    

}