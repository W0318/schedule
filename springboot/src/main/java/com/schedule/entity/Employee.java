package com.schedule.entity;

import com.schedule.algorithm.Chromosome;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Employee {
    private String employeeId;
    private String storeId;
    private String employeeName;
    private String email;
    private String position;
    private int phone;
    private int root;

    private ArrayList<Integer> preference;   //偏好数值化0-39
    private Chromosome chromo;
    private int countDay = 0;
}
