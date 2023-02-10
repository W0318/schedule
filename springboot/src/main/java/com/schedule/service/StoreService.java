package com.schedule.service;

import com.schedule.entity.Store;
import com.schedule.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {
    @Autowired
    StoreMapper storeMapper;

    public List<Store> getAllStore() {
        return storeMapper.getAllStore();
    }
}
