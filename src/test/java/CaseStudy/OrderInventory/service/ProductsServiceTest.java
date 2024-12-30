package CaseStudy.OrderInventory.service;

import CaseStudy.OrderInventory.DAO.ProductsDAO;
import CaseStudy.OrderInventory.model.Products;
import org.springframework.data.domain.Sort; // Correct import

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductsServiceTest {

    @InjectMocks
    private ProductsService productsService;

    @Mock
    private ProductsDAO productsDAO;

    private Products sampleProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize sample product
        sampleProduct = new Products();
        sampleProduct.setProductId(1);
        sampleProduct.setProductName("Test Product");
        sampleProduct.setBrand("Test Brand");
        sampleProduct.setColour("Red");
        sampleProduct.setUnitPrice(100.00);
    }

    @Test
    void testGetAllProducts() {
        when(productsDAO.findAll()).thenReturn(Arrays.asList(sampleProduct));
        List<Products> products = productsService.getAllProducts();
        assertEquals(1, products.size());
        verify(productsDAO, times(1)).findAll();
    }

    @Test
    void testAddOrUpdateProduct() {
        when(productsDAO.save(sampleProduct)).thenReturn(sampleProduct);
        Products product = productsService.addOrUpdateProduct(sampleProduct);
        assertNotNull(product);
        assertEquals("Test Product", product.getProductName());
        verify(productsDAO, times(1)).save(sampleProduct);
    }

    @Test
    void testGetProductById() {
        when(productsDAO.findById(1)).thenReturn(Optional.of(sampleProduct));
        Products product = productsService.getProductById(1);
        assertNotNull(product);
        assertEquals("Test Product", product.getProductName());
        verify(productsDAO, times(1)).findById(1);
    }

    @Test
    void testGetProductByIdNotFound() {
        when(productsDAO.findById(1)).thenReturn(Optional.empty());
        Products product = productsService.getProductById(1);
        assertNull(product);
        verify(productsDAO, times(1)).findById(1);
    }

    @Test
    void testDeleteProductById() {
        when(productsDAO.existsById(1)).thenReturn(true);
        boolean result = productsService.deleteProductById(1);
        assertTrue(result);
        verify(productsDAO, times(1)).deleteById(1);
    }

    @Test
    void testDeleteProductByIdNotFound() {
        when(productsDAO.existsById(1)).thenReturn(false);
        boolean result = productsService.deleteProductById(1);
        assertFalse(result);
        verify(productsDAO, times(1)).existsById(1);
    }

    @Test
    void testGetProductsByBrand() {
        when(productsDAO.findByBrand("Test Brand")).thenReturn(Arrays.asList(sampleProduct));
        List<Products> products = productsService.getProductsByBrand("Test Brand");
        assertEquals(1, products.size());
        verify(productsDAO, times(1)).findByBrand("Test Brand");
    }

    @Test
    void testGetProductsByName() {
        when(productsDAO.findByProductName("Test Product")).thenReturn(Arrays.asList(sampleProduct));
        List<Products> products = productsService.getProductsByName("Test Product");
        assertEquals(1, products.size());
        verify(productsDAO, times(1)).findByProductName("Test Product");
    }

    @Test
    void testGetProductsByColour() {
        when(productsDAO.findByColour("Red")).thenReturn(Arrays.asList(sampleProduct));
        List<Products> products = productsService.getProductsByColour("Red");
        assertEquals(1, products.size());
        verify(productsDAO, times(1)).findByColour("Red");
    }

    @Test
    void testGetProductsByPriceRange() {
        when(productsDAO.findByUnitPriceBetween(50, 150)).thenReturn(Arrays.asList(sampleProduct));
        List<Products> products = productsService.getProductsByPriceRange(50, 150);
        assertEquals(1, products.size());
        verify(productsDAO, times(1)).findByUnitPriceBetween(50, 150);
    }

    @Test
    void testGetProductsSortedByBrand() {
        when(productsDAO.findByBrand("Test Brand", Sort.by(Sort.Order.asc("id")))).thenReturn(Arrays.asList(sampleProduct));
        List<Products> products = productsService.getProductsSortedByBrand("Test Brand");
        assertEquals(1, products.size());
        verify(productsDAO, times(1)).findByBrand("Test Brand", Sort.by(Sort.Order.asc("id")));
    }

    @Test
    void testGetProductCountByBrand() {
        when(productsDAO.findByBrand("Test Brand")).thenReturn(Arrays.asList(sampleProduct));
        long count = productsService.getProductCountByBrand("Test Brand");
        assertEquals(1, count);
        verify(productsDAO, times(1)).findByBrand("Test Brand");
    }
}
