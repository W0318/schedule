package com.schedule.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Employee implements Serializable {
    private String employeeId;
    private String storeId;
    private String employeeName;
    private String email;
    private String position;
    private int phone;
    private int root;
    private int state;
    private String username;
    private String password;
}
