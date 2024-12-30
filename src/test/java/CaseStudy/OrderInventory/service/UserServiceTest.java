package CaseStudy.OrderInventory.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import CaseStudy.OrderInventory.DAO.UserDAO;
import CaseStudy.OrderInventory.model.UserEntity;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserDAO userDAO;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userEntity = new UserEntity();
        userEntity.setUser_id(1L);
        userEntity.setUsername("John Doe");
    }

    @Test
    void testSaveUser() {
        userService.saveUser(userEntity);
        verify(userDAO, times(1)).save(userEntity);
    }

    @Test
    void testFindByIdExists() {
        when(userDAO.findById(1L)).thenReturn(Optional.of(userEntity));
        boolean result = userService.findById(1L);
        assertTrue(result);
        verify(userDAO, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotExists() {
        when(userDAO.findById(1L)).thenReturn(Optional.empty());
        boolean result = userService.findById(1L);
        assertFalse(result);
        verify(userDAO, times(1)).findById(1L);
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(1L);
        verify(userDAO, times(1)).deleteById(1L);
    }

    @Test
    void testGetUserByIdExists() {
        when(userDAO.findById(1L)).thenReturn(Optional.of(userEntity));
        UserEntity result = userService.getUserById(1L);
        assertNotNull(result);
        assertEquals(userEntity.getUser_id(), result.getUser_id());
        assertEquals(userEntity.getUsername(), result.getUsername());
        verify(userDAO, times(1)).findById(1L);
    }

    @Test
    void testGetUserByIdNotExists() {
        when(userDAO.findById(1L)).thenReturn(Optional.empty());
        assertThrows(java.util.NoSuchElementException.class, () -> userService.getUserById(1L));
        verify(userDAO, times(1)).findById(1L);
    }
}
