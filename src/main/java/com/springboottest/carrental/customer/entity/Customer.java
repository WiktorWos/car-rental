package com.springboottest.carrental.customer.entity;

import com.springboottest.carrental.transaction.entity.Transaction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "experience")
    private int experience;

    @Column(name = "email")
    private String email;

    @Column(name = "driving_licence_number")
    private String drivingLicenceNumber;

    @OneToMany(mappedBy = "customer",
            cascade =  {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public Customer() {
    }

    public Customer(String firstName, String lastName, int experience, String email, String drivingLicenceNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.experience = experience;
        this.email = email;
        this.drivingLicenceNumber = drivingLicenceNumber;
    }

    public String getDrivingLicenceNumber() {
        return drivingLicenceNumber;
    }

    public void setDrivingLicenceNumber(String drivingLicenceNumber) {
        this.drivingLicenceNumber = drivingLicenceNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Transaction> getTransaction() {
        return transactions;
    }

    public void setTransaction(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", experience=" + experience +
                ", email='" + email + '\'' +
                ", drivingLicenceNumber='" + drivingLicenceNumber + '\'' +
                ", transaction=" + transactions +
                '}';
    }

    public void addTransaction(Transaction transaction) {
        if(transactions == null) {
            transactions = new ArrayList<>();
        }
        transactions.add(transaction);
        transaction.setCustomer(this);
    }
}
