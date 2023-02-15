package com.schedule.service;

import com.schedule.entity.Scheduling;
import com.schedule.mapper.SchedulingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Service
public class SchedulingService {
    @Autowired
    SchedulingMapper schedulingMapper;

    /**
     * @author 吴佳丽
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    public List<Date> getEmployeeWorkday(String employeeId) {
        return schedulingMapper.getEmployeeWorkday(employeeId);
    }

    public List<String> getEmployeeDayWork(String employeeId, Date day) {
        return schedulingMapper.getEmployeeDayWork(employeeId, day);
    }

    public List<Scheduling> getAWeekWork(Date Monday, Date Sunday, String storeId) {
        return schedulingMapper.getAWeekWork(Monday, Sunday, storeId);
    }

    public List<Scheduling> getADayWork(Date day, String storeId) {
        return schedulingMapper.getADayWork(day, storeId);
    }

    public int deleteScheduling(String ids) {
        return schedulingMapper.deleteScheduling(ids);
    }

    public void insertScheduling(String storeId, String employeeIds, Date day, Time startTime, Time endTime, String periodName) {
        schedulingMapper.insertScheduling(storeId, employeeIds, day, startTime, endTime, periodName);
    }

    public void updateScheduling(String employeeIds, int id) {
        schedulingMapper.updateScheduling(employeeIds, id);
    }

    public void updateEmployeeIds(String employeeIds, Date day, Time startTime) {
        schedulingMapper.updateEmployeeIds(employeeIds, day, startTime);
    }

    public void deleteEmployeeIds(Date day, Time startTime) {
        schedulingMapper.deleteEmployeeIds(day, startTime);
    }

    public Scheduling getById(int id) {
        return schedulingMapper.getById(id);
    }

    public Scheduling getByTime(Date day, Time startTime) {
        return schedulingMapper.getByTime(day, startTime);
    }
}
