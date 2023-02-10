package com.schedule.mapper;

import com.schedule.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    @Select("SELECT * from employee")
    List<Employee> getAllEmployee();

    @Select("SELECT * from employee where employeeId = #{employeeId}")
    Employee getEmployeeById(String employeeId);

    @Select("select employeeId,username,root from employee where username=#{username} and password = #{password}")
    Employee login(String username, String password);

}
