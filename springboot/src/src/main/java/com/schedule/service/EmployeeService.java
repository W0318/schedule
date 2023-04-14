package com.schedule.service;

import com.schedule.entity.Employee;
import com.schedule.entity.Store;
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

    /**
     * @author 张卓童
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    public List<Employee> getEmployee() {
        return employeeMapper.getEmployee();
    }

    //查找某门店下的所有员工
    public List<Employee> getEmployeeByStoreId(String storeName) {
        return employeeMapper.getEmployeeByStoreId(storeName);
    }

    //寻找是否已有该员工id
    public List<Employee> getFindIfExistEmployee(String employeeId) {
        return employeeMapper.getFindIfExistEmployee(employeeId);
    }

    public void deleteEmployeeStateByStoreId(String storeId) {
        employeeMapper.deleteEmployeeStateByStoreId(storeId);
    }

    public List<Employee> getEmployeeByEmployeeName(String employeeName) {
        return employeeMapper.getEmployeeByEmployeeName(employeeName);
    }

    public List<Employee> getEmployeeByPosition(String position) {
        return employeeMapper.getEmployeeByPosition(position);
    }

    public List<Employee> getEmployeeByEmployeeNameAndPosition(String employeeName, String position) {
        return employeeMapper.getEmployeeByEmployeeNameAndPosition(employeeName, position);
    }

    public void updateEmployee(String employeeId, String employeeName, String storeId, String email, String position, String phone) {
        employeeMapper.updateEmployee(employeeId, employeeName, storeId, email, position, phone);
    }

    public void updateEmployeeStateOne(String employeeId) {
        employeeMapper.updateEmployeeStateOne(employeeId);
    }

    public void updateEmployeeStateZero(String employeeId) {
        employeeMapper.updateEmployeeStateZero(employeeId);
    }

    public void deleteEmployee(String employeeId) {
        employeeMapper.deleteEmployee(employeeId);
    }

    public void insertEmployee(String employeeId, String employeeName, String storeId, String email, String position, String phone) {
        employeeMapper.insertEmployee(employeeId, employeeName, storeId, email, position, phone);
    }
}