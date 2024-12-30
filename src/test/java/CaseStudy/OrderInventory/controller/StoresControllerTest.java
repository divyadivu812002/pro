package CaseStudy.OrderInventory.controller;

import CaseStudy.OrderInventory.model.Stores;
import CaseStudy.OrderInventory.service.StoresService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class StoresControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StoresService storesService;

    @InjectMocks
    private StoresController storesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(storesController).build();
    }

    @Test
    void getAllStores() throws Exception {
        Stores store1 = new Stores(1, "Store A", "www.storea.com", "123 Main St", 10.0, 20.0, null, "image/png", "logo1.png", "UTF-8", new Date());
        Stores store2 = new Stores(2, "Store B", "www.storeb.com", "456 Elm St", 15.0, 25.0, null, "image/jpeg", "logo2.jpg", "UTF-8", new Date());

        when(storesService.getAllStores()).thenReturn(List.of(store1, store2));

        mockMvc.perform(get("/api/stores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].storeId").value(1))
                .andExpect(jsonPath("$[1].storeId").value(2));
    }

    @Test
    void getStoreById() throws Exception {
        Stores store = new Stores(1, "Store A", "www.storea.com", "123 Main St", 10.0, 20.0, null, "image/png", "logo1.png", "UTF-8", new Date());

        when(storesService.getStoreById(eq(1))).thenReturn(store); // Use eq(1) for id

        mockMvc.perform(get("/api/stores/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeId").value(1))
                .andExpect(jsonPath("$.storeName").value("Store A"));
    }

    @Test
    void addStore() throws Exception {
        Stores newStore = new Stores(1, "Store A", "www.storea.com", "123 Main St", 10.0, 20.0, null, "image/png", "logo1.png", "UTF-8", new Date());

        when(storesService.addStore(any(Stores.class))).thenReturn(newStore); // Return the newStore directly

        mockMvc.perform(post("/api/stores")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "storeName": "Store A",
                          "webAddress": "www.storea.com",
                          "physicalAddress": "123 Main St",
                          "latitude": 10.0,
                          "longitude": 20.0,
                          "logoMimeType": "image/png",
                          "logoFilename": "logo1.png",
                          "logoCharset": "UTF-8"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeId").value(1))
                .andExpect(jsonPath("$.storeName").value("Store A"));
    }

    @Test
    void updateStore() throws Exception {
        Stores updatedStore = new Stores(1, "Updated Store A", "www.updatedstorea.com", "789 Pine St", 12.0, 22.0, null, "image/png", "updated_logo1.png", "UTF-8", new Date());

        when(storesService.updateStore(eq(1), any(Stores.class))).thenReturn(updatedStore); // Use eq(1) for id

        mockMvc.perform(put("/api/stores/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "storeName": "Updated Store A",
                          "webAddress": "www.updatedstorea.com",
                          "physicalAddress": "789 Pine St",
                          "latitude": 12.0,
                          "longitude": 22.0,
                          "logoMimeType": "image/png",
                          "logoFilename": "updated_logo1.png",
                          "logoCharset": "UTF-8"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeName").value("Updated Store A")); 
    }

    @Test
    void deleteStore() throws Exception {
        mockMvc.perform(delete("/api/stores/{id}", 1))
                .andExpect(status().isOk());
    }
}