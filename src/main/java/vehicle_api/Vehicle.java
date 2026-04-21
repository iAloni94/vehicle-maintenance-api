package vehicle_api;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    
    @Id // id assigned by postgres
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String make; 
    private String model; 
    private int manufacturingYear;
    private int engineCapacityCc;
    private int currentMileage;

    // One vehicle can have many maintenance logs
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<MaintenanceLog> maintenanceLogs;
    
    // getters setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMake() {
        return make;
    }
    public void setMake(String make) {
        this.make = make;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public int getManufacturingYear() {
        return manufacturingYear;
    }
    public void setManufacturingYear(int manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }
    public int getEngineCapacityCc() {
        return engineCapacityCc;
    }
    public void setEngineCapacityCc(int engineCapacityCc) {
        this.engineCapacityCc = engineCapacityCc;
    }
    public List<MaintenanceLog> getMaintenanceLogs() {
        return maintenanceLogs;
    }
    public void setMaintenanceLogs(List<MaintenanceLog> maintenanceLogs) {
        this.maintenanceLogs = maintenanceLogs;
    }
    public int getCurrentMileage() {
        return currentMileage;
    }
    public void setCurrentMileage(int currentMilage) {
        this.currentMileage = currentMilage;
    }
    
}
