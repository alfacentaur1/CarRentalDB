package entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "service")
public class Service {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToMany(mappedBy = "services")
    private List<Employee> employees;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "service_date", nullable = false)
    private LocalDate serviceDate;

    @Column(name = "type", nullable = false, length = Integer.MAX_VALUE)
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}