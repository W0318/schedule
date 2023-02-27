package com.schedule.service;

import com.schedule.mapper.RuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class  RuleService {
    @Autowired
    RuleMapper ruleMapper;
}
