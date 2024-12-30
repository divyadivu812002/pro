package CaseStudy.OrderInventory.controller;

import CaseStudy.OrderInventory.model.OrderItems;
import CaseStudy.OrderInventory.model.Orders;
import CaseStudy.OrderInventory.model.Products;
import CaseStudy.OrderInventory.model.Shipments;
import CaseStudy.OrderInventory.service.OrderItemsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderItemsControllerTest {

    @InjectMocks
    private OrderItemsController orderItemsController;

    @Mock
    private OrderItemsService orderItemsService;

    private OrderItems mockOrderItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock OrderItems object with relationships
        mockOrderItem = new OrderItems();
        mockOrderItem.setLineItemId(1);
        mockOrderItem.setOrder(new Orders()); // Set a mock Orders object
        mockOrderItem.setProduct(new Products()); // Set a mock Products object
        mockOrderItem.setShipment(new Shipments()); // Set a mock Shipments object
        mockOrderItem.setUnitPrice(100.0);
        mockOrderItem.setQuantity(2);
    }

    @Test
    void testFetchAllOrderItems() {
        List<OrderItems> mockOrderItems = Collections.singletonList(mockOrderItem);
        when(orderItemsService.getAllOrderItems()).thenReturn(mockOrderItems);

        List<OrderItems> response = orderItemsController.fetchAllOrderItems();

        assertEquals(mockOrderItems, response);
        verify(orderItemsService, times(1)).getAllOrderItems();
    }

    @Test
    void testFetchOrderItemById_Success() {
        int lineItemId = 1;
        when(orderItemsService.getOrderItemById(lineItemId)).thenReturn(Optional.of(mockOrderItem));

        OrderItems response = orderItemsController.fetchOrderItemById(lineItemId);

        assertEquals(mockOrderItem, response);
        verify(orderItemsService, times(1)).getOrderItemById(lineItemId);
    }

    @Test
    void testFetchOrderItemById_NotFound() {
        int lineItemId = 1;
        when(orderItemsService.getOrderItemById(lineItemId)).thenReturn(Optional.empty());

        try {
            orderItemsController.fetchOrderItemById(lineItemId);
        } catch (IllegalArgumentException e) {
            assertEquals("OrderItem with ID " + lineItemId + " not found", e.getMessage());
        }

        verify(orderItemsService, times(1)).getOrderItemById(lineItemId);
    }

    @Test
    void testSaveOrderItem() {
        // Assuming saveOrderItem returns a ResponseEntity or similar, instead of void
        when(orderItemsService.saveOrderItem(mockOrderItem)).thenReturn(mockOrderItem);

        ResponseEntity<String> response = orderItemsController.saveOrderItem(mockOrderItem);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Record created", response.getBody());
        verify(orderItemsService, times(1)).saveOrderItem(mockOrderItem);
    }

    @Test
    void testUpdateOrderItem() {
        int lineItemId = 1;
        when(orderItemsService.updateOrderItem(lineItemId, mockOrderItem)).thenReturn(mockOrderItem);

        OrderItems response = orderItemsController.updateOrderItem(lineItemId, mockOrderItem);

        assertEquals(mockOrderItem, response);
        verify(orderItemsService, times(1)).updateOrderItem(lineItemId, mockOrderItem);
    }

    @Test
    void testDeleteOrderItem() {
        int lineItemId = 1;
        doNothing().when(orderItemsService).deleteOrderItem(lineItemId);

        ResponseEntity<String> response = orderItemsController.deleteOrderItem(lineItemId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Record deleted", response.getBody());
        verify(orderItemsService, times(1)).deleteOrderItem(lineItemId);
    }
}
