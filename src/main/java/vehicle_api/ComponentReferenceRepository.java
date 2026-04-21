// holds the DataLoader.java data for each part.

package vehicle_api;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentReferenceRepository extends JpaRepository<ComponentReference, Long> {
}