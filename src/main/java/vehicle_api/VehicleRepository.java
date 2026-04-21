package vehicle_api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    
    // Spring magically writes the SQL for this based purely on the method name!
    List<Vehicle> findByMake(String make); 
    
    // Find all vehicles with a specific engine size (e.g., 125 or 500)
    List<Vehicle> findByEngineCapacityCc(int capacity);
    List<Vehicle> findById(int id);
    

}