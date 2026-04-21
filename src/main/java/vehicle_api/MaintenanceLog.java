package vehicle_api;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "maintenance_logs")
public class MaintenanceLog {

    @Id // set by postgres
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate serviceDate;
    private int mileageReadingKm;
    
    private String description; 


    // Many logs belong to one vehicle
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    @JsonIgnore // <--- ADD THIS LINE
    private Vehicle vehicle;

    // Many logs can reference one specific component replacement
    @ManyToOne
    @JoinColumn(name = "component_id")
    private Component replacedComponent;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    // ... add getters/setters for the rest of the fields
    public LocalDate getServiceDate() {
        return serviceDate;
    }
    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }
    public int getmilageReadingKmKm() {
        return mileageReadingKm;
    }
    public void setmilageReadingKmKm(int milageReadingKmKm) {
        this.mileageReadingKm = milageReadingKmKm;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    public Component getReplacedComponent() {
        return replacedComponent;
    }
    public void setReplacedComponent(Component replacedComponent) {
        this.replacedComponent = replacedComponent;
    }
    
}