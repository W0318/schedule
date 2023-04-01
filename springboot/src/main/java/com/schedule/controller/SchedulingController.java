package com.schedule.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.schedule.entity.Employee;
import com.schedule.entity.Scheduling;
import com.schedule.service.EmployeeService;
import com.schedule.service.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/scheduling")
public class SchedulingController {
    @Autowired
    SchedulingService schedulingService;
    @Autowired
    EmployeeService employeeService;

    /**
     * 数据库你可以改，比如增加一张员工和排班关系表，就不用对employee是处理了
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    String[] weekdayStart = {"08:30:00", "11:00:00", "14:00:00", "17:00:00", "19:00:00", "21:00:00"};
    String[] weekendStart = {"09:30:00", "12:00:00", "14:00:00", "17:00:00", "20:00:00", "22:00:00"};
    String[] weekdayEnd = {"11:00:00", "14:00:00", "17:00:00", "19:00:00", "21:00:00", "23:00:00"};
    String[] weekendEnd = {"12:00:00", "14:00:00", "17:00:00", "20:00:00", "22:00:00", "24:00:00"};
    String[] week = {"一", "二", "三", "四", "五", "六", "日"};

    @GetMapping("/getWorkday/{employeeId}")
    public List<Date> getEmployeeWorkday(@PathVariable("employeeId") String employeeId) {
        return schedulingService.getEmployeeWorkday(employeeId);
    }

    @GetMapping("/getDayWork/{employeeId}/{day}")
    public List<String> getEmployeeDayWork(@PathVariable("employeeId") String employeeId, @PathVariable("day") Date day) {
        return schedulingService.getEmployeeDayWork(employeeId, day);
    }

    /**
     * 获取排班数据，并转化成前端一周数据格式
     * 数据库里的数据可以改
     * @param Monday
     * @param Sunday
     * @param storeId
     * @return
     */
    @GetMapping("/getAWeekWork/{Monday}/{Sunday}/{storeId}")
    public List<Object> getAWeekWork(@PathVariable("Monday") Date Monday, @PathVariable("Sunday") Date Sunday, @PathVariable("storeId") String storeId) {
        int index = 0;

        List<Scheduling> work = schedulingService.getAWeekWork(Monday, Sunday, storeId);
        List<Scheduling> newWork = new ArrayList<>();
        List<Object> newWorks = new ArrayList<>();
        for (Scheduling scheduling : work) {
            while (!((scheduling.getPeriodName()).contains("周" + week[index]))) {
                newWorks.add(newWork);
                newWork = new ArrayList<>();
                index++;
            }

            if ((scheduling.getPeriodName()).contains("周" + week[index])) {
                newWork.add(scheduling);
            }
        }
        for (int i = index; i < 7; i++) {
            newWorks.add(newWork);
            newWork = new ArrayList<>();
        }

        for (int i = 0; i < 7; i++) {
            newWork = (List<Scheduling>) newWorks.get(i);
            List<Object> newWork1 = new ArrayList<>();
            index = 0;
            for (int j = 0; j < 6; j++) {
                List<Object> newWork2;
                if (i < 5) {
                    if (index < newWork.size() && ((newWork.get(index).getStartTime().toString()).equals(weekdayStart[j]))) {
                        newWork2 = new ArrayList<>();
                        newWork2.add(employeeService.getEmployees(newWork.get(index).getEmployeeIds()));
                        newWork2.add(newWork.get(index).getId());
                        newWork1.add(newWork2);
                        index++;
                    } else {
                        newWork2 = new ArrayList<>();
                        newWork2.add(new ArrayList<Employee>());
                        newWork2.add(null);
                        newWork1.add(newWork2);
                    }
                } else {
                    if (index < newWork.size() && ((newWork.get(index).getStartTime().toString()).equals(weekendStart[j]))) {
                        newWork2 = new ArrayList<>();
                        newWork2.add(employeeService.getEmployees(newWork.get(index).getEmployeeIds()));
                        newWork2.add(newWork.get(index).getId());
                        newWork1.add(newWork2);
                        index++;
                    } else {
                        newWork2 = new ArrayList<>();
                        newWork2.add(new ArrayList<Employee>());
                        newWork2.add(null);
                        newWork1.add(newWork2);
                    }
                }
            }
            newWorks.set(i, newWork1);
        }

        return newWorks;
    }

