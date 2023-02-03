package com.example.springboot.bean.bean;

/**
 * @Author: yy
 * @Date: 2019/12/31 16:33
 * @Version 1.0
 */

public class User {
    //映射数据库字段
    private Long id;
    private String name;
    private Integer age;
    private String pwd;
    //定义一个msg，发送消息
    private String msg;
    //判断是否成功
    private boolean success;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
