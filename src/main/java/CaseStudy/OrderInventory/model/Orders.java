package CaseStudy.OrderInventory.model;
 
import jakarta.persistence.*;
 
import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import Converter.*;
@Entity
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	private int orderId;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
	private List<OrderItems> orderItems;
	
	@Column(name = "order_tms", nullable = false)
	@Convert(converter = LocalDateTimeAttributeConverter.class)
	private LocalDateTime orderTms;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customers customerId;
	
	@Column(name = "order_status", nullable = false)
	private String orderStatus;
	
	@ManyToOne
	@JoinColumn(name = "store_id" , nullable = false)
	private Stores storeId;
	
	
	public Orders(int orderId, List<OrderItems> orderItems, LocalDateTime orderTms, Customers customerId,
			String orderStatus, Stores storeId) {
		super();
		this.orderId = orderId;
		this.orderItems = orderItems;
		this.orderTms = orderTms;
		this.customerId = customerId;
		this.orderStatus = orderStatus;
		this.storeId = storeId;
	}
	public Orders() {
		super();
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public List<OrderItems> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItems> orderItems) {
		this.orderItems = orderItems;
	}
	public LocalDateTime getOrderTms() {
		return orderTms;
	}
	public void setOrderTms(LocalDateTime orderTms) {
		this.orderTms = orderTms;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Customers getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Customers customerId) {
		this.customerId = customerId;
	}
	public Stores getStoreId() {
		return storeId;
	}
	public void setStoreId(Stores storeId) {
		this.storeId = storeId;
	}
}	