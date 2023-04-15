package com.schedule.method;

import com.alibaba.fastjson.JSONObject;
import com.schedule.entity.Rule;

import java.util.ArrayList;
import java.util.List;

public class Methods {
    /**
     * 获取开店前和关店后所需时间
     *
     * @param rules
     * @return
     */
    public List<Double> getTime(List<Rule> rules) {
        List<Double> time = new ArrayList<>();
        time.add(-1.0);
        time.add(-1.0);

        for (Rule rule : rules) {
            JSONObject value = JSONObject.parseObject(rule.getRuleValue());

            if ("开店规则".equals(rule.getRuleType())) {
                time.set(0, value.getDoubleValue("before"));
            } else if ("关店规则".equals(rule.getRuleType())) {
                time.set(1, value.getDoubleValue("after"));
            }
        }

        return time;
    }
}
