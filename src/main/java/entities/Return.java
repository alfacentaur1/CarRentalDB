package entities;

import jakarta.persistence.*;
//operation_id
@Entity
@Table(name = "return")
@DiscriminatorValue("RETURN")
@PrimaryKeyJoinColumn(
        name="operation_id"
)
public class Return extends Operation {
    @Id
    @Column(name = "operation_id", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "operation_id", nullable = false)
    private Operation operation;

    @Column(name = "status", nullable = false, length = Integer.MAX_VALUE)
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}