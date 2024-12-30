package CaseStudy.OrderInventory.service;
 
 
 
import jakarta.persistence.EntityNotFoundException;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import CaseStudy.OrderInventory.DAO.ShipmentsDAO;
import CaseStudy.OrderInventory.model.Shipments;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
@Service
public class ShipmentsService {
 
    @Autowired
    private ShipmentsDAO shipmentDAO;
 
    private List<Shipments> shipmentList;
 
    public ShipmentsService() {
        shipmentList = new ArrayList<>();
    }
 
    // Retrieve all shipments
    public List<Shipments> getAllShipments() {
        return shipmentDAO.findAll();
    }
 
    // Save a new shipment
    public void saveShipment(Shipments shipment) {
        shipmentList.add(shipment);
        shipmentDAO.save(shipment);
    }
 
    // Update an existing shipment
    public void updateShipment(Integer id, Shipments updatedShipment) {
        Optional<Shipments> existingShipmentOptional = shipmentDAO.findById(id);
 
        if (existingShipmentOptional.isPresent()) {
            Shipments existingShipment = existingShipmentOptional.get();
 
            existingShipment.setStoreId(updatedShipment.getStoreId());
            existingShipment.setCustomer(updatedShipment.getCustomer());
            existingShipment.setDeliveryAddress(updatedShipment.getDeliveryAddress());
            existingShipment.setShipmentStatus(updatedShipment.getShipmentStatus());
 
            shipmentDAO.save(existingShipment);
        } else {
            throw new EntityNotFoundException("Shipment with ID " + id + " not found.");
        }
    }
 
    // Delete a shipment
    public void deleteShipment(Integer id) {
        Optional<Shipments> shipmentOptional = shipmentDAO.findById(id);
 
        if (shipmentOptional.isPresent()) {
            shipmentDAO.delete(shipmentOptional.get());
        } else {
            throw new EntityNotFoundException("Shipment with ID " + id + " not found.");
        }
    }
 
    // Check if a shipment exists
    public boolean findShipment(Shipments shipment) {
        return shipmentList.contains(shipment);
    }
 
	public Shipments getShipmentById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
 
	public void addShipment(Shipments shipment) {
		// TODO Auto-generated method stub
		shipmentDAO.save(shipment);
	}
 
	public Shipments updateShipment(Long id, Shipments shipment) {
		// TODO Auto-generated method stub
		return null;
	}
 
	public void deleteShipment(Long id) {
		// TODO Auto-generated method stub
		
	}
}