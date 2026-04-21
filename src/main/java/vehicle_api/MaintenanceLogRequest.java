package vehicle_api;

import java.util.List;

public class MaintenanceLogRequest {
    private String serviceDate;
    private int mileageReadingKm;
    private List<Long> servicedComponents; // We only ask the user for the IDs!

    // Getters and Setters
    public String getServiceDate() { return serviceDate; }
    public void setServiceDate(String serviceDate) { this.serviceDate = serviceDate; }
    public int getMileageReadingKm() { return mileageReadingKm; }
    public void setMileageReadingKm(int odometerReadingKm) { this.mileageReadingKm = odometerReadingKm; }
    public List<Long> getServicedComponents() { return servicedComponents; }
    public void setServicedComponents(List<Long> componentIds) { this.servicedComponents = componentIds; }
}