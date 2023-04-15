package com.schedule.controller;

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

    int[] week = {1, 2, 3, 4, 5, 6, 0};
    int periodLen;
    int shiftLen;

    @GetMapping("/getWorkDay/{employeeId}")
    public List<Date> getEmployeeWorkday(@PathVariable("employeeId") String employeeId) {
        return schedulingService.getEmployeeWorkday(employeeId);
    }

    @GetMapping("/getDayWork/{employeeId}/{day}")
    public List<String> getEmployeeDayWork(@PathVariable("employeeId") String employeeId, @PathVariable("day") Date day) {
        return schedulingService.getEmployeeDayWork(employeeId, day);
    }

    @GetMapping("/getAWeekWork/{Monday}/{Sunday}/{storeId}")
    public List<Object> getAWeekWork(@PathVariable("Monday") Date Monday, @PathVariable("Sunday") Date Sunday, @PathVariable("storeId") String storeId) {
        List<Scheduling> work = schedulingService.getAWeekWork(Monday, Sunday, storeId);   //从数据库获取一周数据
        System.out.println(work);
        List<Object> weekWork = getSchedule(work, storeId);

        return weekWork;
    }

    @PostMapping("/getStuffWeekWork")
    @ResponseBody
    public List<Object> getStuffWeekWork(@RequestBody Map<String, String> map) {
        if (map.get("Monday") != null && map.get("Sunday") != null && map.get("storeId") != null && map.get("employeeId") != null) {
            Date Monday = Date.valueOf(map.get("Monday"));
            Date Sunday = Date.valueOf(map.get("Sunday"));
            String storeId = map.get("storeId");
            String employeeId = map.get("employeeId");

            List<Scheduling> work = schedulingService.getStuffWeekWork(Monday, Sunday, storeId, employeeId);
            List<Object> weekWork = getSchedule(work, storeId);

            return weekWork;
        }
        return null;
    }

    @GetMapping("/getADayWork/{day}/{week}/{storeId}")
    public List<Object> getADayWork(@PathVariable("day") Date day, @PathVariable("week") int week, @PathVariable("storeId") String storeId) {
        List<Scheduling> work = schedulingService.getADayWork(day, storeId);
        return getDaySchedule(work, storeId, week);
    }

    @PostMapping("/getStuffDayWork")
    @ResponseBody
    public List<Object> getStuffDayWork(@RequestBody Map map) {
        Date day = Date.valueOf(map.get("day").toString());
        int week = (int) map.get("week");
        String storeId = map.get("storeId").toString();
        String employeeId = map.get("employeeId").toString();
        List<Scheduling> work = schedulingService.getStuffDayWork(day, storeId, employeeId);
        return getDaySchedule(work, storeId, week);
    }

    @GetMapping("/deleteScheduling/{ids}")
    public int deleteScheduling(@PathVariable("ids") String ids) {
        return schedulingService.deleteScheduling(ids);
    }

    @PostMapping("/deleteDaySchedule")
    @ResponseBody
    public void deleteDaySchedule(@RequestBody Map map) {
        List<Object> items = (List<Object>) map.get("items");
        Date day = Date.valueOf(map.get("day").toString());
        String storeId = map.get("storeId").toString();
        System.out.println(items);

        for (Object item : items) {
            JSONObject value = JSONObject.parseObject(JSONObject.toJSONString(item));
            System.out.println(value);
            String[] times = value.getString("period").split("-");
            String start = times[0] + ":00";

            if (value.getString("employeeIds").equals("")) {
                schedulingService.deleteEmployeeIds(day, Time.valueOf(start), storeId);
            } else {
                schedulingService.updateEmployeeIds(value.getString("employeeIds"), day, Time.valueOf(start), storeId);
            }
        }
    }

    @PostMapping("/replaceScheduling")
    @ResponseBody
    public void replaceScheduling(@RequestBody Map map) {
        List<Object> items = (List<Object>) map.get("items");
        String storeId = map.get("storeId").toString();
        System.out.println(items);

        for (Object item : items) {
            JSONObject value = JSONObject.parseObject(item.toString());
            if (value.get("id") == null) {
                String[] times = value.getString("period").split("-");
                String start = times[0] + ":00";
                String end = times[1] + ":00";
                Scheduling scheduling = new Scheduling();
                scheduling.setStoreId(storeId);
                scheduling.setEmployeeIds(value.getString("employeeIds"));
                scheduling.setDay(Date.valueOf(value.getString("day")));
                scheduling.setStartTime(Time.valueOf(start));
                scheduling.setEndTime(Time.valueOf(end));

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(scheduling.getDay());
                //判断日期是星期几：1-6对应星期一到星期六，0对应星期日
                int week_index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                String[] week = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
                scheduling.setPeriodName(week[week_index] + value.getString("period"));
                schedulingService.insertScheduling(scheduling);
            } else if (value.getString("employeeIds").equals("")) {
                schedulingService.deleteScheduling(value.getString("id"));
            } else {
                schedulingService.updateScheduling(value.getString("employeeIds"), value.getIntValue("id"));
            }
        }
    }

    @PostMapping("/replaceDaySchedule")
    @ResponseBody
    public void replaceDaySchedule(@RequestBody Map map) {
        List<Object> items = (List<Object>) map.get("items");
        Date day = Date.valueOf(map.get("day").toString());
        String storeId = map.get("storeId").toString();
        List<List<String>> periods = setArray(storeId);
        System.out.println(items);

        for (Object item : items) {
            JSONObject value = JSONObject.parseObject(JSONObject.toJSONString(item));
            String[] times = value.getString("period").split("-");
            String start = times[0] + ":00";

            Scheduling scheduling = schedulingService.getByTime(day, Time.valueOf(start), storeId);
            if (scheduling != null) {
                if (value.getString("employeeIds").equals("")) {
                    schedulingService.deleteEmployeeIds(day, Time.valueOf(start), storeId);
                } else {
                    schedulingService.updateEmployeeIds(value.getString("employeeIds"), day, Time.valueOf(start), storeId);
                }
            } else if (!value.getString("employeeIds").equals("")) {
                String end = times[1] + ":00";
                scheduling = new Scheduling();
                scheduling.setStoreId(storeId);
                scheduling.setEmployeeIds(value.getString("employeeIds"));
                scheduling.setDay(day);
                scheduling.setStartTime(Time.valueOf(start));
                scheduling.setEndTime(Time.valueOf(end));

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(scheduling.getDay());
                //判断日期是星期几：1-6对应星期一到星期六，0对应星期日
                int week_index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                String[] week = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
                scheduling.setPeriodName(week[week_index] + value.getString("period"));
                schedulingService.insertScheduling(scheduling);
            }
        }
    }

    private String period(int flag, int index, List<List<String>> periods) {
        if (flag == 1) return periods.get(3).get(index);
        else if (flag == 2) return periods.get(4).get(index - (periods.get(0).size() - periods.get(2).size()));
        else if (flag == 3) return periods.get(1).get(index);
        else if (flag == 4) return periods.get(2).get(index - (periods.get(0).size() - periods.get(2).size()));
        else if (flag == 5) return periods.get(4).get(index);
        else if (flag == 6) return periods.get(2).get(index);

        return null;
    }

    private List<List<String>> setArray(String storeId) {
        List<List<String>> result = new ArrayList<>();
        List<Double> generalTime = (new Methods()).getTime(ruleService.getGeneralRule());
        List<Double> storeTime = (new Methods()).getTime(ruleService.getStoreRule(storeId));
        for (int i = 0; i < storeTime.size(); i++) {
            if (storeTime.get(i) < 0) storeTime.set(i, generalTime.get(i));
        }
        Double before = storeTime.get(0), after = storeTime.get(1);

        periodLen = 26 + (int) (before / 0.5) + (int) (after / 0.5);
        shiftLen = 24 + (int) (before / 0.5) + (int) (after / 0.5);
        List<String> weekdayStart = new ArrayList<>();
        List<String> weekendStart = new ArrayList<>();
        List<String> weekday = new ArrayList<>();
        List<String> weekend = new ArrayList<>();
        List<String> all = new ArrayList<>();

        for (double i = (9 - before); i < (22 + after); i += 0.5) {
            if (i - (int) i != 0) {
                if (i < 21 + after) {
                    weekdayStart.add(((int) i < 10 ? "0" : "") + (int) i + ":30:00");
                    weekday.add(((int) i < 10 ? "0" : "") + (int) i + ":30-" + ((int) i + 1 < 10 ? "0" : "") + ((int) i + 1) + ":00");
                }
                if (i >= 10 - before) {
                    weekendStart.add(((int) i < 10 ? "0" : "") + (int) i + ":30:00");
                    weekend.add(((int) i < 10 ? "0" : "") + (int) i + ":30-" + ((int) i + 1 < 10 ? "0" : "") + ((int) i + 1) + ":00");
                }
                all.add(((int) i < 10 ? "0" : "") + (int) i + ":30-" + ((int) i + 1 < 10 ? "0" : "") + ((int) i + 1) + ":00");
            } else {
                if (i < 21 + after) {
                    weekdayStart.add(((int) i < 10 ? "0" : "") + (int) i + ":00:00");
                    weekday.add(((int) i < 10 ? "0" : "") + (int) i + ":00-" + ((int) i < 10 ? "0" : "") + (int) i + ":30");
                }
                if (i >= 10 - before) {
                    weekendStart.add(((int) i < 10 ? "0" : "") + (int) i + ":00:00");
                    weekend.add(((int) i < 10 ? "0" : "") + (int) i + ":00-" + ((int) i < 10 ? "0" : "") + (int) i + ":30");
                }
                all.add(((int) i < 10 ? "0" : "") + (int) i + ":00-" + ((int) i < 10 ? "0" : "") + ((int) i) + ":30");
            }
        }

//        System.out.println("all:" + all);
//        System.out.println("weekday:" + weekday);
//        System.out.println("weekend:" + weekend);
//        System.out.println("weekdayStart:" + weekdayStart);
//        System.out.println("weekendStart:" + weekendStart);
        result.add(all);
        result.add(weekday);
        result.add(weekend);
        result.add(weekdayStart);
        result.add(weekendStart);

        return result;
    }

    private List<Object> getSchedule(List<Scheduling> work, String storeId) {
        int index = 0;
        List<List<String>> periods = setArray(storeId);
        List<String> all = periods.get(0);
        List<String> weekday = periods.get(1);
        List<String> weekend = periods.get(2);

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

            for (int j = 0; j < periodLen; j++) {
                List<Object> dayWork2;
                if (i < 5 && j >= weekday.size() || i >= 5 && j < all.size() - weekend.size()) {
                    dayWork2 = new ArrayList<>();
                    dayWork2.add(null);
                    dayWork2.add(null);
                    dayWork2.add(all.get(j));
                    dayWork1.add(dayWork2);
                } else {
                    while (index < dayWork.size() && ((dayWork.get(index).getStartTime().toString()).compareTo(period(flagStart, j, periods)) < 0)) index++;
                    if (index < dayWork.size() && ((dayWork.get(index).getStartTime().toString()).equals(period(flagStart, j, periods)))) {
                        dayWork2 = new ArrayList<>();
                        dayWork2.add(employeeService.getEmployees(dayWork.get(index).getEmployeeIds()));
                        dayWork2.add(dayWork.get(index).getId());
                        dayWork2.add(all.get(j));
                        dayWork1.add(dayWork2);
                        index++;
                    } else {
                        dayWork2 = new ArrayList<>();
                        dayWork2.add(new ArrayList<Employee>());
                        dayWork2.add(null);
                        dayWork2.add(all.get(j));
                        dayWork1.add(dayWork2);
                    }
                }
            }
            weekWork.set(i, dayWork1);
        }

        System.out.println(weekWork);
        return weekWork;
    }

    private List<Object> getDaySchedule(List<Scheduling> work, String storeId, int week) {
        int index = 0;
        List<List<String>> periods = setArray(storeId);
        List<String> weekdayStart = periods.get(3);

        List<Employee> newWork = new ArrayList<>();
        List<Object> newWorks = new ArrayList<>();
        List<Object> result = new ArrayList<>();

        int flagStart = week == 1 ? 1 : 5;
        int flag = week == 1 ? 3 : 6;

        for (Scheduling scheduling : work) {
            while (index < weekdayStart.size() && !((scheduling.getStartTime().toString()).equals(period(flagStart, index, periods)))) {
                for (int j = 0; j < 8; j++) {
                    newWork.add(new Employee());
                }
                newWorks.add(newWork);
                newWorks.add(period(flag, index, periods));
                result.add(newWorks);

                newWorks = new ArrayList<>();
                newWork = new ArrayList<>();
                index++;
            }

            if (index < weekdayStart.size() && (scheduling.getStartTime().toString()).equals(period(flagStart, index, periods))) {
                newWork = employeeService.getEmployees(scheduling.getEmployeeIds());
                int need = 8 - newWork.size();
                for (int j = 0; j < need; j++) {
                    newWork.add(new Employee());
                }
                newWorks.add(newWork);
                newWorks.add(period(flag, index, periods));
                result.add(newWorks);

                newWorks = new ArrayList<>();
                newWork = new ArrayList<>();
                index++;
            }
        }

        for (int i = index; i < shiftLen; i++) {
            for (int j = 0; j < 8; j++) {
                newWork.add(new Employee());
            }
            newWorks.add(newWork);
            newWorks.add(period(flag, index, periods));
            result.add(newWorks);

            newWorks = new ArrayList<>();
            newWork = new ArrayList<>();
            index++;
        }

        return result;
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