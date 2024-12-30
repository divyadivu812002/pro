package CaseStudy.OrderInventory.DAO;

import CaseStudy.OrderInventory.model.Inventory;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryDAO extends JpaRepository<Inventory, Integer> {

    List<Inventory> findByStoreId_StoreId(int storeId); 
    List<Inventory> findByProductId_ProductId(int productId); 
    List<Inventory> findByStoreId_StoreIdAndProductId_ProductId(int storeId, int productId);
    
}
