package com.schedule.entity;

import lombok.Data;

@Data
public class Rule {
    private String ruleId;
    private String storeId;
    private String ruleType;
    private String ruleValue;
}
