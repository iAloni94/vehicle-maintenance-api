package vehicle_api;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.List; 

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

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
            if(mileage != 0){
            newVehicle.setCurrentMileage(mileage);
            }else{
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


    //parse string containing number to only number
    private int parseStr(String str) {
        if (str == null || str.isEmpty()) return 0;

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
}