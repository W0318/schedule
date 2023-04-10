package com.schedule.entity;

import lombok.Data;

@Data
public class Flow {
    private String period;   //时间段
    private int need;   //员工人数需求

    public Flow() {
    }

    public Flow(String period, int need) {
        this.period = period;
        this.need = need;
    }
}
