package com.springboottest.carrental.customer.search;

import com.springboottest.carrental.validation.SearchedCustomerNotFound;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class SearchedCustomerInput {
    @NotNull
    @SearchedCustomerNotFound
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
