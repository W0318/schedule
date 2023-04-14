package com.schedule.controller;

import com.schedule.entity.Rule;
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

    /**
     * @author 尹日尧
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */

    //通用规则
    @GetMapping("/allRule")
    public List<Rule> getRule() {
        return ruleService.getRule();
    }

    @GetMapping("/openshopRule")
    public List<Rule> getOpenshopRule() {
        return ruleService.getOpenshopRule();
    }

    @GetMapping("/closeshopRule")
    public List<Rule> getCloseshopRule() {
        return ruleService.getCloseshopRule();
    }

    @GetMapping("/customerRule")
    public List<Rule> getCustomerRule() {
        return ruleService.getCustomerRule();
    }

    @GetMapping("/classesRule")
    public List<Rule> getClassesRule() {
        return ruleService.getClassesRule();
    }

    @PostMapping("/updateCloseshop")
    @ResponseBody
    public void updateCloseshop(@RequestBody Map map) {
        ruleService.updateCloseshop(map.get("ruleValue").toString());
    }

    @PostMapping("/updateOpenshop")
    @ResponseBody
    public void updateOpenshop(@RequestBody Map map) {
        ruleService.updateOpenshop(map.get("ruleValue").toString());
    }

    @PostMapping("/updateCustomer")
    @ResponseBody
    public void updateCustomer(@RequestBody Map map) {
        ruleService.updateCustomer(map.get("ruleValue").toString());
    }

    @PostMapping("/updateClasses")
    @ResponseBody
    public void updateClasses(@RequestBody Map map) {
        ruleService.updateClasses(map.get("ruleValue").toString());
    }

    @PostMapping("/updateOpenbyStoreId")
    @ResponseBody
    public void updateOpenByStoreId(@RequestBody Map map) {
        ruleService.updateOpenByStoreId(map.get("storeId").toString(), map.get("ruleValue").toString());
    }

    @PostMapping("/updateClosebyStoreId")
    @ResponseBody
    public void updateCloseByStoreId(@RequestBody Map map) {
        ruleService.updateCloseByStoreId(map.get("storeId").toString(), map.get("ruleValue").toString());
    }

    @PostMapping("/updateCustomerbyStoreId")
    @ResponseBody
    public void updateCustomerByStoreId(@RequestBody Map map) {
        ruleService.updateCustomerByStoreId(map.get("storeId").toString(), map.get("ruleValue").toString());
    }

    @PostMapping("/updateClassesbyStoreId")
    @ResponseBody
    public void updateClassesByStoreId(@RequestBody Map map) {
        ruleService.updateClassesByStoreId(map.get("storeId").toString(), map.get("ruleValue").toString());
    }

    @GetMapping("/getOpenbyStoreId/{storeId}")
    public List<Rule> getOpenByStoreId(@PathVariable("storeId") String storeId) {
        return ruleService.getOpenByStoreId(storeId);
    }

    @GetMapping("/getClosebyStoreId/{storeId}")
    public List<Rule> getCloseByStoreId(@PathVariable("storeId") String storeId) {
        return ruleService.getCloseByStoreId(storeId);
    }

    @GetMapping("/getCustomerbyStoreId/{storeId}")
    public List<Rule> getCustomerByStoreId(@PathVariable("storeId") String storeId) {
        return ruleService.getCustomerByStoreId(storeId);
    }

    @GetMapping("/getClassesbyStoreId/{storeId}")
    public List<Rule> getClassesByStoreId(@PathVariable("storeId") String storeId) {
        return ruleService.getClassesByStoreId(storeId);
    }

    @PostMapping("/insertOpen")
    @ResponseBody
    public void insertOpen(@RequestBody Map map) {
        ruleService.insertOpen(map.get("ruleId").toString(), map.get("storeId").toString(), map.get("ruleValue").toString());
    }

    @PostMapping("/insertClose")
    @ResponseBody
    public void insertClose(@RequestBody Map map) {
        ruleService.insertClose(map.get("ruleId").toString(), map.get("storeId").toString(), map.get("ruleValue").toString());
    }

    @PostMapping("/insertCustomer")
    @ResponseBody
    public void insertCustomer(@RequestBody Map map) {
        ruleService.insertCustomer(map.get("ruleId").toString(), map.get("storeId").toString(), map.get("ruleValue").toString());
    }

    @PostMapping("/insertClasses")
    @ResponseBody
    public void insertClasses(@RequestBody Map map) {
        ruleService.insertClasses(map.get("ruleId").toString(), map.get("storeId").toString(), map.get("ruleValue").toString());
    }

    @GetMapping("/all")
    public List<Rule> getAllRule() {
        return ruleService.getAllRule();
    }
}
