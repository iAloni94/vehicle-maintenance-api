package vehicle_api;

import org.springframework.data.jpa.repository.JpaRepository;
// CHECK THIS IMPORT CAREFULLY:
import vehicle_api.ComponentReference; 

public interface ComponentReferenceRepository extends JpaRepository<ComponentReference, Long> {
}