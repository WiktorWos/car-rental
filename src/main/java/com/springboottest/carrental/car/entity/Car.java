package com.springboottest.carrental.car.entity;



import com.springboottest.carrental.transaction.entity.Transaction;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Please enter the car name")
    @Size(min = 3, max = 40, message = "Min name size is 3, max is 40")
    @Column(name = "car_name")
    private String carName;

    @NotNull(message = "Please enter the mileage")
    @Max(value = 1000000, message = "Mileage must be less than 1000000")
    @Min(value = 0, message = "Mileage must be greater than 0")
    @Column(name = "car_mileage")
    private Integer carMileage;

    @Column(name = "car_production")
    @Min(value = 2010)
    @Max(value = 2019)
    @NotNull
    private int carProduction;

    @Column(name = "is_active")
    @NotNull
    private boolean active;

    @OneToOne(mappedBy = "car",
              cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
              fetch = FetchType.LAZY)
    private Transaction transaction;

    public Car(String carName, Integer carMileage, int carProduction, boolean isActive) {
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

    public Integer getCarMileage() {
        return carMileage;
    }

    public void setCarMileage(Integer carMileage) {
        this.carMileage = carMileage;
    }

    public int getCarProduction() {
        return carProduction;
    }

    public void setCarProduction(int carProduction) {
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
