package com.leyvadev.iesjrecontrol.logincontrol.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "radcheck")
public class Radcheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Size(max = 64)
    @NotNull
    @Column(name = "username", nullable = false, length = 64)
    private String username;

    @Size(max = 64)
    @NotNull
    @Column(name = "attribute", nullable = false, length = 64)
    private String attribute;

    @Size(max = 2)
    @NotNull
    @Column(name = "op", nullable = false, length = 2,columnDefinition = "char")
    private String op;

    @Size(max = 253)
    @NotNull
    @Column(name = "value", nullable = false, length = 253)
    private String value;

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

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}