package com.pichincha.prueba.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;
    protected String name;
    protected String gender;
    protected Integer age;
    protected String identification;
    protected String address;
    protected String phone;
}
