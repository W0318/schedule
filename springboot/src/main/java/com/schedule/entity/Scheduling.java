package com.schedule.entity;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class Scheduling {
    private int id;
    private String employeeIds;
    private Date day;
    private String periodName;
    private Time startTime;
    private Time endTime;
}
