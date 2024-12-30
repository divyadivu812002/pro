package CaseStudy.OrderInventory.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import CaseStudy.OrderInventory.DAO.OrderItemsDAO;
import CaseStudy.OrderInventory.model.OrderItems;
 
import java.util.List;
import java.util.Optional;
 
@Service
public class OrderItemsService {
 
    @Autowired
    private OrderItemsDAO orderItemsDAO;
 
    // Fetch all OrderItems
    public List<OrderItems> getAllOrderItems() {
        return orderItemsDAO.findAll();
    }
 
    // Fetch an OrderItem by its ID
    public Optional<OrderItems> getOrderItemById(int lineItemId) {
        return orderItemsDAO.findById(lineItemId);
    }
 
    // Add or save a new OrderItem
    public OrderItems saveOrderItem(OrderItems orderItem) {
        return orderItemsDAO.save(orderItem);
    }
 
    // Update an existing OrderItem
    public OrderItems updateOrderItem(int lineItemId, OrderItems updatedOrderItem) {
        Optional<OrderItems> existingOrderItemOptional = orderItemsDAO.findById(lineItemId);
 
        if (existingOrderItemOptional.isPresent()) {
            OrderItems existingOrderItem = existingOrderItemOptional.get();
 
            // Update relevant fields
            existingOrderItem.setOrder(updatedOrderItem.getOrder());
            existingOrderItem.setProduct(updatedOrderItem.getProduct());
            existingOrderItem.setUnitPrice(updatedOrderItem.getUnitPrice());
            existingOrderItem.setQuantity(updatedOrderItem.getQuantity());
            existingOrderItem.setShipment(updatedOrderItem.getShipment());
 
            return orderItemsDAO.save(existingOrderItem);
        } else {
            throw new IllegalArgumentException("OrderItem with ID " + lineItemId + " does not exist.");
        }
    }
 
    // Delete an OrderItem by ID
    public void deleteOrderItem(int lineItemId) {
        orderItemsDAO.deleteById(lineItemId);
    }
}