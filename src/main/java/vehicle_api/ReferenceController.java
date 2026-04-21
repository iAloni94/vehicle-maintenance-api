package vehicle_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/references")
@CrossOrigin(origins = "*")
public class ReferenceController {

    @Autowired
    private MaintenanceReferenceRepository repository;

    @GetMapping
    public List<MaintenanceReference> getAllReferences() {
        return repository.findAll();
    }
}