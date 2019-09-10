package com.springboottest.carrental.controller;

import com.springboottest.carrental.entity.Car;
import com.springboottest.carrental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {
    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(LocalDate.class, editor);
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
    public String saveCar(@ModelAttribute Car car, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            return "car-form";
        }
        carService.save(car);
        return "redirect:/cars/findAll";
    }

}
