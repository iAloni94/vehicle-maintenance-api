package vehicle_api;

import vehicle_api.MaintenanceLog;
import vehicle_api.Vehicle;
import vehicle_api.MaintenanceLogRepository;
import vehicle_api.VehicleRepository;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceService {

    private final MaintenanceLogRepository maintenanceRepo;
    private final VehicleRepository vehicleRepo;

    public MaintenanceService(MaintenanceLogRepository maintenanceRepo, VehicleRepository vehicleRepo) {
        this.maintenanceRepo = maintenanceRepo;
        this.vehicleRepo = vehicleRepo;
    }

    // add maintenance event to vehicle hostory
    public MaintenanceLog addLogToVehicle(Long vehicleId, MaintenanceLog log) {
        // Find the vehicle by its ID, or throw an error if it doesn't exist
        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        
        // Attach the vehicle to the log
        log.setVehicle(vehicle);
        
        // Save the log to the database
        return maintenanceRepo.save(log);
    }
}