package com.schedule.controller;

import com.alibaba.fastjson.JSONObject;
import com.schedule.entity.Period;
import com.schedule.method.Methods;
import com.schedule.service.PeriodService;
import com.schedule.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/period")
public class PeriodController {
    @Autowired
    PeriodService periodService;
    @Autowired
    RuleService ruleService;

    List<String> weekday;
    List<String> weekend;
    List<String> all;

    @GetMapping("size/{storeId}")
    public List<Integer> getSize(@PathVariable("storeId") String storeId) {
        List<Integer> size = new ArrayList<>();

        List<Period> periodAll = periodService.getStorePeriodAll(storeId);
        if (periodAll.size() == 0)
            periodAll = periodService.getGeneralPeriodAll();

        List<Period> periodWeekday = periodService.getStorePeriodWeekday(storeId);
        if (periodWeekday.size() == 0)
            periodWeekday = periodService.getGeneralPeriodWeekday();

        List<Period> periodWeekend = periodService.getStorePeriodWeekend(storeId);
        if (periodWeekend.size() == 0)
            periodWeekend = periodService.getGeneralPeriodWeekend();

        size.add(periodAll.size());
        size.add(periodWeekday.size());
        size.add(periodWeekend.size());

        return size;
    }

    @GetMapping("/all/{storeId}")
    public List<Period> getPeriodAll(@PathVariable("storeId") String storeId) {
        List<Period> periodAll = periodService.getStorePeriodAll(storeId);
        if (periodAll.size() == 0)
            periodAll = periodService.getGeneralPeriodAll();

        return periodAll;
    }

    @GetMapping("/day/{storeId}/{flag}")
    public List<Period> getPeriodDay(@PathVariable("storeId") String storeId, @PathVariable("flag") int flag) {
        List<Period> periods;
        if (flag == 1) {
            periods = periodService.getStorePeriodWeekday(storeId);
            if (periods.size() == 0)
                periods = periodService.getGeneralPeriodWeekday();
        } else {
            periods = periodService.getStorePeriodWeekend(storeId);
            if (periods.size() == 0)
                periods = periodService.getGeneralPeriodWeekend();
        }

        return periods;
    }

    @PostMapping("/insert")
    @ResponseBody
    public void insertStoreRule(@RequestBody Map<String, String> map) {
        String storeId = map.get("storeId");
        String ruleType = map.get("ruleType");
        //。。。。

        if (ruleType.equals("开店规则") || ruleType.equals("关店规则")) {
            JSONObject value = JSONObject.parseObject(map.get("ruleValue"));
            Double before = -1.0, after = -1.0;
            if (ruleType.equals("开店规则"))
                before = value.getDoubleValue("before");
            else if (ruleType.equals("关店规则"))
                after = value.getDoubleValue("after");

            List<Double> generalTime = (new Methods()).getTime(ruleService.getGeneralRule());
            if (before == -1.0) before = generalTime.get(0);
            if (after == -1.0) after = generalTime.get(1);
            setArray(before, after);

            List<Period> periodAll = periodService.getStorePeriodAll(storeId);
            List<Period> periodWeekday = periodService.getStorePeriodWeekday(storeId);
            List<Period> periodWeekend = periodService.getStorePeriodWeekend(storeId);

            dealPeriod(periodAll, all, "all", storeId, before, after);
            dealPeriod(periodWeekday, weekday, "weekday", storeId, before, after);
            dealPeriod(periodWeekend, weekend, "weekend", storeId, before, after);
        }
    }

    private void setArray(Double before, Double after) {
        weekday = new ArrayList<>();
        weekend = new ArrayList<>();
        all = new ArrayList<>();

        for (double i = (9 - before); i < (22 + after); i += 0.5) {
            if (i - (int) i != 0) {
                if (i < 21 + after)
                    weekday.add(((int) i < 10 ? "0" : "") + (int) i + ":30-" + ((int) i + 1 < 10 ? "0" : "") + ((int) i + 1) + ":00");
                if (i >= 10 - before)
                    weekend.add(((int) i < 10 ? "0" : "") + (int) i + ":30-" + ((int) i + 1 < 10 ? "0" : "") + ((int) i + 1) + ":00");
                all.add(((int) i < 10 ? "0" : "") + (int) i + ":30-" + ((int) i + 1 < 10 ? "0" : "") + ((int) i + 1) + ":00");
            } else {
                if (i < 21 + after)
                    weekday.add(((int) i < 10 ? "0" : "") + (int) i + ":00-" + ((int) i < 10 ? "0" : "") + (int) i + ":30");
                if (i >= 10 - before)
                    weekend.add(((int) i < 10 ? "0" : "") + (int) i + ":00-" + ((int) i < 10 ? "0" : "") + (int) i + ":30");
                all.add(((int) i < 10 ? "0" : "") + (int) i + ":00-" + ((int) i < 10 ? "0" : "") + ((int) i) + ":30");
            }
        }
    }

    private void dealPeriod(List<Period> period, List<String> expect, String type, String storeId, Double before, Double after) {
        if (period.size() == 0) {
            expect.forEach(item -> periodService.insertPeriod(storeId, item, type));
        } else {
            int index = 0;
            for (Period item : period) {
                String starts = item.getPeriodName().split("-")[0];
                String[] startItems = starts.split(":");
                Double start = Double.parseDouble(startItems[0]) + (startItems[1].equals("30") ? 0.5 : 0);

//                System.out.println(start);
                if (start < 9 - before || start >= 22 + after) {
                    System.out.println(item.getPeriodId());
                    periodService.deletePeriod(item.getPeriodId());
                } else {
//                    System.out.println(item.getPeriodName() + " 比较 " + expect.get(index));
                    while (!item.getPeriodName().equals(expect.get(index))) {
//                        System.out.println("No");
                        periodService.insertPeriod(storeId, expect.get(index), type);
                        index++;
                    }
                    if (item.getPeriodName().equals(expect.get(index))) index++;
                }
            }
//            System.out.println("【index】" + index + " compare 【expect.size()】" + expect.size());
            for (int i = index; i < expect.size(); i++) {
//                System.out.println("end");
                periodService.insertPeriod(storeId, expect.get(i), type);
            }
        }
    }
}