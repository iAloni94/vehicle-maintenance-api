package vehicle_api;

import vehicle_api.Vehicle;
import vehicle_api.VehicleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    // Spring automatically injects the repository here
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    // Business Logic to save a new vehicle
    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    // Business Logic to fetch all vehicles
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {
    // 1. Find the existing vehicle or throw an error
    return vehicleRepository.findById(id)
        .map(existingVehicle -> {
            // 2. Update the specific attributes
            existingVehicle.setMake(updatedVehicle.getMake());
            existingVehicle.setModel(updatedVehicle.getModel());
            existingVehicle.setManufacturingYear(updatedVehicle.getManufacturingYear());
            existingVehicle.setEngineCapacityCc(updatedVehicle.getEngineCapacityCc());
            existingVehicle.setCurrentMilage(updatedVehicle.getCurrentMilage());
            
            // 3. Save the modified object
            return vehicleRepository.save(existingVehicle);
        })
        .orElseThrow(() -> new RuntimeException("Vehicle not found with id " + id));
}
}