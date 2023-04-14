package com.schedule.service;

import com.schedule.entity.FormDatePeriod;
import com.schedule.entity.Scheduling;
import com.schedule.mapper.SchedulingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchedulingService {
    @Autowired
    SchedulingMapper schedulingMapper;

    public List<Date> getEmployeeWorkday(String employeeId) {
        List<FormDatePeriod> datas = schedulingMapper.getEmployeeWorkday(employeeId);
        List<Date> dates = new ArrayList<>();
        System.out.println(datas);

        for (FormDatePeriod data: datas) {
            String[] employeeIds = data.getEmployeeIds().split(",");
            for (String e : employeeIds) {
                if (e.equals(employeeId)) {
                    dates.add(data.getDay());
                    break;
                }
            }
        }

        return dates;
    }

    public List<String> getEmployeeDayWork(String employeeId, Date day) {
        List<FormDatePeriod> datas = schedulingMapper.getEmployeeDayWork(employeeId, day);
        List<String> periods = new ArrayList<>();
        System.out.println(datas);

        for (FormDatePeriod data: datas) {
            String[] employeeIds = data.getEmployeeIds().split(",");
            for (String e : employeeIds) {
                if (e.equals(employeeId)) {
                    periods.add(data.getPeriod());
                    break;
                }
            }
        }

        return periods;
    }

    public List<Scheduling> getAWeekWork(Date Monday, Date Sunday, String storeId) {
        return schedulingMapper.getAWeekWork(Monday, Sunday, storeId);
    }

    public List<Scheduling> getStuffWeekWork(Date Monday, Date Sunday, String storeId, String employeeId) {
        List<Scheduling> schedulings = schedulingMapper.getStuffWeekWork(Monday, Sunday, storeId, employeeId);
//        System.out.println("before: " + schedulings);
        for (int i = schedulings.size() - 1; i >= 0; i--) {
            String[] employeeIds = schedulings.get(i).getEmployeeIds().split(",");
            Boolean flag = false;
            for (String e : employeeIds) {
                if (e.equals(employeeId)) {
                    flag = true;
                    schedulings.get(i).setEmployeeIds(employeeId);
                    break;
                }
            }

            if (!flag) {
                schedulings.remove(i);
            }
        }

        System.out.println("after: " +schedulings);
        return schedulings;
    }

    public List<Scheduling> getADayWork(Date day, String storeId) {
        return schedulingMapper.getADayWork(day, storeId);
    }

    public List<Scheduling> getStuffDayWork(Date day, String storeId, String employeeId) {
        List<Scheduling> schedulings = schedulingMapper.getStuffDayWork(day, storeId, employeeId);

        for (int i = schedulings.size() - 1; i >= 0; i--) {
            String[] employeeIds = schedulings.get(i).getEmployeeIds().split(",");
            Boolean flag = false;
            for (String e : employeeIds) {
                if (e.equals(employeeId)) {
                    flag = true;
                    schedulings.get(i).setEmployeeIds(employeeId);
                    break;
                }
            }

            if (!flag) {
                schedulings.remove(i);
            }
        }

        System.out.println("after: " +schedulings);
        return schedulings;
    }

    public int deleteScheduling(String ids) {
        return schedulingMapper.deleteScheduling(ids);
    }

    public void insertScheduling(Scheduling scheduling) {
        schedulingMapper.insertScheduling(scheduling);
    }

    public void updateScheduling(String employeeIds, int id) {
        schedulingMapper.updateScheduling(employeeIds, id);
    }

    public void updateEmployeeIds(String employeeIds, Date day, Time startTime, String storeId) {
        schedulingMapper.updateEmployeeIds(employeeIds, day, startTime, storeId);
    }

    public void deleteEmployeeIds(Date day, Time startTime, String storeId) {
        schedulingMapper.deleteEmployeeIds(day, startTime, storeId);
    }

    public Scheduling getById(int id) {
        return schedulingMapper.getById(id);
    }

    public Scheduling getByTime(Date day, Time startTime, String storeId) {
        return schedulingMapper.getByTime(day, startTime, storeId);
    }
}
