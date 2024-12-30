package CaseStudy.OrderInventory.model;
 
import jakarta.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
 
 
 
@Entity
public class OrderItems {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "line_item_id")
    private int lineItemId;
 
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;
 
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;
 
    @Column(name = "unit_price", nullable = false)
    private double unitPrice;
 
    @Column(name = "quantity", nullable = false)
    private int quantity;
 
    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipments shipment;
 
    public OrderItems() {
        super();
    }
    public int getLineItemId() {
        return lineItemId;
    }
 
    public void setLineItemId(int lineItemId) {
        this.lineItemId = lineItemId;
    }
 
    public Orders getOrder() {
        return order;
    }
 
    public void setOrder(Orders order) {
        this.order = order;
    }
 
    public Products getProduct() {
        return product;
    }
 
    public void setProduct(Products product) {
        this.product = product;
    }
 
    public double getUnitPrice() {
        return unitPrice;
    }
 
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
 
    public int getQuantity() {
        return quantity;
    }
 
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
 
    public Shipments getShipment() {
        return shipment;
    }
 
    public void setShipment(Shipments shipment) {
        this.shipment = shipment;
    }
}