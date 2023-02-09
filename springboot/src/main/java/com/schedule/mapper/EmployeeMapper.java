package com.schedule.mapper;

import com.schedule.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {
//    @Select("select * from employee WHERE employeeId = '1' and password = '123'")
//    public List<Employee> getAllEmployee();
//
//    @Select("SELECT * from employee where employeeId = #{employeeId}")
//    public Employee getEmployeeById(String employeeId);

    @Select("select employeeId,username,root from employee where username=#{username} and password = #{password}")
    Employee login(String username,String password);

//    @Select("select email from User where email = #{email}")
//    Employee findemail(String email);
//    @Select("select username from User where username = #{username}")
//    Employee findusername(String username);
}
