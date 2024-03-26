package ru.uvuv643.business_logic.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "subscriptions")
public class Subscription {


    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private double coinsLeft;

    @Column
    @CurrentTimestamp
    private Timestamp createdAt;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public double getCoinsLeft() {
        return coinsLeft;
    }

    public void setCoinsLeft(double coinsLeft) {
        this.coinsLeft = coinsLeft;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