    /**
     * 获取排班数据，并转化成前端一日数据格式
     * 数据库里的数据可以改
     * @param day
     * @param week
     * @param storeId
     * @return
     */
    @GetMapping("/getADayWork/{day}/{week}/{storeId}")
    public List<Object> getADayWork(@PathVariable("day") Date day, @PathVariable("week") int week, @PathVariable("storeId") String storeId) {
        int index = 0;

        List<Scheduling> work = schedulingService.getADayWork(day, storeId);
        List<Employee> newWork = new ArrayList<>();
        List<Object> newWorks = new ArrayList<>();
        if (week == 1) {
            for (Scheduling scheduling : work) {
                while (!((scheduling.getStartTime().toString()).equals(weekdayStart[index]))) {
                    for (int j = 0; j < 8; j++) {
                        newWork.add(new Employee());
                    }
                    newWorks.add(newWork);
                    newWork = new ArrayList<>();
                    index++;
                }

                if ((scheduling.getStartTime().toString()).equals(weekdayStart[index])) {
                    newWork = employeeService.getEmployees(scheduling.getEmployeeIds());
                    int need = 8 - newWork.size();
                    for (int j = 0; j < need; j++) {
                        newWork.add(new Employee());
                    }
                    newWorks.add(newWork);
                    newWork = new ArrayList<>();
                    index++;
                }
            }

            for (int i = index; i < 6; i++) {
                for (int j = 0; j < 8; j++) {
                    newWork.add(new Employee());
                }
                newWorks.add(newWork);
                newWork = new ArrayList<>();
                index++;
            }
        } else {
            for (Scheduling scheduling : work) {
                while (!((scheduling.getStartTime().toString()).equals(weekendStart[index]))) {
                    for (int j = 0; j < 8; j++) {
                        newWork.add(new Employee());
                    }
                    newWorks.add(newWork);
                    newWork = new ArrayList<>();
                    index++;
                }

                if ((scheduling.getStartTime().toString()).equals(weekendStart[index])) {
                    newWork = employeeService.getEmployees(scheduling.getEmployeeIds());
                    int need = 8 - newWork.size();
                    for (int j = 0; j < need; j++) {
                        newWork.add(new Employee());
                    }
                    newWorks.add(newWork);
                    newWork = new ArrayList<>();
                    index++;
                }
            }

            for (int i = index; i < 6; i++) {
                for (int j = 0; j < 8; j++) {
                    newWork.add(new Employee());
                }
                newWorks.add(newWork);
                newWork = new ArrayList<>();
                index++;
            }
        }

        return newWorks;
    }

    @GetMapping("/deleteScheduling/{ids}")
    public int deleteScheduling(@PathVariable("ids") String ids) {
        return schedulingService.deleteScheduling(ids);
    }

    @PostMapping("/deleteDaySchedule")
    @ResponseBody
    public void deleteDaySchedule(@RequestBody Map map) {
        String employeeIds = map.get("employeeIds").toString();
        Date day = Date.valueOf(map.get("day").toString());
        int week = (int) map.get("week");
        String startIndex = map.get("startIndex").toString();

        String[] partIds = employeeIds.split("[=]");
        String[] partIndexs = startIndex.split("[,]");
//        System.out.println(employeeIds);
//        System.out.println(startIndex);
        for (int i = 0; i < partIndexs.length; i++) {
            if (partIds[i].equals("")) {
                if (week == 1)
                    schedulingService.deleteEmployeeIds(day, Time.valueOf(weekdayStart[Integer.parseInt(partIndexs[i])]));
                else
                    schedulingService.deleteEmployeeIds(day, Time.valueOf(weekendStart[Integer.parseInt(partIndexs[i])]));
            } else {
                if (week == 1)
                    schedulingService.updateEmployeeIds(partIds[i], day, Time.valueOf(weekdayStart[Integer.parseInt(partIndexs[i])]));
                else
                    schedulingService.updateEmployeeIds(partIds[i], day, Time.valueOf(weekendStart[Integer.parseInt(partIndexs[i])]));

            }
        }
    }

