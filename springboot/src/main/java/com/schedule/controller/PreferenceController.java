package com.schedule.controller;

import com.schedule.entity.Preference;
import com.schedule.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/preference")
public class PreferenceController {
    @Autowired
    PreferenceService preferenceService;

    /**
     * @author 张卓童
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */

    @PostMapping("/deletePreferenceByEmployeeId")
    @ResponseBody
    public void deletePreferenceByEmployeeId(@RequestBody Map map) {
        preferenceService.deletePreferenceByEmployeeId(map.get("employeeId").toString());
    }

    @PostMapping("/insertPreferenceHaveOne")
    @ResponseBody
    public void insertPreferenceHaveOne(@RequestBody Map map) {
        preferenceService.insertPreferenceHaveOne(map.get("employeeId").toString(), map.get("preferValue").toString());
    }
    @PostMapping("/insertPreferenceHaveTwo")
    @ResponseBody
    public void insertPreferenceHaveTwo(@RequestBody Map map) {
        preferenceService.insertPreferenceHaveTwo(map.get("employeeId").toString(), map.get("preferValue").toString());
    }
    @PostMapping("/insertPreferenceHaveThree")
    @ResponseBody
    public void insertPreferenceHaveThree(@RequestBody Map map) {
//        System.out.println( map.get("preferValue").toString());
        preferenceService.insertPreferenceHaveThree(map.get("employeeId").toString(), map.get("preferValue").toString());
    }

    @GetMapping("/getPreferenceHaveOne/{employeeId}")
    public List<Preference> getPreferenceHaveOne(@PathVariable("employeeId") String employeeId) {
        return preferenceService.getPreferenceHaveOne(employeeId);
    }
    @GetMapping("/getPreferenceHaveTwo/{employeeId}")
    public List<Preference> getPreferenceHaveTwo(@PathVariable("employeeId") String employeeId) {
        return preferenceService.getPreferenceHaveTwo(employeeId);
    }
    @GetMapping("/getPreferenceHaveThree/{employeeId}")
    public List<Preference> getPreferenceHaveThree(@PathVariable("employeeId") String employeeId) {
        return preferenceService.getPreferenceHaveThree(employeeId);
    }

    @PostMapping("/updatePreferenceHaveOne")
    @ResponseBody
    public void updatePreferenceHaveOne(@RequestBody Map map) {
        preferenceService.updatePreferenceHaveOne(map.get("employeeId").toString(), map.get("preferValue").toString());
    }
    @PostMapping("/updatePreferenceHaveTwo")
    @ResponseBody
    public void updatePreferenceHaveTwo(@RequestBody Map map) {
        preferenceService.updatePreferenceHaveTwo(map.get("employeeId").toString(), map.get("preferValue").toString());
    }
    @PostMapping("/updatePreferenceHaveThree")
    @ResponseBody
    public void updatePreferenceHaveThree(@RequestBody Map map) {
        preferenceService.updatePreferenceHaveThree(map.get("employeeId").toString(), map.get("preferValue").toString());
    }
    @PostMapping("/deletePreferenceHaveOne")
    @ResponseBody
    public void deletePreferenceHaveOne(@RequestBody Map map) {
        preferenceService.deletePreferenceHaveOne(map.get("employeeId").toString());
    }
    @PostMapping("/deletePreferenceHaveTwo")
    @ResponseBody
    public void deletePreferenceHaveTwo(@RequestBody Map map) {
        preferenceService.deletePreferenceHaveTwo(map.get("employeeId").toString());
    }
    @PostMapping("/deletePreferenceHaveThree")
    @ResponseBody
    public void deletePreferenceHaveThree(@RequestBody Map map) {
        preferenceService.deletePreferenceHaveThree(map.get("employeeId").toString());
    }
}
