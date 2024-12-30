package CaseStudy.OrderInventory.model;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;
    
    @Column(unique=true,nullable=false)
    private String username;
    @Column(nullable = false)
    private String password;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    private List<Role> roles;
    
	public UserEntity(String username, String password,List<Role> role) {
		super();
		this.username = username;
		this.password = password;
		this.roles = role;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
    
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
    
    
	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}
    
	public void setUsername(String username) {
		this.username = username;
	}
    
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
   public UserEntity() {}
   
}