package com.schedule.entity;

import lombok.Data;
import java.sql.Time;

@Data
public class TimePeriod {
    private int id;
    private String worktimeId;
    private int day;
    private String periodName;
    private Time startTime;
    private Time endTime;
}
