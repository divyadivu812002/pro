package CaseStudy.OrderInventory.service;

import CaseStudy.OrderInventory.model.*;
import CaseStudy.OrderInventory.service.*;
import CaseStudy.OrderInventory.controller.*;
import CaseStudy.OrderInventory.DAO.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDAO roleRepository;

    // Find role by name (useful for assigning roles to users)
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    // Save a new role
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    // Check if a role already exists by name
    public boolean existsByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName) != null;
    }
}