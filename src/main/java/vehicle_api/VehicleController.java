package vehicle_api;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final MaintenanceService maintenanceService; // Add this

    public VehicleController(VehicleService vehicleService, MaintenanceService maintenanceService) {
        this.vehicleService = vehicleService;
        this.maintenanceService = maintenanceService;
    }

    @PutMapping("/{id}")
    public Vehicle updateVehicle(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        return vehicleService.updateVehicle(id, vehicle);
    }

    // Standard status check
    @GetMapping("/status")
    public String checkStatus() {
        return "Vehicle Maintenance API is fully connected!";
    }

    // Receive JSON data and save it to the database
    @PostMapping
    public Vehicle registerVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.addVehicle(vehicle);
    }

    // Fetch all vehicles from the database
    @GetMapping
    public List<Vehicle> fetchAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @PostMapping("/{vehicleId}/maintenance")
    public MaintenanceLog logMaintenance(@PathVariable Long vehicleId, @RequestBody MaintenanceLogRequest logRequest) {
        return maintenanceService.addLogToVehicle(vehicleId, logRequest);
    }

    @PostMapping("/autofill")
    public Vehicle autoFillVehicle(
            @RequestParam String make, 
            @RequestParam String model, 
            @RequestParam int year,
        @RequestParam int mileage) {
        
        return vehicleService.autoFillAndSaveVehicle(make, model, year, mileage);
    }
}