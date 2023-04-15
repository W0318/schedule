package com.schedule.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class FormDatePeriod {
    private Date day;
    private String employeeIds;
    private String period;
}
