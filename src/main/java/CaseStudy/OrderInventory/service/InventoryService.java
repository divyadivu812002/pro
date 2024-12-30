package CaseStudy.OrderInventory.service;

import CaseStudy.OrderInventory.DAO.InventoryDAO;
import CaseStudy.OrderInventory.DAO.OrderItemsDAO;
import CaseStudy.OrderInventory.DAO.OrdersDAO;
import CaseStudy.OrderInventory.DAO.ShipmentsDAO;
import CaseStudy.OrderInventory.model.Inventory;
import CaseStudy.OrderInventory.model.OrderItems;
import CaseStudy.OrderInventory.model.Orders;
import CaseStudy.OrderInventory.model.Products;
import CaseStudy.OrderInventory.model.Shipments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryDAO inventoryDAO;
    @Autowired
    private ShipmentsDAO shipmentsDAO;
    @Autowired
    private OrdersDAO ordersDAO;
    @Autowired
    private OrderItemsDAO orderItemsDAO;

    public List<Inventory> getAllInventory() {
        return inventoryDAO.findAll();
    }
    public Inventory getInventoryById(int inventoryId) {
        return inventoryDAO.findById(inventoryId).orElse(null); // Added method
    }

    public List<Inventory> getInventoryByStore(int storeId) {
        return inventoryDAO.findByStoreId_StoreId(storeId);
    }

    public List<Inventory> getInventoryByProduct(int productId) {
        return inventoryDAO.findByProductId_ProductId(productId);
    }

    public List<Inventory> getInventoryByStoreAndProduct(int storeId, int productId) {
        return inventoryDAO.findByStoreId_StoreIdAndProductId_ProductId(storeId, productId);
    }

    public Map<String, Long> getShipmentStatusCount(int storeId) {
        List<Inventory> inventories = inventoryDAO.findByStoreId_StoreId(storeId);

        Map<String, Long> shipmentStatusCount = new HashMap<>();
        for (Inventory inventory : inventories) {
            List<Shipments> shipments = shipmentsDAO.findByStoreId_StoreId(inventory.getStoreId().getStoreId());
            for (Shipments shipment : shipments) {
                shipmentStatusCount.merge(shipment.getShipmentStatus(), 1L, Long::sum);
            }
        }
        return shipmentStatusCount;
    }

    public List<Inventory> getInventoriesWithShipments() {
        try {
            // Fetch all inventories
            List<Inventory> inventories = inventoryDAO.findAll();

            // Filter inventories with shipments
            List<Inventory> matchingInventories = inventories.stream()
                    .filter(inventory -> !shipmentsDAO.findByStoreId_StoreId(inventory.getStoreId().getStoreId()).isEmpty())
                    .collect(Collectors.toList());

            return matchingInventories;
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching inventories with shipments.", e);
        }
    }

    public List<Map<String, Object>> getInventoryDetailsByOrderId(int orderId) {
        Orders order = ordersDAO.findById(orderId).orElse(null);

        if (order == null || order.getStoreId() == null) {
            return Collections.emptyList();
        }

        List<Map<String, Object>> response = new ArrayList<>();
        List<Inventory> inventories = inventoryDAO.findByStoreId_StoreId(order.getStoreId().getStoreId());
        List<Shipments> shipments = shipmentsDAO.findByStoreId_StoreId(order.getStoreId().getStoreId());

        for (Inventory inventory : inventories) {
            Map<String, Object> detail = new HashMap<>();
            detail.put("storeName", order.getStoreId().getStoreName());
            detail.put("productName", inventory.getProductId().getProductName());
            detail.put("productPrice", inventory.getProductId().getUnitPrice());
            detail.put("shipmentStatus", shipments.stream()
                    .filter(shipment -> shipment.getStoreId().getStoreId() == inventory.getStoreId().getStoreId())
                    .map(Shipments::getShipmentStatus)
                    .findFirst()
                    .orElse("Not Shipped"));
            detail.put("totalAmount", inventory.getProductId().getUnitPrice() * inventory.getProductInventory());

            response.add(detail);
        }

        return response;
    }
    public Inventory createInventory(Inventory inventory) {
        return inventoryDAO.save(inventory);
    }

    public Inventory updateInventory(Inventory inventory) {
        return inventoryDAO.save(inventory);
    }

    public void deleteInventory(Inventory inventory) {
        inventoryDAO.delete(inventory);
    }

}
