package dev.kaldiroglu.hexagonal.ayvalikbank.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "password_history")
public class PasswordHistoryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerJpaEntity customer;

    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    /** 0 = most recent previous, 1 = second most recent, etc. */
    @Column(name = "position", nullable = false)
    private int position;

    public PasswordHistoryJpaEntity() {}

    public PasswordHistoryJpaEntity(CustomerJpaEntity customer, String hashedPassword, int position) {
        this.customer = customer;
        this.hashedPassword = hashedPassword;
        this.position = position;
    }

    public UUID getId() { return id; }
    public CustomerJpaEntity getCustomer() { return customer; }
    public void setCustomer(CustomerJpaEntity customer) { this.customer = customer; }
    public String getHashedPassword() { return hashedPassword; }
    public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }
    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
}
