// Saves the parts data and maintenance protocol
package vehicle_api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private MaintenanceReferenceRepository referenceRepo;

    @Override
public void run(String... args) throws Exception {
    if (referenceRepo.count() == 0) {
        // 1. Create the Honda Reference
        MaintenanceReference HondaCBR1000RR = new MaintenanceReference();
        HondaCBR1000RR.setMake("Honda");
        HondaCBR1000RR.setModel("CBR1000RR");

        // 2. Add the specific component rules
        HondaCBR1000RR.addComponent("Engine Oil", 3000, "Change");
        HondaCBR1000RR.addComponent("Gear Oil", 6000, "Change");
        HondaCBR1000RR.addComponent("Air Filter", 6000, "Inspection/Clean");
        HondaCBR1000RR.addComponent("Spark Plug", 6000, "Change");
        HondaCBR1000RR.addComponent("Drive Belt", 12000, "Change");
        HondaCBR1000RR.addComponent("Brake Pads", 5000, "Inspection");

        referenceRepo.save(HondaCBR1000RR);
        System.out.println(">>> Database Seeded with Component Intervals!");
    }
}
}