// actual maintenance log
package vehicle_api;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List; 

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "maintenance_logs")
public class MaintenanceLog {

    @Id // set by postgres
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceDate;
    private int mileageReadingKm;
    

    // Many logs belong to one vehicle
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    @JsonIgnore // <--- ADD THIS LINE
    private Vehicle vehicle;

    @ManyToMany
    @JoinTable(
        name = "log_components",
        joinColumns = @JoinColumn(name = "log_id"),
        inverseJoinColumns = @JoinColumn(name = "component_id")
    )
    private List<ComponentReference> servicedComponents = new ArrayList<>();

    // Don't forget to generate the Getter and Setter for servicedComponents!
    public List<ComponentReference> getServicedComponents() { return servicedComponents; }
    public void setServicedComponents(List<ComponentReference> servicedComponents) { this.servicedComponents = servicedComponents; }


    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    // ... add getters/setters for the rest of the fields
    public String getServiceDate() {
        return serviceDate;
    }
    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }
    public int getmilageReadingKmKm() {
        return mileageReadingKm;
    }
    public void setmilageReadingKmKm(int milageReadingKmKm) {
        this.mileageReadingKm = milageReadingKmKm;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    public int getMileageReadingKm() {
        return mileageReadingKm;
    }
    public void setMileageReadingKm(int mileageReadingKm) {
        this.mileageReadingKm = mileageReadingKm;
    }
    
}