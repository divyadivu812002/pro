package CaseStudy.OrderInventory.controller;

import CaseStudy.OrderInventory.model.AuthenticateUser;

import CaseStudy.OrderInventory.model.Role;
import CaseStudy.OrderInventory.model.UserEntity;
import CaseStudy.OrderInventory.DAO.UserDAO;
import CaseStudy.OrderInventory.Filter.JwtResponse;
import CaseStudy.OrderInventory.Filter.JwtToken;
import CaseStudy.OrderInventory.exception.Response;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private DaoAuthenticationProvider provider;

    @Mock
    private UserDAO userRepository;

    @Mock
    private JwtToken jwtToken;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateSuccess() {
        // Arrange
        AuthenticateUser user = new AuthenticateUser();
        user.setUserName("testuser");
        user.setPassword("testpass");
        user.setRole("USER");

        Role role = new Role();
        role.setRole_name("USER");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(new UserEntity("testuser", "testpass", Collections.singletonList(role))));
        when(provider.authenticate(any(Authentication.class))).thenAnswer(invocation -> {
            Authentication auth = invocation.getArgument(0);
            if ("testuser".equals(auth.getName()) && "testpass".equals(auth.getCredentials())) {
                auth.setAuthenticated(true);
            }
            return auth;
        });
        when(jwtToken.generateToken(anyString(), anyString(), anyString())).thenReturn("dummyToken");

        // Act
        ResponseEntity<?> response = authController.authenticate(user);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("dummyToken", ((JwtResponse) response.getBody()).getToken());
    }

    @Test
    public void testAuthenticateInvalidCredentials() {
        // Arrange
        AuthenticateUser user = new AuthenticateUser();
        user.setUserName("invaliduser");
        user.setPassword("invalidpass");
        user.setRole("USER");

        when(provider.authenticate(any(Authentication.class))).thenThrow(new AuthenticationException("Invalid credentials") {});

        // Act
        ResponseEntity<?> response = authController.authenticate(user);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("UNAUTHORIZED", ((Response) response.getBody()).getMessage());
    }

    @Test
    public void testAuthenticateInvalidRole() {
        // Arrange
        AuthenticateUser user = new AuthenticateUser();
        user.setUserName("testuser");
        user.setPassword("testpass");
        user.setRole("INVALID_ROLE");

        Role role = new Role();
        role.setRole_name("USER");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(new UserEntity("testuser", "testpass", Collections.singletonList(role))));
        when(provider.authenticate(any(Authentication.class))).thenAnswer(invocation -> {
            Authentication auth = invocation.getArgument(0);
            if ("testuser".equals(auth.getName()) && "testpass".equals(auth.getCredentials())) {
                auth.setAuthenticated(true);
            }
            return auth;
        });

        // Act
        ResponseEntity<?> response = authController.authenticate(user);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}