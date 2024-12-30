package CaseStudy.OrderInventory.controller;

import CaseStudy.OrderInventory.model.Shipments;
import CaseStudy.OrderInventory.service.ShipmentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ShipmentsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ShipmentsService shipmentsService;

    @InjectMocks
    private ShipmentsController shipmentsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(shipmentsController).build();
    }

    @Test
    void getAllShipments() throws Exception {
        Shipments shipment1 = new Shipments();
        shipment1.setShipmentId(1);
        shipment1.setDeliveryAddress("123 Main St");
        shipment1.setShipmentStatus("Shipped");

        Shipments shipment2 = new Shipments();
        shipment2.setShipmentId(2);
        shipment2.setDeliveryAddress("456 Elm St");
        shipment2.setShipmentStatus("Pending");

        when(shipmentsService.getAllShipments()).thenReturn(Arrays.asList(shipment1, shipment2));

        mockMvc.perform(get("/shipments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].shipmentId").value(1))
                .andExpect(jsonPath("$[1].shipmentId").value(2));
    }

    @Test
    void getShipmentById() throws Exception {
        Shipments shipment = new Shipments();
        shipment.setShipmentId(1);
        shipment.setDeliveryAddress("123 Main St");
        shipment.setShipmentStatus("Shipped");

        when(shipmentsService.getShipmentById(1L)).thenReturn(shipment);

        mockMvc.perform(get("/shipments/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shipmentId").value(1))
                .andExpect(jsonPath("$.deliveryAddress").value("123 Main St"));
    }

  /*  @Test
    void addShipment() throws Exception {
        Shipments shipment = new Shipments();
        shipment.setShipmentId(1);
        shipment.setDeliveryAddress("123 Main St");
        shipment.setShipmentStatus("Shipped");

        mockMvc.perform(post("/shipments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"storeId\": 101, \"deliveryAddress\": \"123 Main St\", \"shipmentStatus\": \"Shipped\" }"))
                .andExpect(status().isOk())
                .andExpect(content().string("Record created"));

        verify(shipmentsService).addShipment(any(Shipments.class));
    }

    @Test
    void updateShipment() throws Exception {
        Shipments shipment = new Shipments();
        shipment.setShipmentId(1);
        shipment.setDeliveryAddress("123 Main St");
        shipment.setShipmentStatus("Shipped");

        when(shipmentsService.updateShipment(eq(1L), any(Shipments.class))).thenReturn(shipment);

        mockMvc.perform(put("/shipments/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"storeId\": 101, \"deliveryAddress\": \"123 Main St\", \"shipmentStatus\": \"Shipped\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shipmentId").value(1))
                .andExpect(jsonPath("$.shipmentStatus").value("Shipped"));

        verify(shipmentsService).updateShipment(eq(1L), any(Shipments.class));
    }
*/
    @Test
    void deleteShipment() throws Exception {
        // Mock the behavior of the service layer for a void method
        doNothing().when(shipmentsService).deleteShipment(1L);

        // Perform the DELETE request
        mockMvc.perform(delete("/shipments/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Record deleted"));

        // Verify the service method was called
        verify(shipmentsService).deleteShipment(1L);
    }
}
