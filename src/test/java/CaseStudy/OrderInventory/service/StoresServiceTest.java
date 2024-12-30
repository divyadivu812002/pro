package CaseStudy.OrderInventory.service;

import CaseStudy.OrderInventory.DAO.StoresDAO;
import CaseStudy.OrderInventory.model.Stores;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StoresServiceTest {

    @InjectMocks
    private StoresService storesService;

    @Mock
    private StoresDAO storesDAO;

    private Stores sampleStore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize sample store
        sampleStore = new Stores();
        sampleStore.setStoreId(1);
        sampleStore.setStoreName("Test Store");
        sampleStore.setWebAddress("http://teststore.com");
        sampleStore.setPhysicalAddress("123 Test Address");
        sampleStore.setLatitude(12.345);
        sampleStore.setLongitude(67.890);
        sampleStore.setLogo(new byte[]{1, 2, 3}); // mock logo byte array
        sampleStore.setLogoMimeType("image/png");
        sampleStore.setLogoFilename("logo.png");
        sampleStore.setLogoCharset("UTF-8");
        sampleStore.setLogoLastUpdated(new Date());
    }

    @Test
    void testGetAllStores() {
        when(storesDAO.findAll()).thenReturn(Arrays.asList(sampleStore));

        List<Stores> stores = storesService.getAllStores();

        assertEquals(1, stores.size());
        assertEquals("Test Store", stores.get(0).getStoreName());
        verify(storesDAO, times(1)).findAll();
    }

    @Test
    void testGetStoreById() {
        when(storesDAO.findById(1)).thenReturn(Arrays.asList(sampleStore));

        Stores store = storesService.getStoreById(1);

        assertNotNull(store);
        assertEquals("Test Store", store.getStoreName());
        verify(storesDAO, times(1)).findById(1);
    }

    @Test
    void testGetStoreByIdNotFound() {
        when(storesDAO.findById(1)).thenReturn(Arrays.asList());

        assertThrows(EntityNotFoundException.class, () -> {
            storesService.getStoreById(1);
        });

        verify(storesDAO, times(1)).findById(1);
    }

    @Test
    void testAddStore() {
        when(storesDAO.save(sampleStore)).thenReturn(sampleStore);

        Stores store = storesService.addStore(sampleStore);

        assertNotNull(store);
        assertEquals("Test Store", store.getStoreName());
        verify(storesDAO, times(1)).save(sampleStore);
    }

//    @Test
//    void testUpdateStore() {
//        when(storesDAO.findById(1)).thenReturn(Arrays.asList(sampleStore));
//
//        Stores updatedStore = new Stores();
//        updatedStore.setStoreId(1);
//        updatedStore.setStoreName("Updated Store");
//        updatedStore.setWebAddress("http://updatedstore.com");
//        updatedStore.setPhysicalAddress("456 Updated Address");
//
//        Stores updatedStoreResult = storesService.updateStore(1, updatedStore);
//
//        assertNotNull(updatedStoreResult);
//        assertEquals("Updated Store", updatedStoreResult.getStoreName());
//        assertEquals("http://updatedstore.com", updatedStoreResult.getWebAddress());
//        assertEquals("456 Updated Address", updatedStoreResult.getPhysicalAddress());
//        verify(storesDAO, times(1)).findById(1);
//        verify(storesDAO, times(1)).save(any(Stores.class));
//    }

    @Test
    void testUpdateStoreNotFound() {
        when(storesDAO.findById(1)).thenReturn(Arrays.asList());

        Stores updatedStore = new Stores();
        updatedStore.setStoreId(1);
        updatedStore.setStoreName("Updated Store");

        assertThrows(EntityNotFoundException.class, () -> {
            storesService.updateStore(1, updatedStore);
        });

        verify(storesDAO, times(1)).findById(1);
    }

    @Test
    void testDeleteStore() {
        when(storesDAO.findById(1)).thenReturn(Arrays.asList(sampleStore));

        storesService.deleteStore(1);

        verify(storesDAO, times(1)).findById(1);
        verify(storesDAO, times(1)).delete(sampleStore);
    }

    @Test
    void testDeleteStoreNotFound() {
        when(storesDAO.findById(1)).thenReturn(Arrays.asList());

        assertThrows(EntityNotFoundException.class, () -> {
            storesService.deleteStore(1);
        });

        verify(storesDAO, times(1)).findById(1);
    }

//    @Test
//    void testFindStore() {
//        when(storesDAO.findById(1)).thenReturn(Arrays.asList(sampleStore));
//        boolean found = storesService.findStore(sampleStore);
//        assertTrue(found);
//        verify(storesDAO, times(1)).findById(1);
//    }

//    @Test
//    void testFindStoreNotFound() {
//        when(storesDAO.findById(1)).thenReturn(Arrays.asList());
//        boolean found = storesService.findStore(sampleStore);
//        assertFalse(found);
//        verify(storesDAO, times(1)).findById(1);
//    }
}
