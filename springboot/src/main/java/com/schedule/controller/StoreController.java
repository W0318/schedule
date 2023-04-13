package com.schedule.controller;

import com.schedule.beans.Storebean;
import com.schedule.entity.Store;
import com.schedule.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {
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
    @GetMapping("/{storeId}")
    public int getStorePersons(@PathVariable("storeId") String storeId){
        return storeService.getStorePersons(storeId);
    };
    @GetMapping("/stores/{storeId}")
    public Store getStoreById(@PathVariable("storeId") String storeId){
        return storeService.getStoreById(storeId);
    };
    @GetMapping("/storemessage")
    public List<Storebean> getStoresMessage() {
        return storeService.getStoresMessage();
    }

}

