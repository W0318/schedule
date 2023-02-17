package com.schedule.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Data
public class Scheduling implements Serializable {
    private int id;
    private String employeeIds;
    private Date day;
    private String periodName;
    private Time startTime;
    private Time endTime;
}
