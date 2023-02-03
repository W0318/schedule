package com.example.springboot.bean.services;

import com.example.springboot.bean.bean.User;

/**
 * @Author: yy
 * @Date: 2019/12/31 16:37
 * @Version 1.0
 */
public interface UserServices {
    User register(String name, Integer age, String pwd);
    User login(String name, String pwd);
}
