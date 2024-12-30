package CaseStudy.OrderInventory.DAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import CaseStudy.OrderInventory.model.*;
import CaseStudy.OrderInventory.service.*;
import CaseStudy.OrderInventory.controller.*;
import CaseStudy.OrderInventory.DAO.*;


public interface RoleDAO extends JpaRepository<Role, Long> {
	
	@Query("SELECT r FROM Role r WHERE r.role_name = ?1")
    Role findByRoleName(String roleName);
	
}