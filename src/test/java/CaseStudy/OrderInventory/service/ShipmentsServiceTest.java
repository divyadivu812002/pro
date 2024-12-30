package CaseStudy.OrderInventory.service;

import CaseStudy.OrderInventory.model.Customers;
import CaseStudy.OrderInventory.model.Shipments;
import CaseStudy.OrderInventory.model.Stores;
import CaseStudy.OrderInventory.DAO.ShipmentsDAO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShipmentsServiceTest {

    @InjectMocks
    private ShipmentsService shipmentsService;

    @Mock
    private ShipmentsDAO shipmentDAO;

    private Shipments sampleShipment;
    private Customers sampleCustomer;
    private Stores sampleStore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize sample shipment and customer
        sampleCustomer = new Customers();
        sampleCustomer.setCustomerId(1);  // Assigning sample customer ID
        sampleStore = new Stores();
        sampleStore.setStoreId(1); 
        sampleShipment = new Shipments();
        sampleShipment.setShipmentId(1);
        sampleShipment.setStoreId(sampleStore);
        sampleShipment.setCustomer(sampleCustomer);
        sampleShipment.setDeliveryAddress("123 Test Address");
        sampleShipment.setShipmentStatus("In Transit");
    }

    @Test
    void testFindShipment() {
        // Add the sample shipment to the list using saveShipment()
        shipmentsService.saveShipment(sampleShipment);

        // Now call findShipment() and assert it returns true because the shipment was added
        assertTrue(shipmentsService.findShipment(sampleShipment));
    }

    @Test
    void testGetAllShipments() {
        // Simulate returning all shipments from DAO
        when(shipmentDAO.findAll()).thenReturn(List.of(sampleShipment));

        // Call getAllShipments and verify
        assertEquals(1, shipmentsService.getAllShipments().size());
        verify(shipmentDAO, times(1)).findAll();
    }

    @Test
    void testSaveShipment() {
        // Simulate the save action using Mockito.when
        when(shipmentDAO.save(sampleShipment)).thenReturn(sampleShipment);

        // Save the shipment
        shipmentsService.saveShipment(sampleShipment);

        // Verify that save() was called once
        verify(shipmentDAO, times(1)).save(sampleShipment);
    }

    @Test
    void testUpdateShipment() {
        // Simulate the existing shipment from DAO
        when(shipmentDAO.findById(1)).thenReturn(Optional.of(sampleShipment));

        // Create updated shipment data
        Shipments updatedShipment = new Shipments();
        updatedShipment.setStoreId(sampleStore);
        updatedShipment.setCustomer(sampleCustomer);
        updatedShipment.setDeliveryAddress("456 Updated Address");
        updatedShipment.setShipmentStatus("Delivered");

        // Update the shipment
        shipmentsService.updateShipment(1, updatedShipment);

        // Verify that the shipmentDAO save method was called to persist the changes
        verify(shipmentDAO, times(1)).save(sampleShipment);
    }

    @Test
    void testUpdateShipmentNotFound() {
        // Simulate a situation where the shipment ID does not exist
        when(shipmentDAO.findById(2)).thenReturn(Optional.empty());

        // Create updated shipment data
        Shipments updatedShipment = new Shipments();
        updatedShipment.setStoreId(sampleStore);
        updatedShipment.setCustomer(sampleCustomer);
        updatedShipment.setDeliveryAddress("456 Updated Address");
        updatedShipment.setShipmentStatus("Delivered");

        // Assert that EntityNotFoundException is thrown when the shipment is not found
        assertThrows(EntityNotFoundException.class, () -> {
            shipmentsService.updateShipment(2, updatedShipment);
        });
    }

    @Test
    void testDeleteShipment() {
        // Simulate the existing shipment from DAO
        when(shipmentDAO.findById(1)).thenReturn(Optional.of(sampleShipment));

        // Delete the shipment
        shipmentsService.deleteShipment(1);

        // Verify that delete() was called once
        verify(shipmentDAO, times(1)).delete(sampleShipment);
    }

    @Test
    void testDeleteShipmentNotFound() {
        // Simulate a situation where the shipment ID does not exist
        when(shipmentDAO.findById(2)).thenReturn(Optional.empty());

        // Assert that EntityNotFoundException is thrown when trying to delete a non-existent shipment
        assertThrows(EntityNotFoundException.class, () -> {
            shipmentsService.deleteShipment(2);
        });
    }

    @Test
    void testSaveShipmentWithMockedDAO() {
        // Mock save method
        when(shipmentDAO.save(sampleShipment)).thenReturn(sampleShipment);

        // Save shipment using service
        shipmentsService.saveShipment(sampleShipment);

        // Verify save() is called once
        verify(shipmentDAO, times(1)).save(sampleShipment);
    }
}