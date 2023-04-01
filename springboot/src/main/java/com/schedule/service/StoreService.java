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

    /**
     * @author 吴佳丽
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    public List<Store> getAllStore() {
        return storeMapper.getAllStore();
    }

    public Store getStoreById(String storeId) {
        return storeMapper.getStoreById(storeId);
    }
}
