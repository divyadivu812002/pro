package CaseStudy.OrderInventory.controller;


import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import CaseStudy.OrderInventory.model.Products;
import CaseStudy.OrderInventory.service.ProductsService;
@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> products = productsService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable int id) {
        Products product = productsService.getProductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addproduct")
    public ResponseEntity<Products> addOrUpdateProduct(@RequestBody Products products) {
        Products savedProduct = productsService.addOrUpdateProduct(products);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable int id) {
        boolean isDeleted = productsService.deleteProductById(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Products>> getProductsByBrand(@PathVariable String brand) {
        List<Products> products = productsService.getProductsByBrand(brand);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Products>> getProductsByName(@PathVariable String name) {
        List<Products> products = productsService.getProductsByName(name);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/colour/{colour}")
    public ResponseEntity<List<Products>> getProductsByColour(@PathVariable String colour) {
        List<Products> products = productsService.getProductsByColour(colour);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Products>> getProductsByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        List<Products> products = productsService.getProductsByPriceRange(minPrice, maxPrice);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/filterByPrice/{minPrice}/{maxPrice}")
    public ResponseEntity<Object> findProductsByPriceRange(@PathVariable("minPrice") double minPrice,
                                                            @PathVariable("maxPrice") double maxPrice) {
        List<Products> products = productsService.getProductsByPriceRange(minPrice, maxPrice);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
 
    @CrossOrigin
    @GetMapping("/sort/brand")
    public ResponseEntity<List<Products>> findProductsSortedByBrand() {
        List<Products> sortedProducts = productsService.getAllProducts();
        Comparator<Products> brandComparator = Comparator.comparing(Products::getBrand);
        sortedProducts.sort(brandComparator);
        return new ResponseEntity<>(sortedProducts, HttpStatus.OK);
    }
}
 
   