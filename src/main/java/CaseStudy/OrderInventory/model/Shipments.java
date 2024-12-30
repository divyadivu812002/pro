package CaseStudy.OrderInventory.model;
 
 
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import CaseStudy.OrderInventory.model.*;
@Entity
@Table(name = "shipments")
public class Shipments {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shipment_id")
    private Integer shipmentId;
    
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Stores storeId;
   
    
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customers customer;
 
    @Column(name = "delivery_address", length = 512)
    private String deliveryAddress;
 
    @Column(name = "shipment_status", length = 100)
    private String shipmentStatus;
    
	@OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<OrderItems> orderItems;
 
    public Shipments() {
    	
    }

	public Integer getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Integer shipmentId) {
		this.shipmentId = shipmentId;
	}

	public Stores getStoreId() {
		return storeId;
	}

	public void setStoreId(Stores storeId) {
		this.storeId = storeId;
	}

	public List<OrderItems> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItems> orderItems) {
		this.orderItems = orderItems;
	}

	public Customers getCustomer() {
		return customer;
	}

	public void setCustomer(Customers customer) {
		this.customer = customer;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getShipmentStatus() {
		return shipmentStatus;
	}

	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}
    
 	
	}
 
   
    
    
