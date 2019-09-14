package com.springboottest.carrental.car.controller;

import com.springboottest.carrental.initbinder.StringTrimmer;
import com.springboottest.carrental.car.entity.Car;
import com.springboottest.carrental.car.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {
    private CarService carService;
    private StringTrimmer stringTrimmer;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        stringTrimmer.initBinder(webDataBinder);
    }

    @Autowired
    public CarController(CarService carService, StringTrimmer stringTrimmer) {
        this.carService = carService;
        this.stringTrimmer = stringTrimmer;
    }


    @GetMapping("/findAll")
    public String findAll(Model model){
        List<Car> cars = carService.findAll();
        model.addAttribute("cars",cars);
        return "car-list";
    }

    @GetMapping("/addCar")
    public String addCar(Model model) {
        Car car = new Car();
        model.addAttribute("car",car);
        return "car-form";
    }

    @PostMapping("/save")
    public String saveCar(@Valid @ModelAttribute Car car, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            return "car-form";
        } else {
            carService.save(car);
            return "redirect:/cars/findAll";
        }
    }

    @GetMapping("/delete")
    public String deleteCar(@RequestParam Long id) {
        carService.deleteById(id);
        return "redirect:/cars/findAll";
    }

    @GetMapping("/update")
    public String updateCar(@RequestParam Long id, Model model) {
        Car car = carService.getById(id);
        model.addAttribute("car",car);
        return "car-form";
    }

}
