package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "birth_number")
public class BirthNumber {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "employee_id")
    private entities.Employee employee;

    @Column(name = "birth_number", nullable = false, length = 11)
    private String birthNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public entities.Employee getEmployee() {
        return employee;
    }

    public void setEmployee(entities.Employee employee) {
        this.employee = employee;
    }

    public String getBirthNumber() {
        return birthNumber;
    }

    public void setBirthNumber(String birthNumber) {
        this.birthNumber = birthNumber;
    }

}