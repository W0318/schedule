package com.schedule.controller;

import com.schedule.entity.Employee;
import com.schedule.entity.Scheduling;
import com.schedule.service.EmployeeService;
import com.schedule.service.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/scheduling")
public class SchedulingController {
    @Autowired
    SchedulingService schedulingService;

    @GetMapping("/getWorkday/{employeeId}")
    public List<Date> getEmployeeWorkday(@PathVariable("employeeId") String employeeId) {
        return schedulingService.getEmployeeWorkday(employeeId);
    }

    @GetMapping("/getDaywork/{employeeId}/{day}")
    public List<String> getEmployeeDaywork(@PathVariable("employeeId") String employeeId, @PathVariable("day") Date day) {
        return schedulingService.getEmployeeDaywork(employeeId, day);
    }

    @Autowired
    EmployeeService employeeService;
    @GetMapping("/getAWeekwork/{Monday}/{Sunday}")
    public List<Object> getAWeekwork(@PathVariable("Monday") Date Monday, @PathVariable("Sunday") Date Sunday) {
        String[] weekdayTimes = {"08:30:00", "11:00:00", "14:00:00", "17:00:00", "19:00:00", "21:00:00"};
        String[] weekendTimes = {"09:30:00", "12:00:00", "14:00:00", "17:00:00", "20:00:00", "22:00:00"};
        String[] week = {"一", "二", "三", "四", "五", "六", "日"};
        int index = 0;

        List<Scheduling> work = schedulingService.getAWeekwork(Monday, Sunday);
        List<Scheduling> newWork = new ArrayList<>();
        List<Object> newWorks = new ArrayList<>();
        for (int i = 0; i < work.size(); i++) {
            while (!((work.get(i).getPeriodName()).contains("周" + week[index]))) {
                newWorks.add(newWork);
                newWork = new ArrayList<>();
                index++;
            }

            if ((work.get(i).getPeriodName()).contains("周" + week[index])) {
                newWork.add(work.get(i));
            }
        }
        for (int i = index; i < 7; i++) {
            newWorks.add(newWork);
            newWork = new ArrayList<>();
        }

        for(int i = 0; i < 5; i++) {
            newWork = (List<Scheduling>) newWorks.get(i);
            List<Object> newWork1 = new ArrayList<>();
            index = 0;
            for(int j = 0; j < 6; j++) {
                if (index < newWork.size() && ((newWork.get(index).getStartTime().toString()).equals(weekdayTimes[j]))) {
                    newWork1.add(employeeService.getWeekEmployees(newWork.get(index).getEmployeeIds()));
                    index++;
                }
                else {
                    newWork1.add(new ArrayList<Employee>());
                }
            }
            newWorks.set(i, newWork1);
        }
        for(int i = 5; i < 7; i++) {
            newWork = (List<Scheduling>) newWorks.get(i);
            List<Object> newWork1 = new ArrayList<>();
            index = 0;
            for(int j = 0; j < 6; j++) {
                if (index < newWork.size() && ((newWork.get(index).getStartTime().toString()).equals(weekendTimes[j]))) {
                    newWork1.add(employeeService.getWeekEmployees(newWork.get(index).getEmployeeIds()));
                    index++;
                }
                else {
                    newWork1.add(new ArrayList<Employee>());
                }
            }
            newWorks.set(i, newWork1);
        }

        return newWorks;
    }
}
