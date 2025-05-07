package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "rental")
@DiscriminatorValue("RENTAL")
@PrimaryKeyJoinColumn(
        name="operation_id"
)
public class Rental extends Operation {
    @Id
    @Column(name = "operation_id", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "operation_id", nullable = false)
    private Operation operation;

    @Column(name = "fine_per_day", nullable = false)
    private Integer finePerDay;

    @Column(name = "price_per_day", nullable = false)
    private Integer pricePerDay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Integer getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(Integer finePerDay) {
        this.finePerDay = finePerDay;
    }

    public Integer getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Integer pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

}