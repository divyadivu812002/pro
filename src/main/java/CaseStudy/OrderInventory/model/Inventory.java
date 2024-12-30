package CaseStudy.OrderInventory.model;

import jakarta.persistence.*;

@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int inventoryId;
    
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Stores storeId; 

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products productId; 

    @Column(name="productInventory")
    private int productInventory;

	public int getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Stores getStoreId() {
		return storeId;
	}

	public void setStoreId(Stores storeId) {
		this.storeId = storeId;
	}

	public Products getProductId() {
		return productId;
	}

	public void setProductId(Products productId) {
		this.productId = productId;
	}

	public int getProductInventory() {
		return productInventory;
	}

	public void setProductInventory(int productInventory) {
		this.productInventory = productInventory;
	}
}

