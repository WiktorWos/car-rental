package com.springboottest.carrental.entity;



import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "car_name")
    private String carName;

    @Column(name = "car_mileage")
    private int carMileage;

    @Column(name = "car_production")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate carProduction;

    @Column(name = "is_active")
    private boolean active;

    @OneToOne(mappedBy = "car",
              cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
              fetch = FetchType.LAZY)
    private Transaction transaction;

    public Car(String carName, int carMileage, LocalDate carProduction, boolean isActive) {
        this.carName = carName;
        this.carMileage = carMileage;
        this.carProduction = carProduction;
        this.active = isActive;
    }

    public Car() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getCarMileage() {
        return carMileage;
    }

    public void setCarMileage(int carMileage) {
        this.carMileage = carMileage;
    }

    public LocalDate getCarProduction() {
        return carProduction;
    }

    public void setCarProduction(LocalDate carProduction) {
        this.carProduction = carProduction;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", carName='" + carName + '\'' +
                ", carMileage=" + carMileage +
                ", carProduction=" + carProduction +
                ", active=" + active +
                '}';
    }
}
