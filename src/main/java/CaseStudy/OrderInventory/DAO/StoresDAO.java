package CaseStudy.OrderInventory.DAO;
 
 
import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import CaseStudy.OrderInventory.model.Stores;
 
@Repository
public interface StoresDAO extends JpaRepository<Stores, Integer> {
	List<Stores> findById(int storeId);
    // Additional query methods can be defined here
}
 