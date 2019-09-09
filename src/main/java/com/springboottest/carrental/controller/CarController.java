package com.springboottest.carrental.controller;

import com.springboottest.carrental.entity.Car;
import com.springboottest.carrental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {
    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/findAll")
    public String findAll(Model model){
        List<Car> cars = carService.findAll();
        model.addAttribute("cars",cars);
        return "car-list";
    }
}
