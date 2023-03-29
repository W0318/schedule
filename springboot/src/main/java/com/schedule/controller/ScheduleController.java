package com.schedule.controller;

import com.schedule.entity.Employee;
import com.schedule.entity.Store;
import com.schedule.mapper.EmployeeMapper;
import com.schedule.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleMapper scheduleMapper;
    @PostMapping("/123")
    public List<Employee> queryEmployeeByStoreId(@RequestBody String str){
        System.out.println(str);
        List<Employee> storeemployees = scheduleMapper.queryEmployeeByStoreId(str);
        HashMap<String, Employee> employee_arr = Employee.employee_arrInit();
        for(Employee employee:storeemployees){
            employee_arr.put(employee.getEmployeeName(),employee);
        }
        System.out.println(employee_arr);

        Store store1 = new Store("1","张家界",100.0,employee_arr);
        int DAY1=1;//
        for(int day=1;day<=DAY1;day++)
        {
            store1.use_GA(day);//哪天
        }
        return storeemployees;
    }
}
