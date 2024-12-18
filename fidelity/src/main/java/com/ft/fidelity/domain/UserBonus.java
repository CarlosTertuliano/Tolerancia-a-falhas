package com.ft.fidelity.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "user_bonus")
public class UserBonus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;

    private int bonus;

    public UserBonus(int userId, int bonus) {
        this.userId = userId;
        this.bonus = bonus;
    }

    public UserBonus() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}
