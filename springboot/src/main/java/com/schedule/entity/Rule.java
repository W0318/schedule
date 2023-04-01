package com.schedule.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Rule implements Serializable {
    private String ruleId;
    private String storeId;
    private String ruleType;
    private String ruleValue;
}
