package vehicle_api;

import vehicle_api.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {
    
    // Find all components within a specific category (e.g., "Electrical")
    List<Component> findByCategory(String category);
}