package com.schedule.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.schedule.entity.Employee;
import com.schedule.entity.Scheduling;
import com.schedule.method.Methods;
import com.schedule.service.EmployeeService;
import com.schedule.service.RuleService;
import com.schedule.service.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/scheduling")
public class SchedulingController {
    @Autowired
    SchedulingService schedulingService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    RuleService ruleService;

    String[] weekdayStart;
    String[] weekendStart;
    String[] weekdayEnd;
    String[] weekendEnd;
    int[] week = {1, 2, 3, 4, 5, 6, 0};
    int shiftLen;

    @GetMapping("/getWorkday/{employeeId}")
    public List<Date> getEmployeeWorkday(@PathVariable("employeeId") String employeeId) {
        return schedulingService.getEmployeeWorkday(employeeId);
    }

    @GetMapping("/getDayWork/{employeeId}/{day}")
    public List<String> getEmployeeDayWork(@PathVariable("employeeId") String employeeId, @PathVariable("day") Date day) {
        return schedulingService.getEmployeeDayWork(employeeId, day);
    }

    @GetMapping("/getAWeekWork/{Monday}/{Sunday}/{storeId}")
    public List<Object> getAWeekWork(@PathVariable("Monday") Date Monday, @PathVariable("Sunday") Date Sunday, @PathVariable("storeId") String storeId) {
        int index = 0;
        setPeriods(storeId);

        List<Scheduling> work = schedulingService.getAWeekWork(Monday, Sunday, storeId);   //从数据库获取一周数据
        List<Scheduling> dayWork = new ArrayList<>();   //一天的排班数据
        List<Object> weekWork = new ArrayList<>();   //七天的排班数据【dayWork * 7】
        for (Scheduling scheduling : work) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(scheduling.getDay());
            //判断日期是星期几：1-6对应星期一到星期六，0对应星期日
            int week_index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            while (week_index != week[index]) {
                weekWork.add(dayWork);   //dayWork为空或1/多个数据
                dayWork = new ArrayList<>();
                index++;
            }

            if (week_index == week[index]) {   //将同一天的数据放入同一个列表
                dayWork.add(scheduling);
            }
        }
        //index到周日之间存在连续天没有排班数据，全置为空
        for (int i = weekWork.size(); i < 7; i++) {
            weekWork.add(dayWork);
            dayWork = new ArrayList<>();
        }

        //给每个排班时间段数据，无论是否为空【为了前端显示】
        for (int i = 0; i < 7; i++) {
            dayWork = (List<Scheduling>) weekWork.get(i);   //一天数据
            List<Object> dayWork1 = new ArrayList<>();   //新的一天数据
            index = 0;

            int flagStart = i < 5 ? 1 : 2;

            for (int j = 0; j < shiftLen; j++) {
                List<Object> dayWork2;
                if (index < dayWork.size() && (format((dayWork.get(index).getStartTime()), "H:mm:ss").equals(period(flagStart, j)))) {
                    dayWork2 = new ArrayList<>();
                    dayWork2.add(employeeService.getEmployees(dayWork.get(index).getEmployeeIds()));
                    dayWork2.add(dayWork.get(index).getId());
                    dayWork1.add(dayWork2);
                    index++;
                } else {
                    dayWork2 = new ArrayList<>();
                    dayWork2.add(new ArrayList<Employee>());
                    dayWork2.add(null);
                    dayWork1.add(dayWork2);
                }
            }
            weekWork.set(i, dayWork1);
        }

