package com.schedule.controller;

import com.schedule.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