    @PostMapping("/replaceScheduling")
    @ResponseBody
    public void replaceScheduling(@RequestBody Map map) {
        String tableData = map.get("tableData").toString();
        String week = map.get("week").toString();
        String[] datas = tableData.split("[<]");
        String[] weeks = week.split("[,]");
//        Object[][][] schedule = new Object[6][7][2];

        String[] weekValue = {"一", "二", "三", "四", "五", "六", "日"};

        for (int i = 0; i < datas.length; i++) {
            String[] datas1 = datas[i].split("[-]");
            for (int j = 0; j < datas1.length; j++) {
                if (!(datas1[j].equals("+"))) {
                    String[] datas2 = datas1[j].split("[+]");
                    Scheduling scheduling = new Scheduling();
                    scheduling.setDay(Date.valueOf(weeks[j]));
                    if (j < 5) {
                        scheduling.setStartTime(Time.valueOf(weekdayStart[i]));
                        scheduling.setEndTime(Time.valueOf(weekdayEnd[i]));
                        scheduling.setPeriodName("周" + weekValue[j] + weekdayStart[i] + "-" + weekdayEnd[i]);
                    } else {
                        scheduling.setStartTime(Time.valueOf(weekendStart[i]));
                        scheduling.setEndTime(Time.valueOf(weekendEnd[i]));
                        scheduling.setPeriodName("周" + weekValue[j] + weekendStart[i] + "-" + weekendEnd[i]);
                    }

                    for (int k = 0; k < datas2.length; k++) {
                        String[] datas3 = datas2[k].split("[>]");
//                        List<Employee> employees = new ArrayList<>();
                        StringBuilder employeeIds = new StringBuilder();
                        for (int l = 0; l < datas3.length; l++) {
                            if (k == 0) {
                                if (!(datas3[l].equals(""))) {
                                    Employee employee = JSON.toJavaObject(JSONObject.parseObject(datas3[l]), Employee.class);
                                    scheduling.setStoreId(employee.getStoreId());
                                    if (l == 0) employeeIds.append(employee.getEmployeeId());
                                    else employeeIds.append(",").append(employee.getEmployeeId());
                                    if (l == datas3.length - 1)
                                        scheduling.setEmployeeIds(employeeIds.toString());
//                                employees.add(employee);
                                }
                            } else {
                                scheduling.setId(Integer.parseInt(datas3[l]));
//                                schedule[i][j][k] = datas3[l];
                            }
                        }

                        if (k == 1 && scheduling.getId() != 0 && scheduling.getEmployeeIds() != null &&
                                !((scheduling.getEmployeeIds()).equals(schedulingService.getById(scheduling.getId()).getEmployeeIds()))) {
                            System.out.println(scheduling);
                            schedulingService.updateScheduling(scheduling.getEmployeeIds(), scheduling.getId());
                        } else if (datas2.length < 2) {
                            System.out.println(scheduling);
                            schedulingService.insertScheduling(scheduling.getStoreId(), scheduling.getEmployeeIds(), scheduling.getDay(), scheduling.getStartTime(), scheduling.getEndTime(), scheduling.getPeriodName());
                        } else if (k == 1 && scheduling.getEmployeeIds() == null) {
                            System.out.println(scheduling);
                            schedulingService.deleteScheduling(String.valueOf(scheduling.getId()));
                        }
//                        if (k == 0) {
//                            schedule[i][j][k] = employees;
//                        }
                    }
                }
            }
        }
    }

    @GetMapping("/replaceDaySchedule/{employeeIds}/{storeId}/{day}/{week}")
    public void replaceDaySchedule(@PathVariable("employeeIds") String employeeIds, @PathVariable("storeId") String storeId, @PathVariable("day") Date day, @PathVariable("week") int week) {
        String[] weeks = {"日", "一", "二", "三", "四", "五", "六"};
        String[] partIds = employeeIds.split("[=]", -1);
        System.out.println(employeeIds);
        System.out.println(partIds.length);
        for (int i = 0; i < 6; i++) {
            if (partIds[i].equals("")) {
                if (week == 0 || week == 6)
                    schedulingService.deleteEmployeeIds(day, Time.valueOf(weekendStart[i]));
                else
                    schedulingService.deleteEmployeeIds(day, Time.valueOf(weekdayStart[i]));
            } else {
                if (week == 0 || week == 6) {
                    Scheduling scheduling = schedulingService.getByTime(day, Time.valueOf(weekendStart[i]));
                    if (scheduling == null)
                        schedulingService.insertScheduling(storeId, partIds[i], day, Time.valueOf(weekendStart[i]),
                                Time.valueOf(weekendEnd[i]), "周" + weeks[week] + weekendStart[i] + weekendEnd[i]);
                    else
                        schedulingService.updateEmployeeIds(partIds[i], day, Time.valueOf(weekendStart[i]));
                } else {
                    Scheduling scheduling = schedulingService.getByTime(day, Time.valueOf(weekdayStart[i]));
                    if (scheduling == null)
                        schedulingService.insertScheduling(storeId, partIds[i], day, Time.valueOf(weekdayStart[i]),
                                Time.valueOf(weekdayEnd[i]), "周" + weeks[week] + weekdayStart[i] + "-" + weekdayEnd[i]);
                    else
                        schedulingService.updateEmployeeIds(partIds[i], day, Time.valueOf(weekdayStart[i]));
                }
            }
        }
    }
}