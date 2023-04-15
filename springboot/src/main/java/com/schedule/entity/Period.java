package com.schedule.entity;

import lombok.Data;

@Data
public class Period {
    private int periodId;
    private String storeId;
    private String periodName;
    private String type;
}
