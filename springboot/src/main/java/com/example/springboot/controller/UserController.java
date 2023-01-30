package com.example.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.example.springboot.common.Result;
import com.example.springboot.entity.EmployeeId;
import com.example.springboot.mapper.EmployeeIdMapper;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenchen
 * @since 2021-12-10
 */
@RestController
@RequestMapping("/zk")
public class UserController {
    @Resource
    EmployeeIdMapper employeeIdMapper;
    @PostMapping
    public Result save(@RequestBody EmployeeId employeeId){
        employeeIdMapper.insert(employeeId);
        return Result.success();
    }
//    @PostMapping("/login")
//    public Result<?> login(@RequestBody EmployeeId employeeId){
//        // 使用昵称登录判断
//        EmployeeId one = userService.getOne(Wrappers.<EmployeeId>lambdaQuery()
//                .eq(User::getNickName, user.getName()).eq(User::getPassword, user.getPassword()));
//        if (!ObjectUtils.isEmpty(one)){
//            return Result.success(one);
//        }else {
//            return Result.error("-1","用户名或密码错误");
//        }
//    }


}

