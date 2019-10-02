package com.springboottest.carrental.customer.controller;

import com.springboottest.carrental.customer.entity.Customer;
import com.springboottest.carrental.customer.search.SearchedCustomerInput;
import com.springboottest.carrental.customer.jackson.CustomerPojoToJson;
import com.springboottest.carrental.customer.service.CustomerService;
import com.springboottest.carrental.initbinder.StringTrimmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/customer")
public class CustomerController {
    private CustomerService customerService;
    private StringTrimmer stringTrimmer;
    private CustomerPojoToJson customerPojoToJson;

    @Autowired
    public CustomerController(CustomerService customerService, StringTrimmer stringTrimmer,
                              CustomerPojoToJson customerPojoToJson) {
        this.customerService = customerService;
        this.stringTrimmer = stringTrimmer;
        this.customerPojoToJson = customerPojoToJson;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        stringTrimmer.initBinder(webDataBinder);
    }

    @GetMapping("/findAll")
    public String findAll(Model model) {
        model.addAttribute("customers",customerService.findAll());
        return "customer-list";
    }

    @GetMapping("/addCustomer")
    public String addCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer-form";
    }

    @PostMapping("/confirm")
    public String confirmCustomer(@Valid @ModelAttribute Customer customer, BindingResult bindingResult) {

        if(isCustomerAlreadyAdded(customer)) {
            performTransactionWithExistingCustomer(customer);
            return "redirect:/transaction/addTransaction";
        }

        if(bindingResult.hasErrors()) {
            return "customer-form";
        }

        performTransactionWithNewCustomer(customer);
        return "redirect:/transaction/addTransaction";
    }

    private boolean isCustomerAlreadyAdded(Customer customer) {
        return customer.getId() != null;
    }

    private void performTransactionWithExistingCustomer(Customer customer) {
        customer = customerService.getById(customer.getId());
        customerPojoToJson.convertToJson(customer);
    }

    private void performTransactionWithNewCustomer(Customer customer){
        customer.setOnUpdate(false);
        customerPojoToJson.convertToJson(customer);
    }

    @PostMapping("/save")
    public String saveCustomer(@Valid @ModelAttribute Customer customer, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "customer-update-form";
        }

        saveUpdatedCustomer(customer);
        return "redirect:/customer/findAll";
    }

    private void saveUpdatedCustomer(Customer customer) {
        customer.setOnUpdate(false);
        customerService.save(customer);
    }

    @GetMapping("/update")
    public String updateCustomer(@RequestParam Long id, Model model) {
        Customer customer = performUpdateOnCustomer(id);
        model.addAttribute("customer", customer);
        return "customer-update-form";
    }

    private Customer performUpdateOnCustomer(Long id) {
        Customer customer = customerService.getById(id);
        customer.setOnUpdate(true);
        customerService.save(customer);
        return customer;
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam Long id) {
        customerService.deleteById(id);
        return "redirect:/customer/findAll";
    }

    @GetMapping("/select")
    public String selectCustomer(Model model) {
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("customer", new Customer());
        model.addAttribute("searchedCustomer", new SearchedCustomerInput());
        return "customer-select";
    }

    @GetMapping("/search")
    public String searchCustomer(@Valid @ModelAttribute SearchedCustomerInput searchedCustomerInput, Model model) {
        String searchInput = searchedCustomerInput.getSearchByLastNameOrDrivingLicence();
        List <Customer> customersFoundByDrivingLicence = customerService.isDrivingLicenceInUse(searchInput);
        int foundCustomerIndex = 0;

        if(isCustomerFound(customersFoundByDrivingLicence)) {
            model.addAttribute("customers", customersFoundByDrivingLicence.get(foundCustomerIndex));
            return "customer-list";
        }

        List <Customer> customersFoundByLastName = customerService.searchCustomerByName(searchInput);
        if(isCustomerFound(customersFoundByLastName)){
            model.addAttribute("customers", customersFoundByLastName.get(foundCustomerIndex));
            return "customer-list";
        }

        return "redirect:/customer/select";
    }

    private boolean isCustomerFound(List<Customer> customers) {
        return !customers.isEmpty();
    }


}
