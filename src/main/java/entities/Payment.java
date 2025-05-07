package entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "card", nullable = false, precision = 10, scale = 2)
    private BigDecimal card;

    @Column(name = "cash", nullable = false, precision = 10, scale = 2)
    private BigDecimal cash;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id")
    private entities.Rental rental;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getCard() {
        return card;
    }

    public void setCard(BigDecimal card) {
        this.card = card;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public entities.Rental getRental() {
        return rental;
    }

    public void setRental(entities.Rental rental) {
        this.rental = rental;
    }

}