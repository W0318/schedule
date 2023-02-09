package com.schedule.controller;

import com.schedule.entity.Employee;
import com.schedule.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/selectAll")
    public List<Employee> getAllEmployee(){
        return employeeService.getAllEmployee();
    }

    @GetMapping("/selectById/{employeeId}")
    public Employee getEmployeeById(@PathVariable("employeeId") String employeeId){
        return employeeService.getEmployeeById(employeeId);
    }
}
