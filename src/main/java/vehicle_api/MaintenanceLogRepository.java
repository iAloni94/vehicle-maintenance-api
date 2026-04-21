package vehicle_api;

import vehicle_api.MaintenanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceLogRepository extends JpaRepository<MaintenanceLog, Long> {
    
    // Find all service logs for a specific vehicle ID
    List<MaintenanceLog> findByVehicleId(Long vehicleId);
    
    // Find logs based on Milage
    List<MaintenanceLog> findByMilage(String milage);
}
