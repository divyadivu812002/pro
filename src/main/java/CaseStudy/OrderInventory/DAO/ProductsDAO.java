package CaseStudy.OrderInventory.DAO;


import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;


import CaseStudy.OrderInventory.model.Products;



import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsDAO extends JpaRepository<Products, Integer> {
	
	List<Products> findByProductName(String productName);
	List<Products> findByColour(String colour);
	List<Products> findByBrand(String brand);
	List<Products> findByUnitPriceBetween(double minPrice, double maxPrice);
	List<Products> findByBrand(String brand, Sort sort);
}