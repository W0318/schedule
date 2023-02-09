package com.schedule.mapper;

import com.schedule.entity.Employee;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface EmployeeMapper {
    @Select("SELECT * from employee")
    public List<Employee> getAllEmployee();

    @Select("SELECT * from employee where employeeId = #{employeeId}")
    public Employee getEmployeeById(String employeeId);
}
