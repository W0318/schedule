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

    public List<Rule> getGeneralRule() {
        return ruleMapper.getGeneralRule();
    }

    public List<Rule> getStoreRule(String storeId) {
        return ruleMapper.getStoreRule(storeId);
    }
}
