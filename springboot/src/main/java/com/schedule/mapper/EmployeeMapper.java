package com.schedule.mapper;

import com.schedule.entity.Employee;
import com.schedule.entity.Store;
import org.apache.ibatis.annotations.Insert;
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
    @Select("select * from employee where employeeId=#{username} and password = #{password}")
    Employee login(String username, String password);

    /**
     * @author 张卓童
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @Select("SELECT * FROM employee")
    List<Employee> getEmployee();

    //查找某门店下的所有员工
    @Select("SELECT *\n" +
            "FROM employee\n" +
            "WHERE storeId = (SELECT storeId FROM store WHERE storeName = #{storeName})")
    List<Employee> getEmployeeByStoreId(String storeName);

    //寻找是否已有该员工id
    @Select("SELECT employeeId FROM employee WHERE employeeId = #{employeeId}")
    List<Employee> getFindIfExistEmployee(String employeeId);

    @Select("DELETE FROM employee WHERE storeId = #{storeId}")
    void deleteEmployeeStateByStoreId(String storeId);

    @Select("select * \n" +
            "FROM employee \n" +
            "WHERE employeeName like concat('%', #{employeeName},'%')")
    List<Employee> getEmployeeByEmployeeName(String employeeName);

    @Select("select * \n" +
            "FROM employee \n" +
            "WHERE position like concat('%', #{position},'%')")
    List<Employee> getEmployeeByPosition(String position);

    @Select("select * \n" +
            "FROM employee \n" +
            "WHERE employeeName like concat('%', #{employeeName},'%')\n" +
            "and position like concat('%', #{position},'%')")
    List<Employee> getEmployeeByEmployeeNameAndPosition(String employeeName, String position);

    @Select("UPDATE employee \n" +
            "SET employeeName = #{employeeName}, storeId = #{storeId}, email = #{email}, position = #{position}, phone = #{phone}\n" +
            "WHERE employeeId = #{employeeId}")
    void updateEmployee(String employeeId, String employeeName, String storeId, String email, String position, String phone);

    @Select("UPDATE employee SET state = 1 WHERE employeeId = #{employeeId} ")
    void updateEmployeeStateZero(String employeeId);

    @Select("UPDATE employee SET state = 0 WHERE employeeId = #{employeeId} ")
    void updateEmployeeStateOne(String employeeId);

    @Select("DELETE FROM employee WHERE employeeId = #{employeeId}")
    void deleteEmployee(String employeeId);

    @Insert("INSERT INTO employee VALUES (#{employeeId}, #{employeeName}, #{storeId}, #{email}, #{position}, #{phone}, 2, #{employeeId}, #{employeeId}, 1)")
    void insertEmployee(String employeeId, String employeeName, String storeId, String email, String position, String phone);
}