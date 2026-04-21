package vehicle_api;

public class ComponentStatus {
    private String componentName;
    private int intervalKm;
    private int lastServicedKm;
    private int nextDueKm;
    private int remainingKm;
    private boolean isOverdue;

    // Generate Getters and Setters for all of these!
    public String getComponentName() { return componentName; }
    public void setComponentName(String componentName) { this.componentName = componentName; }
    public int getIntervalKm() { return intervalKm; }
    public void setIntervalKm(int intervalKm) { this.intervalKm = intervalKm; }
    public int getLastServicedKm() { return lastServicedKm; }
    public void setLastServicedKm(int lastServicedKm) { this.lastServicedKm = lastServicedKm; }
    public int getNextDueKm() { return nextDueKm; }
    public void setNextDueKm(int nextDueKm) { this.nextDueKm = nextDueKm; }
    public int getRemainingKm() { return remainingKm; }
    public void setRemainingKm(int remainingKm) { this.remainingKm = remainingKm; }
    public boolean isOverdue() { return isOverdue; }
    public void setOverdue(boolean overdue) { isOverdue = overdue; }
}