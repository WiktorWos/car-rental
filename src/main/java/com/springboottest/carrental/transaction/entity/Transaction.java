package com.springboottest.carrental.transaction.entity;

import com.springboottest.carrental.customer.entity.Customer;
import org.springframework.format.annotation.DateTimeFormat;
import com.springboottest.carrental.car.entity.Car;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(name = "start_mileage")
    private int startMileage;

    @Column(name = "price")
    private double price;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
              fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @JoinColumn(name = "customer_id")
    @ManyToOne(cascade =  {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private Customer customer;

    public Transaction() {
    }

    public Transaction(LocalDate startDate, int startMileage, double price) {
        this.startDate = startDate;
        this.startMileage = startMileage;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getStartMileage() {
        return startMileage;
    }

    public void setStartMileage(int startMileage) {
        this.startMileage = startMileage;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", startMileage=" + startMileage +
                ", price=" + price +
                ", car=" + car +
                ", customer=" + customer +
                '}';
    }
}
