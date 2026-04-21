package vehicle_api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MotorcycleDTO {
    // API-Ninjas returns data in specific field names, so we map them
    private String make;
    private String model;
    private String year;
    
    @JsonProperty("displacement")
    private String displacement; // e.g., "124.0 ccm"
    
    @JsonProperty("fuel_system")
    private String fuelSystem; // e.g., "Carburetor" or "Injection"

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getFuelSystem() {
        return fuelSystem;
    }

    public void setFuelSystem(String fuelSystem) {
        this.fuelSystem = fuelSystem;
    }
    
}