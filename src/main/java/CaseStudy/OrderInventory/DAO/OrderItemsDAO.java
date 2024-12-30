package CaseStudy.OrderInventory.DAO;
 

 
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import CaseStudy.OrderInventory.model.*;
 
@Repository
public interface OrderItemsDAO extends JpaRepository<OrderItems, Integer> {

	
    // Add custom query methods if required
}
 