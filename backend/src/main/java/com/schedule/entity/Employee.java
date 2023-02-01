package com.schedule.entity;

import lombok.Data;

@Data
public class Employee {
    private String employeeId;
    private String storeId;
    private String employeeName;
    private String email;
    private String position;
    private int phone;
    private int root;
}
