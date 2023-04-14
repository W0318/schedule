package com.schedule.controller;

import com.schedule.method.Methods;
import com.schedule.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rule")
public class RuleController {
    @Autowired
    RuleService ruleService;

    /**
     * @author 张卓童
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @PostMapping("/deleteRuleByStoreId")
    @ResponseBody
    public void deleteRuleByStoreId(@RequestBody Map map) {
        ruleService.deleteRuleByStoreId(map.get("storeId").toString());
    }
}
    @GetMapping("/getPeriods/{storeId}")
    public List<String> getPeriods(@PathVariable("storeId") String storeId) {
        List<String> periods = new ArrayList<>();
        List<Double> generalTime = (new Methods()).getTime(ruleService.getGeneralRule());
        List<Double> storeTime = (new Methods()).getTime(ruleService.getStoreRule(storeId));

        for (int i = 0; i < storeTime.size(); i++) {
            if (storeTime.get(i) < 0) storeTime.set(i, generalTime.get(i));
        }
        for (double i = (8 - storeTime.get(0)); i < (22 + storeTime.get(1)); i += 0.5) {
            if (i - (int) i != 0) {
                periods.add((int) i + ":30-" + ((int) i + 1) + ":00");
            } else {
                periods.add((int) i + ":00-" + ((int) i) + ":30");
            }
        }

        return periods;
    }
}
