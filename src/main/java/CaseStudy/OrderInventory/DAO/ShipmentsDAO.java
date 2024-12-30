package CaseStudy.OrderInventory.DAO;
 
 
import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import CaseStudy.OrderInventory.model.Shipments;
 
@Repository
public interface ShipmentsDAO extends JpaRepository<Shipments, Integer> {
 
    // Example custom query method to find shipments by status
    List<Shipments> findByShipmentStatus(String shipmentStatus);
    List<Shipments> findByStoreId_StoreId(int storeId);
    List<Shipments> findByCustomer_CustomerId(int customerId );
    
}