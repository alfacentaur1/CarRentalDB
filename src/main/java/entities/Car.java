package entities;

import jakarta.persistence.*;


@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;


    @Column(name = "plate_number", nullable = false, length = 7)
    private String plateNumber;

    @Column(name = "type", nullable = false, length = Integer.MAX_VALUE)
    private String type;

    @Column(name = "mileage")
    private Integer mileage;


    @Column(name = "is_borrowed")
    private Boolean isBorrowed;

    @Column(name = "brand", nullable = false, length = Integer.MAX_VALUE)
    private String brand;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Boolean getIsBorrowed() {
        return isBorrowed;
    }

    public void setIsBorrowed(Boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + type + '\'' +
                ", mileage=" + mileage +
                '}';
    }

}