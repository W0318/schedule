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

    /**
     * @author 吴佳丽
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    public List<Employee> getAllEmployee(String storeId) {
        return employeeMapper.getAllEmployee(storeId);
    }

    public Employee getEmployeeById(String employeeId) {
        return employeeMapper.getEmployeeById(employeeId);
    }

    public List<Employee> getEmployees(String employeeIds) {
        return employeeMapper.getEmployees(employeeIds);
    }


    /**
     * @author 朱凯
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    public Employee login(String a, String b) {
        return employeeMapper.login(a, b);
    }
}