package vehicle_api;

import jakarta.persistence.*;

@Entity
@Table(name = "components")
public class Component {

    @Id // id assigned by postgres
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; 
    private String category;
    
    // Getters Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public Integer getExpectedLifespanKm() {
        return expectedLifespanKm;
    }
    public void setExpectedLifespanKm(Integer expectedLifespanKm) {
        this.expectedLifespanKm = expectedLifespanKm;
    }
    public Integer getExpectedLifespanMonths() {
        return expectedLifespanMonths;
    }
    public void setExpectedLifespanMonths(Integer expectedLifespanMonths) {
        this.expectedLifespanMonths = expectedLifespanMonths;
    }
    
}