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

    public List<Employee> getAllEmployee(String storeId) {
        return employeeMapper.getAllEmployee(storeId);
    }

    public Employee getEmployeeById(String employeeId) {
        return employeeMapper.getEmployeeById(employeeId);
    }

    public Employee login(String a, String b) {
        return employeeMapper.login(a, b);
    }

    public List<Employee> getWeekEmployees(String employeeIds) {
        return employeeMapper.getWeekEmployees(employeeIds);
    }
}