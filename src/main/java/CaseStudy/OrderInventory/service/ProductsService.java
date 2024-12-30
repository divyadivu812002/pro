
package CaseStudy.OrderInventory.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import CaseStudy.OrderInventory.DAO.*;
import CaseStudy.OrderInventory.model.Products;

@Service
public class ProductsService {
	
    @Autowired
    private ProductsDAO productsDao;

    public List<Products> getAllProducts() {
        return productsDao.findAll();
    }

    public Products addOrUpdateProduct(Products product) {
        return productsDao.save(product);
    }

    public Products getProductById(int productId) {
        return productsDao.findById(productId).orElse(null);
    }

    public boolean deleteProductById(int productId) {
        if (productsDao.existsById(productId)) {
            productsDao.deleteById(productId);
            return true;
        }
        return false;
    }

    public List<Products> getProductsByBrand(String brand) {
        return productsDao.findByBrand(brand);
    }

    public List<Products> getProductsByName(String productName) {
        return productsDao.findByProductName(productName);
    }

    public List<Products> getProductsByColour(String colour) {
        return productsDao.findByColour(colour);
    }

    public List<Products> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productsDao.findByUnitPriceBetween(minPrice, maxPrice);
    }

    public List<Products> getProductsSortedByBrand(String brand) {
        Sort sort = Sort.by(Sort.Order.asc("id"));  
        return productsDao.findByBrand(brand, sort);
    }

    public long getProductCountByBrand(String brand) {
        return productsDao.findByBrand(brand).size();
    }
}
