package com.schedule.service;

import com.schedule.mapper.PreferenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreferenceService {
    @Autowired
    PreferenceMapper preferenceMapper;
}