        return weekWork;
    }

    @GetMapping("/getADayWork/{day}/{week}/{storeId}")
    public List<Object> getADayWork(@PathVariable("day") Date day, @PathVariable("week") int week, @PathVariable("storeId") String storeId) {
        int index = 0;
        setPeriods(storeId);

        List<Scheduling> work = schedulingService.getADayWork(day, storeId);
        List<Employee> newWork = new ArrayList<>();
        List<Object> newWorks = new ArrayList<>();

        int flagStart = week == 1 ? 1 : 2;

        for (Scheduling scheduling : work) {
            while (index < weekdayStart.length && !(format((scheduling.getStartTime()), "H:mm:ss").equals(period(flagStart, index)))) {
                for (int j = 0; j < 8; j++) {
                    newWork.add(new Employee());
                }
                newWorks.add(newWork);
                newWork = new ArrayList<>();
                index++;
            }

            if (index < weekdayStart.length && (format(scheduling.getStartTime(), "H:mm:ss")).equals(period(flagStart, index))) {
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

        for (int i = index; i < shiftLen; i++) {
            for (int j = 0; j < 8; j++) {
                newWork.add(new Employee());
            }
            newWorks.add(newWork);
            newWork = new ArrayList<>();
            index++;
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
        String storeId = map.get("storeId").toString();
        setPeriods(storeId);

        String[] partIds = employeeIds.split("[=]");
        String[] partIndexs = startIndex.split("[,]");
        System.out.println("---------------deleteDaySchedule---------------");
        System.out.println(employeeIds);
        System.out.println(startIndex);
        System.out.println(partIds[0]);
        System.out.println(partIndexs[0].equals(""));
        System.out.println("---------------end---------------");

        int flagStart = week == 1 ? 1 : 2;

        for (int i = 0; i < partIds.length; i++) {
            if (partIds[i].equals("") && !partIndexs[i].equals("")) {
                schedulingService.deleteEmployeeIds(day, Time.valueOf(period(flagStart, Integer.parseInt(partIndexs[i]))));
            } else if (!partIndexs[i].equals("")) {
                schedulingService.updateEmployeeIds(partIds[i], day, Time.valueOf(period(flagStart, Integer.parseInt(partIndexs[i]))));
            }
        }
    }

    @PostMapping("/replaceScheduling")
    @ResponseBody
    public void replaceScheduling(@RequestBody Map map) {
        String tableData = map.get("tableData").toString();
        String week = map.get("week").toString();
        String storeId = map.get("storeId").toString();
        setPeriods(storeId);

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

                    int flagStart = j < 5 ? 1 : 2;
                    int flagEnd = j < 5 ? 3 : 4;
                    scheduling.setStartTime(Time.valueOf(period(flagStart, i)));
                    scheduling.setEndTime(Time.valueOf(period(flagEnd, i)));
                    scheduling.setPeriodName("周" + weekValue[j] + period(flagStart, i) + "-" + period(flagEnd, i));

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

    @PostMapping("/replaceDaySchedule")
    @ResponseBody
    public void replaceDaySchedule(@RequestBody Map map) {
        String employeeIds = map.get("employeeIds").toString();
        String storeId = map.get("storeId").toString();
        setPeriods(storeId);
        Date day = Date.valueOf(map.get("day").toString());
        int week = (int) map.get("week");


        String[] weeks = {"日", "一", "二", "三", "四", "五", "六"};
        String[] partIds = employeeIds.split("[=]", -1);
        System.out.println(employeeIds);
        System.out.println(partIds.length);

        int flagStart = (week == 0 || week == 6) ? 2 : 1;
        int flagEnd = (week == 0 || week == 6) ? 4 : 3;
        for (int i = 0; i < 6; i++) {
            if (partIds[i].equals("")) {
                schedulingService.deleteEmployeeIds(day, Time.valueOf(period(flagStart, i)));
            } else {
                Scheduling scheduling = schedulingService.getByTime(day, Time.valueOf(period(flagStart, i)));
                if (scheduling == null)
                    schedulingService.insertScheduling(storeId, partIds[i], day, Time.valueOf(period(flagStart, i)),
                            Time.valueOf(period(flagEnd, i)), "周" + weeks[week] + period(flagStart, i) + "-" + period(flagEnd, i));
                else
                    schedulingService.updateEmployeeIds(partIds[i], day, Time.valueOf(period(flagStart, i)));
            }
        }
    }

    private String format(Time time, String form) {
        SimpleDateFormat sdf = new SimpleDateFormat(form);
        return sdf.format(time);
    }

    private String period(int flag, int index) {
        if (flag == 1) return weekdayStart[index];
        else if (flag == 2) return weekendStart[index];
        else if (flag == 3) return weekdayEnd[index];
        else if (flag == 4) return weekendEnd[index];

        return null;
    }

    private List<Double> setArray(String storeId) {
        List<Double> generalTime = (new Methods()).getTime(ruleService.getGeneralRule());
        List<Double> storeTime = (new Methods()).getTime(ruleService.getStoreRule(storeId));
        for (int i = 0; i < storeTime.size(); i++) {
            if (storeTime.get(i) < 0) storeTime.set(i, generalTime.get(i));
        }

        shiftLen = 24 + (int) (storeTime.get(0) / 0.5) + (int) (storeTime.get(1) / 0.5);
        weekdayStart = new String[shiftLen];
        weekendStart = new String[shiftLen];
        weekdayEnd = new String[shiftLen];
        weekendEnd = new String[shiftLen];

        return storeTime;
    }

    private void setPeriods(String storeId) {
        List<Double> storeTime = setArray(storeId);

        int index = 0;
        for (double i = (9 - storeTime.get(0)); i < (21 + storeTime.get(1)); i += 0.5, index++) {
            if (i - (int) i != 0) {
                weekdayStart[index] = (int) i + ":30:00";
                weekdayEnd[index] = ((int) i + 1) + ":00:00";
                weekendStart[index] = ((int) i + 1) + ":30:00";
                weekendEnd[index] = ((int) i + 2) + ":00:00";
            } else {
                weekdayStart[index] = (int) i + ":00:00";
                weekdayEnd[index] = ((int) i) + ":30:00";
                weekendStart[index] = ((int) i + 1) + ":00:00";
                weekendEnd[index] = ((int) i + 1) + ":30:00";
            }
        }
    }

    /**
     * @author 张卓童
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @PostMapping("/deleteSchedulingByStoreId")
    @ResponseBody
    public void deleteSchedulingByStoreId(@RequestBody Map map) {
        schedulingService.deleteSchedulingByStoreId(map.get("storeId").toString());
    }
}