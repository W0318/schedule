package com.schedule.service;

import com.schedule.entity.Employee;
import com.schedule.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    public List<Employee> getAllEmployee(){
        return employeeMapper.getAllEmployee();
    }

    public Employee getEmployeeById(String employeeId){
        return employeeMapper.getEmployeeById(employeeId);
    }
}
