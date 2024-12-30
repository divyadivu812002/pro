package CaseStudy.OrderInventory.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Customers {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int customerId;
	
	@Column(name="email_address",nullable=false)
	private String emailAddress;
	
	@Column(name="full_name",nullable=false)
	private String fullName;
	
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Shipments> shipments;
    
    @OneToMany(mappedBy = "customerId", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Orders> orders;
    
	public Customers() {
		super();
	}
	
	public Customers(int customerId, String emailAddress, String fullName) {
		super();
		this.customerId = customerId;
		this.emailAddress = emailAddress;
		this.fullName = fullName;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public List<Shipments> getShipments() {
		return shipments;
	}
	public void setShipments(List<Shipments> shipments) {
		this.shipments = shipments;
	}
	public List<Orders> getOrders() {
		return orders;
	}
	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}
	
	
	
	

}
