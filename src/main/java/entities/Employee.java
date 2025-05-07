package entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToMany
    @JoinTable(
            name="is_responsible_for",
            joinColumns = @JoinColumn(name="employee"),
            inverseJoinColumns = @JoinColumn(name="service")

    )
    private List<Service> services;


    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "phone", nullable = false, length = 13)
    private String phone;

    @Column(name = "role", nullable = false, length = Integer.MAX_VALUE)
    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}