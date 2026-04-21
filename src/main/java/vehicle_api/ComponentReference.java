// Components used for maintenance.

package vehicle_api;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class ComponentReference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String componentName; // e.g., "Drive Belt"
    private int intervalKm;       // e.g., 12000
    private String action;       // e.g., "Replace"

    @ManyToOne
    @JoinColumn(name = "maintenance_reference_id")
    @JsonBackReference // Prevents infinite loops when converting to JSON
    private MaintenanceReference maintenanceReference;

    // Standard Constructors, Getters, and Setters
    public ComponentReference() {}
    public ComponentReference(String name, int km, String action, MaintenanceReference ref) {
        this.componentName = name;
        this.intervalKm = km;
        this.action = action;
        this.maintenanceReference = ref;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getComponentName() {
        return componentName;
    }
    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }
    public int getIntervalKm() {
        return intervalKm;
    }
    public void setIntervalKm(int intervalKm) {
        this.intervalKm = intervalKm;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public MaintenanceReference getMaintenanceReference() {
        return maintenanceReference;
    }
    public void setMaintenanceReference(MaintenanceReference maintenanceReference) {
        this.maintenanceReference = maintenanceReference;
    }
   
}