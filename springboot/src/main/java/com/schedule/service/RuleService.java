package com.schedule.service;

import com.schedule.entity.Rule;
import com.schedule.mapper.RuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleService {
    @Autowired
    RuleMapper ruleMapper;

    /**
     * @author 张卓童
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    public void deleteRuleByStoreId(String storeId) {
        ruleMapper.deleteRuleByStoreId(storeId);
    }
    public List<Rule> getGeneralRule() {
        return ruleMapper.getGeneralRule();
    }

    public List<Rule> getStoreRule(String storeId) {
        return ruleMapper.getStoreRule(storeId);
    }
}
