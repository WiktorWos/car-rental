package com.springboottest.carrental.customer.search;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class SearchedCustomerInput {
    @NotNull
    private String searchByLastNameOrDrivingLicence;

    public SearchedCustomerInput() {
    }

    public String getSearchByLastNameOrDrivingLicence() {
        return searchByLastNameOrDrivingLicence;
    }

    public void setSearchByLastNameOrDrivingLicence(String searchByLastNameOrDrivingLicence) {
        this.searchByLastNameOrDrivingLicence = searchByLastNameOrDrivingLicence;
    }
}
