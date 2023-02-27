package com.schedule.controller;

import com.schedule.entity.Store;
import com.schedule.mapper.StoreMapper;
import com.schedule.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController  {
    @Autowired
    StoreService storeService;

    /**
     * @author 吴佳丽
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @GetMapping("/all")
    public List<Store> getAllStore() {
        return storeService.getAllStore();
    }

    @Autowired
    private StoreMapper storeMapper;
    @GetMapping("/queryStoreList")
    public  List<Store> queryStoreList(){
        List<Store> storeList = storeMapper.queryStoreList();
        for(Store store:storeList){
            System.out.println(store);
        }
        return storeList;
    }
}
