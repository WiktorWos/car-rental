package com.springboottest.carrental.customer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springboottest.carrental.transaction.entity.Transaction;
import com.springboottest.carrental.validation.ExtendedEmailValidator;
import com.springboottest.carrental.validation.UniqueDrivingLicence;
import com.springboottest.carrental.validation.UniqueEmail;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Indexed
@Table(name = "customers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "transactions"})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Please enter first name")
    @Size(min = 3, max = 20, message = "Min name size is 3, max is 20")
    @Column(name = "first_name")
    @Field
    private String firstName;

    @NotNull(message = "Please enter last name")
    @Size(min = 3, max = 20, message = "Min name size is 3, max is 20")
    @Column(name = "last_name")
    @Field
    private String lastName;

    @Min(value = 1, message = "Experience must be greater or equal to 1 year")
    @Column(name = "experience")
    private int experience;

    @Column(name = "email")
    @NotNull(message = "Please enter email")
    @ExtendedEmailValidator
    @UniqueEmail
    private String email;

    @Column(name = "driving_licence_number")
    @NotNull(message = "Please enter driving licence number")
    @Pattern(regexp = "[a-z0-9]{4}\\/\\d{2}\\/\\d{4}", message = "Wrong pattern, should be: xx/yy/dddd, where x are " +
            "letters and digits, and y, d are digits")
    @UniqueDrivingLicence
    private String drivingLicenceNumber;

    @OneToMany(mappedBy = "customer",
            cascade =  {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    @Column(name = "on_update")
    private boolean onUpdate;

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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public boolean getOnUpdate() {
        return onUpdate;
    }

    public void setOnUpdate(boolean onUpdate) {
        this.onUpdate = onUpdate;
    }

    @Override
    public String toString() {
        return  firstName + " " +
                lastName +
                ", email: " + email +
                ", driving licence number: " + drivingLicenceNumber;
    }

    public void addTransaction(Transaction transaction) {
        if(transactions.isEmpty()) {
            transactions = new ArrayList<>();
        }

        if(transactions.contains(transaction)) {
            return;
        }

        transactions.add(transaction);
        transaction.setCustomer(this);
    }



    public void removeTransaction(Transaction transaction) {
        if (!transactions.contains(transaction))
            return;

        transactions.remove(transaction);

        transaction.setCustomer(null);
    }
}
