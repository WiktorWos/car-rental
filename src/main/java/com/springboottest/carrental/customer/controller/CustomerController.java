package com.springboottest.carrental.customer.controller;

import com.springboottest.carrental.customer.entity.Customer;
import com.springboottest.carrental.customer.service.CustomerService;
import com.springboottest.carrental.initbinder.StringTrimmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/customer")
public class CustomerController {
    private CustomerService customerService;
    private StringTrimmer stringTrimmer;

    @Autowired
    public CustomerController(CustomerService customerService, StringTrimmer stringTrimmer) {
        this.customerService = customerService;
        this.stringTrimmer = stringTrimmer;
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

    @PostMapping("/save")
    public String saveCustomer(@Valid @ModelAttribute Customer customer, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "customer-form";
        }
        customerService.save(customer);
        return "redirect:/customer/findAll";
    }

    @GetMapping("/update")
    public String updateCustomer(@RequestParam Long id, Model model) {
        Customer customer = customerService.getById(id);
        model.addAttribute("customer", customer);
        return "customer-form";
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam Long id) {
        customerService.deleteById(id);
        return "redirect:/customer/findAll";
    }
}
