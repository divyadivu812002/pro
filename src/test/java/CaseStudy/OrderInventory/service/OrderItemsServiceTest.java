package CaseStudy.OrderInventory.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import CaseStudy.OrderInventory.DAO.OrderItemsDAO;
import CaseStudy.OrderInventory.model.OrderItems;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

class OrderItemsServiceTest {

    @InjectMocks
    private OrderItemsService orderItemsService;

    @Mock
    private OrderItemsDAO orderItemsDAO;

    private OrderItems orderItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderItem = new OrderItems();
        orderItem.setLineItemId(1);
        orderItem.setUnitPrice(100.0);
        orderItem.setQuantity(2);
    }

    @Test
    void testGetAllOrderItems() {
        List<OrderItems> orderItemsList = new ArrayList<>();
        orderItemsList.add(orderItem);

        when(orderItemsDAO.findAll()).thenReturn(orderItemsList);

        List<OrderItems> result = orderItemsService.getAllOrderItems();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderItemsDAO, times(1)).findAll();
    }

    @Test
    void testGetOrderItemByIdExists() {
        when(orderItemsDAO.findById(1)).thenReturn(Optional.of(orderItem));

        Optional<OrderItems> result = orderItemsService.getOrderItemById(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getLineItemId());
        verify(orderItemsDAO, times(1)).findById(1);
    }

    @Test
    void testGetOrderItemByIdNotExists() {
        when(orderItemsDAO.findById(1)).thenReturn(Optional.empty());

        Optional<OrderItems> result = orderItemsService.getOrderItemById(1);
        assertFalse(result.isPresent());
        verify(orderItemsDAO, times(1)).findById(1);
    }

    @Test
    void testSaveOrderItem() {
        when(orderItemsDAO.save(orderItem)).thenReturn(orderItem);

        OrderItems result = orderItemsService.saveOrderItem(orderItem);
        assertNotNull(result);
        assertEquals(1, result.getLineItemId());
        verify(orderItemsDAO, times(1)).save(orderItem);
    }

    @Test
    void testUpdateOrderItemExists() {
        OrderItems updatedOrderItem = new OrderItems();
        updatedOrderItem.setUnitPrice(200.0);
        updatedOrderItem.setQuantity(5);

        when(orderItemsDAO.findById(1)).thenReturn(Optional.of(orderItem));
        when(orderItemsDAO.save(any(OrderItems.class))).thenReturn(orderItem);

        OrderItems result = orderItemsService.updateOrderItem(1, updatedOrderItem);
        assertNotNull(result);
        assertEquals(200.0, result.getUnitPrice());
        assertEquals(5, result.getQuantity());
        verify(orderItemsDAO, times(1)).findById(1);
        verify(orderItemsDAO, times(1)).save(any(OrderItems.class));
    }

    @Test
    void testUpdateOrderItemNotExists() {
        when(orderItemsDAO.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderItemsService.updateOrderItem(1, orderItem);
        });

        assertEquals("OrderItem with ID 1 does not exist.", exception.getMessage());
        verify(orderItemsDAO, times(1)).findById(1);
        verify(orderItemsDAO, times(0)).save(any(OrderItems.class));
    }

    @Test
    void testDeleteOrderItem() {
        orderItemsService.deleteOrderItem(1);
        verify(orderItemsDAO, times(1)).deleteById(1);
    }
}
