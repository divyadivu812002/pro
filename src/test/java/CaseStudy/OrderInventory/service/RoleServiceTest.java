package CaseStudy.OrderInventory.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import CaseStudy.OrderInventory.DAO.RoleDAO;
import CaseStudy.OrderInventory.model.Role;

class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleDAO roleRepository;

    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        role = new Role("ADMIN");
        role.setRole_id(1L);
    }

    @Test
    void testFindByRoleNameExists() {
        when(roleRepository.findByRoleName("ADMIN")).thenReturn(role);
        Role result = roleService.findByRoleName("ADMIN");
        assertNotNull(result);
        assertEquals("ADMIN", result.getRole_name());
        verify(roleRepository, times(1)).findByRoleName("ADMIN");
    }

    @Test
    void testFindByRoleNameNotExists() {
        when(roleRepository.findByRoleName("USER")).thenReturn(null);
        Role result = roleService.findByRoleName("USER");
        assertNull(result);
        verify(roleRepository, times(1)).findByRoleName("USER");
    }

    @Test
    void testSaveRole() {
        roleService.saveRole(role);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void testExistsByRoleNameExists() {
        when(roleRepository.findByRoleName("ADMIN")).thenReturn(role);
        boolean result = roleService.existsByRoleName("ADMIN");
        assertTrue(result);
        verify(roleRepository, times(1)).findByRoleName("ADMIN");
    }

    @Test
    void testExistsByRoleNameNotExists() {
        when(roleRepository.findByRoleName("USER")).thenReturn(null);
        boolean result = roleService.existsByRoleName("USER");
        assertFalse(result);
        verify(roleRepository, times(1)).findByRoleName("USER");
    }
}
