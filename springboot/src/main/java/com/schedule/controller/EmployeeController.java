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

    /**
     * @author 吴佳丽
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @GetMapping("/all/{storeId}")
    public List<Employee> getAllEmployee(@PathVariable("storeId") String storeId) {
        return employeeService.getAllEmployee(storeId);
    }

    @GetMapping("/selectById/{employeeId}")
    public Employee getEmployeeById(@PathVariable("employeeId") String employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }


    /**
     * @author 朱凯
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @PostMapping("/login")
    @ResponseBody
    // 注意login函数接收数据的方式为@RequestBody,而register函数接收数据的方式为@RequestParam，这与前端axios发送数据的方式不同有关。稍后详解
    public int login2(@RequestBody Map map) {
        Employee user = employeeService.login(map.get("username").toString(), map.get("password").toString());
        if (user != null) {
            int root = user.getRoot();
            return root;
        } else {
            return 0;
        }
    }
}
