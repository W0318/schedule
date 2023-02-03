package com.example.springboot.bean.services.impl;

import com.example.springboot.bean.bean.User;
import com.example.springboot.bean.dao.UserDao;
import com.example.springboot.bean.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yy
 * @Date: 2019/12/31 16:37
 * @Version 1.0
 */
@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private UserDao userDao;

    /**
     * 注册
     * @param
     * @return
     */
    @Override
    public User register(String name, Integer age, String pwd){

        User existUser=userDao.findUserByName(name);

        User result = new User();

        try{
            if(existUser !=null){
                result.setMsg("用户名已存在！");
                result.setSuccess(false);
                result.setId(existUser.getId());
                result.setAge(existUser.getAge());
                result.setName(existUser.getName());
                result.setPwd(existUser.getPwd());
            }
            else{
                userDao.register(name, age, pwd);
                result.setMsg("注册成功！");
                result.setSuccess(true);

                //注册后再使用该名字查询用户信息
                User currentRegUser = userDao.findUserByName(name);

                result.setId(currentRegUser.getId());
                result.setAge(currentRegUser.getAge());
                result.setName(currentRegUser.getName());
                result.setPwd(currentRegUser.getPwd());
            }
        } catch (Exception e){
            e.printStackTrace();
            result.setMsg(e.getMessage());
        }

        return result;
    }

    @Override
    public User login(String name, String pwd){
        User result = new User();
        result.setSuccess(false);

        Long userId = userDao.login(name,pwd);
        try{
            if(userId !=null){
                result.setMsg("登录成功！");
                result.setSuccess(true);

                //根据userId查询出当前登录用户
                User currentLoginUser = userDao.findUserById(userId);

                result.setId(userId);
                result.setAge(currentLoginUser.getAge());
                result.setName(currentLoginUser.getName());
                result.setPwd(currentLoginUser.getPwd());
            }else{
                result.setMsg("用户名或密码不正确！");
                result.setSuccess(false);
            }
        } catch (Exception e){
            e.printStackTrace();
            result.setMsg(e.getMessage());
        }

        return result;
    }

}
