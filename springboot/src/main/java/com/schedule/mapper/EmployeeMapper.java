package com.schedule.mapper;

import com.schedule.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    /**
     * @author 吴佳丽
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @Select("SELECT * from employee WHERE storeId = #{storeId}")
    List<Employee> getAllEmployee(String storeId);

    @Select("SELECT * from employee where employeeId = #{employeeId}")
    Employee getEmployeeById(String employeeId);

    @Select("SELECT * FROM employee WHERE employeeId in (${employeeIds})")
    List<Employee> getEmployees(String employeeIds);


    /**
     * @author 朱凯
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @Select("select employeeId,username,root from employee where username=#{username} and password = #{password}")
    Employee login(String username, String password);
}