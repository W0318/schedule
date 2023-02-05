package com.schedule.controller;

import com.schedule.entity.Employee;
import com.schedule.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/ll")
    public List<Employee> getAllEmployee(){
        return employeeService.getAllEmployee();
    }
@PostMapping("/login")
@ResponseBody
// 注意login函数接收数据的方式为@RequestBody,而register函数接收数据的方式为@RequestParam，这与前端axios发送数据的方式不同有关。稍后详解
public String login2(@RequestBody Map map){
    Employee user = employeeService.login(map.get("username").toString(),map.get("password").toString());
    if(user != null){
        return "index";
    }
    else {
        return "login";
    }
}
//    @GetMapping("/selectById/{employeeId}")
//    public Employee getEmployeeById(@PathVariable("employeeId") String employeeId){
//        return employeeService.getEmployeeById(employeeId);
//    }
}
