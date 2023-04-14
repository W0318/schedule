package com.schedule.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Preference implements Serializable {
    private int preferId;
    private String employeeId;
    private String preferType;
    private String preferValue;
}
