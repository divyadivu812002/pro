package CaseStudy.OrderInventory.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import CaseStudy.OrderInventory.model.*;
import CaseStudy.OrderInventory.service.*;

import java.util.ArrayList;
import java.util.List;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    private UserEntity user;
    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserEntity();
        user.setUser_id(1L);
        user.setPassword("plainPassword");
        user.setRoles(new ArrayList<>());

        role = new Role();
        role.setRole_name("ROLE_USER");
    }

    @Test
    void testRegisterUser() {
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        doNothing().when(userService).saveUser(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        ResponseEntity<?> response = userController.registerUser(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    void testUpdateUser() {
        when(userService.getUserById(1L)).thenReturn(user);
        doNothing().when(roleService).saveRole(any(Role.class));
 
        ResponseEntity<?> response = userController.updateUser(1L);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService, times(1)).getUserById(1L);
        verify(roleService, times(1)).saveRole(any(Role.class));
    }

    @Test
    void testRegisterStoreManager() {
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        doNothing().when(userService).saveUser(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        ResponseEntity<?> response = userController.registerStoreManager(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    void testRegisterInventoryManager() {
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        doNothing().when(userService).saveUser(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        ResponseEntity<?> response = userController.registerInventoryManager(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    void testRegisterAdmin() {
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        doNothing().when(userService).saveUser(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        ResponseEntity<?> response = userController.registerAdmin(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService, times(1)).saveUser(user);
    }
}
