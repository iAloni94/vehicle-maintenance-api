package vehicle_api;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface MaintenanceReferenceRepository extends JpaRepository<MaintenanceReference, Long> {
    // We use IgnoreCase so "sym" and "SYM" both work
    Optional<MaintenanceReference> findFirstByMakeIgnoreCaseAndModelContainingIgnoreCase(String make, String model);
}