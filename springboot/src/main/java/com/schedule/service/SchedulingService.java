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

    public List<String> getEmployeeDaywork(String employeeId, Date day) {
        return schedulingMapper.getEmployeeDaywork(employeeId, day);
    }

    public List<Scheduling> getAWeekwork(Date Monday, Date Sunday, String storeId) {
        return schedulingMapper.getAWeekwork(Monday, Sunday, storeId);
    }

    public List<Scheduling> getADaywork(Date day, String storeId) {
        return schedulingMapper.getADaywork(day, storeId);
    }

    public int deleteScheduling(String ids) {
        return schedulingMapper.deleteScheduling(ids);
    }

    public int insertScheduling(String storeId, String employeeIds, Date day, Time startTime, Time endTime, String periodName) {
        return schedulingMapper.insertScheduling(storeId, employeeIds, day, startTime, endTime, periodName);
    }

    public int updateScheduling(String employeeIds, int id) {
        return schedulingMapper.updateScheduling(employeeIds, id);
    }

    public Scheduling getById(int id) {
        return schedulingMapper.getById(id);
    }
}
