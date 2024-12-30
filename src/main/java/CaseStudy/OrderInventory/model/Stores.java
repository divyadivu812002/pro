package CaseStudy.OrderInventory.model;
 
import java.util.Date;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
 
@Entity
@Table(name = "stores")
public class Stores {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "store_id")
    private int storeId;
 
    @Column(name = "store_name", nullable = false, length = 255)
    private String storeName;
 
    @Column(name = "web_address", length = 100)
    private String webAddress;
 
    @Column(name = "physical_address", length = 512)
    private String physicalAddress;
 
    @Column(name = "latitude")
    private Double latitude;
 
    @Column(name = "longitude")
    private Double longitude;
 
    @Lob
    @Column(name = "logo")
    private byte[] logo;
 
    @Column(name = "logo_mime_type", length = 512)
    private String logoMimeType;
 
    @Column(name = "logo_filename", length = 512)
    private String logoFilename;
 
    @Column(name = "logo_charset", length = 512)
    private String logoCharset;
 
    @Column(name = "logo_last_updated")
    private Date logoLastUpdated;
 
    public Stores() {
    
    }
    
    public Stores(Integer storeId, String storeName, String webAddress, String physicalAddress, Double latitude,
			Double longitude, byte[] logo, String logoMimeType, String logoFilename, String logoCharset,
			Date logoLastUpdated) {
		super();
		this.storeId = storeId;
		this.storeName = storeName;
		this.webAddress = webAddress;
		this.physicalAddress = physicalAddress;
		this.latitude = latitude;
		this.longitude = longitude;
		this.logo = logo;
		this.logoMimeType = logoMimeType;
		this.logoFilename = logoFilename;
		this.logoCharset = logoCharset;
		this.logoLastUpdated = logoLastUpdated;
	}
 
	// Getters and Setters
	public Integer getStoreId() {
		return storeId;
	}
 
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
 
	public String getStoreName() {
		return storeName;
	}
 
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
 
	public String getWebAddress() {
		return webAddress;
	}
 
	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}
 
	public String getPhysicalAddress() {
		return physicalAddress;
	}
 
	public void setPhysicalAddress(String physicalAddress) {
		this.physicalAddress = physicalAddress;
	}
 
	public Double getLatitude() {
		return latitude;
	}
 
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
 
	public Double getLongitude() {
		return longitude;
	}
 
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
 
	public byte[] getLogo() {
		return logo;
	}
 
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
 
	public String getLogoMimeType() {
		return logoMimeType;
	}
 
	public void setLogoMimeType(String logoMimeType) {
		this.logoMimeType = logoMimeType;
	}
 
	public String getLogoFilename() {
		return logoFilename;
	}
 
	public void setLogoFilename(String logoFilename) {
		this.logoFilename = logoFilename;
	}
 
	public String getLogoCharset() {
		return logoCharset;
	}
 
	public void setLogoCharset(String logoCharset) {
		this.logoCharset = logoCharset;
	}
 
	public Date getLogoLastUpdated() {
		return logoLastUpdated;
	}
 
	public void setLogoLastUpdated(Date logoLastUpdated) {
		this.logoLastUpdated = logoLastUpdated;
	}
    
}