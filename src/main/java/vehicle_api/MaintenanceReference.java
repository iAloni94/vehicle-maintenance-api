
package vehicle_api;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List; 

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class MaintenanceReference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String make;
    private String model;

    @OneToMany(mappedBy = "maintenanceReference", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ComponentReference> components = new ArrayList<>();
    // Constructor, Getters, and Setters

    public void addComponent(String name, int maintenanceInterval, String action) {
        components.add(new ComponentReference(name, maintenanceInterval, action, this));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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


}