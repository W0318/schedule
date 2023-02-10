package com.schedule.service;

import com.schedule.entity.Scheduling;
import com.schedule.mapper.SchedulingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class SchedulingService {
    @Autowired
    SchedulingMapper schedulingMapper;

    public List<Date> getEmployeeWorkday(String employeeId) {
        return schedulingMapper.getEmployeeWorkday(employeeId);
    }

    public List<String> getEmployeeDaywork(String employeeId, Date day) {
        return schedulingMapper.getEmployeeDaywork(employeeId, day);
    }

    public List<Scheduling> getAWeekwork(Date Monday, Date Sunday) {
        return schedulingMapper.getAWeekwork(Monday, Sunday);
    }
}
