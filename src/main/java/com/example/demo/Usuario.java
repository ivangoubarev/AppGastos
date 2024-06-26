package com.example.demo;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Gasto> gastos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Presupuesto> presupuestos;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        double balance = 0;
        for(Presupuesto p : presupuestos) {
            balance += p.getBalance();
        }
        return balance;
    }

    public void recalcularBalances() {
        for(Presupuesto p : presupuestos) {
            p.setBalance(p.getTotal());
        }
    }
}