package com.schedule.mapper;

import com.schedule.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    @Select("SELECT * from employee WHERE storeId = #{storeId}")
    List<Employee> getAllEmployee(String storeId);

    @Select("SELECT * from employee where employeeId = #{employeeId}")
    Employee getEmployeeById(String employeeId);

    @Select("select * from employee where employeeId=#{username} and password = #{password}")
    Employee login(String username, String password);

    @Select("SELECT * FROM employee WHERE employeeId in (${employeeIds})")
    List<Employee> getWeekEmployees(String employeeIds);
}

/**
 * 待解决bug记录
 * 1: 一个员工好像可以在多个店里工作
 * 2: 检查员工是否是选中店的员工
 * */