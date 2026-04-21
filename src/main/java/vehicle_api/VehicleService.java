package vehicle_api;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    @Autowired
    private ComponentReferenceRepository componentReferenceRepository;

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
                    existingVehicle.setCurrentMileage(updatedVehicle.getCurrentMileage());

                    // 3. Save the modified object
                    return vehicleRepository.save(existingVehicle);
                })
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id " + id));
    }

    // pulls data from ninja-api for motorcycles given make model and year
    public Vehicle autoFillAndSaveVehicle(String make, String model, int year, int mileage) {
        // 1. Set up the outbound HTTP client
        RestTemplate restTemplate = new RestTemplate();

        // 2. Build the exact URL (e.g., searching for a SYM GTS 125)
        String apiUrl = "https://api.api-ninjas.com/v1/motorcycles?make=" + make + "&model=" + model;

        // 3. Attach your secret API key to the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", "gJPKRcMiWAtIKnWuXRPFDEvoPKIYHSMJrUnlErJi");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 4. Fire the request and catch the JSON array response
        ResponseEntity<MotorcycleDTO[]> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                MotorcycleDTO[].class);

        MotorcycleDTO[] fetchedData = response.getBody();

        if (fetchedData != null && fetchedData.length > 0) {
            // Grab the first matching result
            MotorcycleDTO exactMatch = fetchedData[0];

            // 5. Build your official Vehicle entity using the external data
            Vehicle newVehicle = new Vehicle();
            newVehicle.setMake(exactMatch.getMake());
            newVehicle.setModel(exactMatch.getModel());
            newVehicle.setManufacturingYear(year);
            if (mileage != 0) {
                newVehicle.setCurrentMileage(mileage);
            } else {
                newVehicle.setCurrentMileage(0);
            }

            // check for null and clean string
            if (exactMatch.getDisplacement() != null) {
                newVehicle.setEngineCapacityCc(parseStr(exactMatch.getDisplacement()));
            } else {
                newVehicle.setEngineCapacityCc(0);

            }
            // 6. Save to your PostgreSQL database
            return vehicleRepository.save(newVehicle);
        } else

        {
            throw new RuntimeException("Could not find official specs for this vehicle.");
        }
    }

    // parse string containing number to only number
    private int parseStr(String str) {
        if (str == null || str.isEmpty())
            return 0;

        // reges looking for first digit, a decimal if present and more digit.
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("(\\d+(\\.\\d+)?)").matcher(str);

        if (matcher.find()) {
            try {
                // matcher.group(1) gives us ONLY the first valid number found (e.g., "998.0")
                String cleanNumber = matcher.group(1);
                return (int) Math.round(Double.parseDouble(cleanNumber));
            } catch (NumberFormatException e) {
                System.err.println("Failed to parse numeric part: " + str);
                return 0;
            }
        }

        return 0;
    }

    public String getNextServiceDue(Long vehicleId, Long componentId) {
        // 1. Get the vehicle
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        // 2. Variables to track the most recent service
        int highestMileageLogged = 0;
        int requiredInterval = 0;
        boolean hasBeenServiced = false;

        ComponentReference foundPart = componentReferenceRepository.findById(componentId)
                .orElseThrow(() -> new RuntimeException("CRASH: Could not find part with ID: " + componentId));

        // 3. Loop through all logs for this specific vehicle
        for (MaintenanceLog log : vehicle.getMaintenanceLogs()) {

            // Check if this specific log included the part we are looking for (e.g., Engine
            // Oil)
            for (ComponentReference part : log.getServicedComponents()) {
                if (part.getId().equals(componentId)) {

                    // If it does, and the mileage is the highest we've seen, save it!
                    if (log.getmilageReadingKmKm() > highestMileageLogged) {
                        highestMileageLogged = log.getmilageReadingKmKm();
                        requiredInterval = part.getIntervalKm(); // Grab the 3000km rule
                        hasBeenServiced = true;
                    }
                }
            }
        }

        // 4. Return the result
        if (!hasBeenServiced) {
            return "No history found for this component. Check manufacturer manual!";
        }

        int nextServiceMileage = highestMileageLogged + requiredInterval;
        int kmsRemaining = nextServiceMileage - vehicle.getCurrentMileage();

        return "Next " + foundPart.getComponentName() + " " + foundPart.getAction() + " due at: " + nextServiceMileage
                + "km. You have " + kmsRemaining
                + "km left.";
    }

    public List<ComponentStatus> getFullMaintenanceStatus(Long vehicleId) {
        // 1. Get the vehicle
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        int currentOdo = vehicle.getCurrentMileage();

        // 2. The "Whiteboard" (HashMap)
        // Key = Component ID, Value = Highest Odometer Reading seen so far
        Map<Long, Integer> latestServiceMap = new HashMap<>();
        List<MaintenanceLog> logs = vehicle.getMaintenanceLogs();

        // 3. Loop through the logs EXACTLY ONCE
        for (MaintenanceLog log : logs) {
            int logMileage = log.getMileageReadingKm();

            // loop on each component in said log
            for (ComponentReference part : log.getServicedComponents()) {
                Long partId = part.getId();

                // If the part isn't mapped, OR if this log's mileage is higher
                // than what's on the board:
                if (!latestServiceMap.containsKey(partId) || logMileage > latestServiceMap.get(partId)) {
                    latestServiceMap.put(partId, logMileage); // add to map
                }
            }
        }

        List<ComponentStatus> fullReport = new ArrayList<>();

        // 4. Loop through EVERY component in map
        for (var entry : latestServiceMap.entrySet()) {
            ComponentStatus status = new ComponentStatus();
            ComponentReference thisPart = componentReferenceRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("CRASH: Could not find part with ID: " + entry.getKey()));
            status.setComponentName(thisPart.getComponentName());
            status.setIntervalKm(thisPart.getIntervalKm());
             status.setLastServicedKm(entry.getValue());
            int nextDue = entry.getValue() + thisPart.getIntervalKm();
            status.setNextDueKm(nextDue);
            status.setRemainingKm(nextDue - currentOdo);
            status.setOverdue(currentOdo >= nextDue);

            fullReport.add(status);
        }

        return fullReport;
    }

}