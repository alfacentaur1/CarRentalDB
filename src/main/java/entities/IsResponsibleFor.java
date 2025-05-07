package entities;

import jakarta.persistence.*;


@Entity
@Table(name = "is_responsible_for")
public class IsResponsibleFor {
    @Id

    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "service_id")
    private entities.Service service;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public entities.Service getService() {
        return service;
    }

    public void setService(entities.Service service) {
        this.service = service;
    }

}