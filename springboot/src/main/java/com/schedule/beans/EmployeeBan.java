package com.schedule.beans;

import com.schedule.algorithm.Chromosome;
import lombok.Data;

import java.util.ArrayList;

@Data
public class EmployeeBan {
    private String employeeId;
    private String storeId;
    private String employeeName;
    private String email;
    private String position;
    private String phone;
    private int root;

}
