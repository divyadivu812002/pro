package CaseStudy.OrderInventory.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import CaseStudy.OrderInventory.DAO.StoresDAO;
import CaseStudy.OrderInventory.model.Stores;

import java.util.List;

@Service
public class StoresService {

    @Autowired
    private StoresDAO storeDAO;

    // Retrieve all stores
    public List<Stores> getAllStores() {
        return storeDAO.findAll();
    }

    // Retrieve a store by ID
    public Stores getStoreById(int id) {
        List<Stores> stores = storeDAO.findById(id);
        if (stores.isEmpty()) {
            throw new EntityNotFoundException("Store with ID " + id + " not found.");
        }
        return stores.get(0); 
    }
    public Stores addStore(Stores store) {
        return storeDAO.save(store);
    }

    public Stores updateStore(int id, Stores updatedStore) {
        List<Stores> existingStores = storeDAO.findById(id);
        if (existingStores.isEmpty()) {
            throw new EntityNotFoundException("Store with ID " + id + " not found.");
        }

        Stores existingStore = existingStores.get(0);

        // Update store fields
        existingStore.setStoreName(updatedStore.getStoreName());
        existingStore.setWebAddress(updatedStore.getWebAddress());
        existingStore.setPhysicalAddress(updatedStore.getPhysicalAddress());
        existingStore.setLatitude(updatedStore.getLatitude());
        existingStore.setLongitude(updatedStore.getLongitude());
        existingStore.setLogo(updatedStore.getLogo());
        existingStore.setLogoMimeType(updatedStore.getLogoMimeType());
        existingStore.setLogoFilename(updatedStore.getLogoFilename());
        existingStore.setLogoCharset(updatedStore.getLogoCharset());
        existingStore.setLogoLastUpdated(updatedStore.getLogoLastUpdated());

        return storeDAO.save(existingStore);
    }

    public void deleteStore(int id) {
        List<Stores> stores = storeDAO.findById(id);
        if (stores.isEmpty()) {
            throw new EntityNotFoundException("Store with ID " + id + " not found.");
        }
        storeDAO.delete(stores.get(0));
    }

    public boolean findStore(Stores store) {
        return storeDAO.findById(store.getStoreId()).isPresent();
    }
}
