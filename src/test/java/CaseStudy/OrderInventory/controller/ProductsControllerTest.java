package CaseStudy.OrderInventory.controller;

import CaseStudy.OrderInventory.model.Products;
import CaseStudy.OrderInventory.service.ProductsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductsControllerTest {

    @InjectMocks
    private ProductsController productsController;

    @Mock
    private ProductsService productsService;

    private Products mockProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock Product object
        mockProduct = new Products();
        mockProduct.setProductId(1);
        mockProduct.setProductName("Product A");
        mockProduct.setBrand("Brand X");
        mockProduct.setColour("Red");
        mockProduct.setUnitPrice(99.99);
        mockProduct.setSize("M");
        mockProduct.setRating(4);

        // You can add Inventory or OrderItems here if necessary
    }

    @Test
    void testGetAllProducts() {
        List<Products> mockProducts = Collections.singletonList(mockProduct);
        when(productsService.getAllProducts()).thenReturn(mockProducts);

        ResponseEntity<List<Products>> response = productsController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProducts, response.getBody());
        verify(productsService, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById_Success() {
        int productId = 1;
        when(productsService.getProductById(productId)).thenReturn(mockProduct);

        ResponseEntity<Products> response = productsController.getProductById(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProduct, response.getBody());
        verify(productsService, times(1)).getProductById(productId);
    }

    @Test
    void testGetProductById_NotFound() {
        int productId = 1;
        when(productsService.getProductById(productId)).thenReturn(null);

        ResponseEntity<Products> response = productsController.getProductById(productId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productsService, times(1)).getProductById(productId);
    }

    @Test
    void testAddOrUpdateProduct() {
        when(productsService.addOrUpdateProduct(mockProduct)).thenReturn(mockProduct);

        ResponseEntity<Products> response = productsController.addOrUpdateProduct(mockProduct);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockProduct, response.getBody());
        verify(productsService, times(1)).addOrUpdateProduct(mockProduct);
    }

    @Test
    void testDeleteProductById_Success() {
        int productId = 1;
        when(productsService.deleteProductById(productId)).thenReturn(true);

        ResponseEntity<Void> response = productsController.deleteProductById(productId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productsService, times(1)).deleteProductById(productId);
    }

    @Test
    void testDeleteProductById_NotFound() {
        int productId = 1;
        when(productsService.deleteProductById(productId)).thenReturn(false);

        ResponseEntity<Void> response = productsController.deleteProductById(productId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productsService, times(1)).deleteProductById(productId);
    }

    @Test
    void testGetProductsByBrand() {
        String brand = "Brand X";
        List<Products> mockProducts = Collections.singletonList(mockProduct);
        when(productsService.getProductsByBrand(brand)).thenReturn(mockProducts);

        ResponseEntity<List<Products>> response = productsController.getProductsByBrand(brand);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProducts, response.getBody());
        verify(productsService, times(1)).getProductsByBrand(brand);
    }

    @Test
    void testGetProductsByName() {
        String name = "Product A";
        List<Products> mockProducts = Collections.singletonList(mockProduct);
        when(productsService.getProductsByName(name)).thenReturn(mockProducts);

        ResponseEntity<List<Products>> response = productsController.getProductsByName(name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProducts, response.getBody());
        verify(productsService, times(1)).getProductsByName(name);
    }

    @Test
    void testGetProductsByColour() {
        String colour = "Red";
        List<Products> mockProducts = Collections.singletonList(mockProduct);
        when(productsService.getProductsByColour(colour)).thenReturn(mockProducts);

        ResponseEntity<List<Products>> response = productsController.getProductsByColour(colour);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProducts, response.getBody());
        verify(productsService, times(1)).getProductsByColour(colour);
    }

    @Test
    void testGetProductsByPriceRange() {
        double minPrice = 50.0;
        double maxPrice = 150.0;
        List<Products> mockProducts = Collections.singletonList(mockProduct);
        when(productsService.getProductsByPriceRange(minPrice, maxPrice)).thenReturn(mockProducts);

        ResponseEntity<List<Products>> response = productsController.getProductsByPriceRange(minPrice, maxPrice);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProducts, response.getBody());
        verify(productsService, times(1)).getProductsByPriceRange(minPrice, maxPrice);
    }

    @Test
    void testFindProductsSortedByBrand() {
        List<Products> mockProducts = Collections.singletonList(mockProduct);
        when(productsService.getAllProducts()).thenReturn(mockProducts);

        ResponseEntity<List<Products>> response = productsController.findProductsSortedByBrand();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProducts, response.getBody());
        verify(productsService, times(1)).getAllProducts();
    }
}
