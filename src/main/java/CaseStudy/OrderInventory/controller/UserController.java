package CaseStudy.OrderInventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import CaseStudy.OrderInventory.model.*;
import CaseStudy.OrderInventory.service.*;
import CaseStudy.OrderInventory.controller.*;
import CaseStudy.OrderInventory.DAO.*;
import CaseStudy.OrderInventory.exception.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@RequestBody UserEntity user) {

        // Encode the password before saving the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Create and assign roles
        List<Role> assignedRoles = new ArrayList<>();
        Role userRole = new Role();
        userRole.setRole_name("ROLE_USER"); // Default role for new users
        assignedRoles.add(userRole);
        userRole.setUser(user);

        user.setRoles(assignedRoles);

        try {
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Response("REGISTERSUCCESS", "User created successfully"));
        } catch (Exception e) {
            // Log the exception for better debugging
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("REGISTERFAIL", "Error creating user"));
        }
    }

    @PutMapping("/manager/register/{user_id}")
    public ResponseEntity<?> updateUser(@PathVariable Long user_id) {
        try {
            // Fetch the existing user
            UserEntity existingUser = userService.getUserById(user_id);

            // Check if the user already has the manager role before adding it
            boolean hasManagerRole = existingUser.getRoles().stream()
                                                 .anyMatch(role -> role.getRole_name().equals("ROLE_MANAGER"));

            if (!hasManagerRole) {
                // Assign the manager role if not already assigned
                Role managerRole = new Role("ROLE_MANAGER");
                existingUser.getRoles().add(managerRole);
                managerRole.setUser(existingUser);

                // Save the new role and user
                roleService.saveRole(managerRole);
                userService.saveUser(existingUser);
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new Response("REGISTERSUCCESS", "User updated successfully with selected roles"));
        } catch (Exception e) {
            // Log the exception for better troubleshooting
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response("REGISTERFAIL", "Error creating user"));
        }
        
    }
    
    @PostMapping("/store/manager/register")
    public ResponseEntity<?> registerStoreManager(@RequestBody UserEntity user) {
        // Encode the password before saving the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Create and assign roles
        List<Role> assignedRoles = new ArrayList<>();
        Role storeManagerRole = new Role();
        storeManagerRole.setRole_name("ROLE_STOREMANAGER"); // Role for Store Manager
        assignedRoles.add(storeManagerRole);
        storeManagerRole.setUser(user);

        user.setRoles(assignedRoles);

        try {
            // Save the user with the store manager role
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Response("REGISTERSUCCESS", "Store Manager created successfully"));
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("REGISTERFAIL", "Error creating Store Manager"));
        }
    }
    
    @PostMapping("/inventory/manager/register")
    public ResponseEntity<?> registerInventoryManager(@RequestBody UserEntity user) {
        // Encode the password before saving the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Create and assign roles
        List<Role> assignedRoles = new ArrayList<>();
        Role inventoryManagerRole = new Role();
        inventoryManagerRole.setRole_name("ROLE_INVENTORYMANAGER"); // Role for Inventory Manager
        assignedRoles.add(inventoryManagerRole);
        inventoryManagerRole.setUser(user);

        user.setRoles(assignedRoles);

        try {
            // Save the user with the inventory manager role
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Response("REGISTERSUCCESS", "Inventory Manager created successfully"));
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("REGISTERFAIL", "Error creating Inventory Manager"));
        }
    }
    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdmin(@RequestBody UserEntity user) {
        // Encode the password before saving the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Create and assign roles
        List<Role> assignedRoles = new ArrayList<>();
        Role adminRole = new Role();
        adminRole.setRole_name("ROLE_ADMIN"); // Role for Admin
        assignedRoles.add(adminRole);
        adminRole.setUser(user);

        user.setRoles(assignedRoles);

        try {
            // Save the user with the admin role
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Response("REGISTERSUCCESS", "Admin created successfully"));
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("REGISTERFAIL", "Error creating Admin"));
        }
    }

}