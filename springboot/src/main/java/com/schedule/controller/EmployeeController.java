package com.schedule.controller;

import com.alibaba.fastjson.JSON;
import com.schedule.entity.Employee;
import com.schedule.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    /**
     * @author 吴佳丽
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @GetMapping("/all/{storeId}")
    public List<Employee> getAllEmployee(@PathVariable("storeId") String storeId) {
        return employeeService.getAllEmployee(storeId);
    }

    @GetMapping("/selectById/{employeeId}")
    public Employee getEmployeeById(@PathVariable("employeeId") String employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    /**
     * @author 朱凯
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @PostMapping("/login")
    @ResponseBody
    // 注意login函数接收数据的方式为@RequestBody,而register函数接收数据的方式为@RequestParam，这与前端axios发送数据的方式不同有关。稍后详解
    public String login2(@RequestBody Map map) {
        String flag="error";
        Employee user = employeeService.login(map.get("username").toString(), map.get("password").toString());
        HashMap<String, Object> res = new HashMap<>();
        if(user!=null){
            flag="ok";
        }
        res.put("flag",flag);
        res.put("employee",user);
        System.out.println(res);
        String res_json = JSON.toJSONString(res);

        System.out.println(res_json);
        return  res_json;
//        if (user != null) {
//            int root = user.getRoot();
//            return root;
//        } else {
//            return 0;
//        }

    }

    /**
     * @author 张卓童
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @GetMapping("/allEmployee")
    public List<Employee> getEmployee() {
        return employeeService.getEmployee();
    }

    //查找某门店下的所有员工
    @GetMapping("/getEmployeeByStoreId/{storeName}")
    public List<Employee> getEmployeeByStoreId(@PathVariable("storeName") String storeName) {
        return employeeService.getEmployeeByStoreId(storeName);
    }

    //寻找是否已有该员工id
    @GetMapping("/getFindIfExistEmployee/{employeeId}")
    public List<Employee> getFindIfExistEmployee(@PathVariable("employeeId") String employeeId) {
        return employeeService.getFindIfExistEmployee(employeeId);
    }

    @PostMapping("/deleteEmployeeStateByStoreId")
    @ResponseBody
    public void banEmployeeStateByStoreId(@RequestBody Map map) {
        employeeService.deleteEmployeeStateByStoreId(map.get("storeId").toString());
    }

    @GetMapping("/getEmployeeByEmployeeName/{employeeName}")
    public List<Employee> getEmployeeByEmployeeName(@PathVariable("employeeName") String employeeName) {
        return employeeService.getEmployeeByEmployeeName(employeeName);
    }

    @GetMapping("/getEmployeeByPosition/{position}")
    public List<Employee> getEmployeeByPosition(@PathVariable("position") String position) {
        return employeeService.getEmployeeByPosition(position);
    }

    @GetMapping("/getEmployeeByEmployeeNameAndPosition/{employeeName}/{position}")
    public List<Employee> getEmployeeByEmployeeNameAndPosition(@PathVariable("employeeName") String employeeName, @PathVariable("position") String position) {
        return employeeService.getEmployeeByEmployeeNameAndPosition(employeeName, position);
    }

    @PostMapping("/updateEmployee")
    @ResponseBody
    public void updateEmployee(@RequestBody Map map) {
        employeeService.updateEmployee(map.get("employeeId").toString(), map.get("employeeName").toString(), map.get("storeId").toString(), map.get("email").toString(), map.get("position").toString(), map.get("phone").toString());
    }

    @PostMapping("/updateEmployeeStateOne")
    @ResponseBody
    public void updateEmployeeStateOne(@RequestBody Map map) {
        employeeService.updateEmployeeStateOne(map.get("employeeId").toString());
    }

    @PostMapping("/updateEmployeeStateZero")
    @ResponseBody
    public void updateEmployeeStateZero(@RequestBody Map map) {
        employeeService.updateEmployeeStateZero(map.get("employeeId").toString());
    }

    @PostMapping("/deleteEmployee")
    @ResponseBody
    public void deleteEmployee(@RequestBody Map map) {
        employeeService.deleteEmployee(map.get("employeeId").toString());
    }

    @PostMapping("/insertEmployee")
    @ResponseBody
    public void insertEmployee(@RequestBody Map map) {
        System.out.println(map.get("storeId").toString());
        System.out.println(map.get("employeeId").toString());
        employeeService.insertEmployee(map.get("employeeId").toString(), map.get("employeeName").toString(), map.get("storeId").toString(), map.get("email").toString(), map.get("position").toString(), map.get("phone").toString());
    }
}
