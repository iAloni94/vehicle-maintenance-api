package vehicle_api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceService {

    private final MaintenanceLogRepository maintenanceRepo;
    private final VehicleRepository vehicleRepo;

    @Autowired
    private ComponentReferenceRepository componentReferenceRepository;

    public MaintenanceService(MaintenanceLogRepository maintenanceRepo, VehicleRepository vehicleRepo) {
        this.maintenanceRepo = maintenanceRepo;
        this.vehicleRepo = vehicleRepo;
        this.componentReferenceRepository = null;
    }

    public MaintenanceLog addLogToVehicle(Long vehicleId, MaintenanceLogRequest request) {
        // 1. Find the vehicle
        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        // update mileage according to at least the last known maintenance
        if (vehicle.getCurrentMileage() < request.getMileageReadingKm())
            vehicle.setCurrentMileage(request.getMileageReadingKm());

        // 2. Create the log
        MaintenanceLog log = new MaintenanceLog();
        log.setServiceDate(request.getServiceDate());
        log.setMileageReadingKm(request.getMileageReadingKm());
        log.setVehicle(vehicle);

        // 3. Link the components!
        if (request.getServicedComponents() != null && !request.getServicedComponents().isEmpty()) {

            List<ComponentReference> componentsToAttach = new ArrayList<>();

            for (Number rawId : request.getServicedComponents()) {
                // This forces the JSON number to become a strict Long
                Long safeId = rawId.longValue();

                ComponentReference foundPart = componentReferenceRepository.findById(safeId)
                        .orElseThrow(() -> new RuntimeException("CRASH: Could not find part with ID: " + safeId));

                componentsToAttach.add(foundPart);
            }

            log.setServicedComponents(componentsToAttach);
        }

        return maintenanceRepo.save(log);
    }
}