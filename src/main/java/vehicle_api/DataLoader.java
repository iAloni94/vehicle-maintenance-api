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
        // 1. Create the SYM Reference
        MaintenanceReference HondaCBR1000RR = new MaintenanceReference();
        HondaCBR1000RR.setMake("Honda");
        HondaCBR1000RR.setModel("CBR1000RR");

        // 2. Add the specific component rules
        HondaCBR1000RR.addComponent("Engine Oil", 3000, "Replace");
        HondaCBR1000RR.addComponent("Gear Oil", 6000, "Replace");
        HondaCBR1000RR.addComponent("Air Filter", 6000, "Inspect/Clean");
        HondaCBR1000RR.addComponent("Spark Plug", 6000, "Replace");
        HondaCBR1000RR.addComponent("Drive Belt", 12000, "Replace");
        HondaCBR1000RR.addComponent("Brake Pads", 5000, "Inspect");

        referenceRepo.save(HondaCBR1000RR);
        System.out.println(">>> Database Seeded with Component Intervals!");
    }
}
}