package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("employeeId")
@Data
public class EmployeeId {
    @TableId(type = IdType.AUTO)
    private String employeeId;
    private String storeId;
    private String employeeName;
    private String email;
    private String position;
    private int phone;
    private int root;
}