//package com.schedule.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.schedule.algorithm.Calculate;
//import com.schedule.algorithm.Chromosome;
//import com.schedule.algorithm.GeneticAlgorithm;
//import com.schedule.entity.Employee;
//import com.schedule.entity.Preference;
//import com.schedule.entity.Rule;
//import com.schedule.entity.Store;
//import com.schedule.service.EmployeeService;
//import com.schedule.service.PreferenceService;
//import com.schedule.service.RuleService;
//import com.schedule.service.StoreService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * bug备注：
// * 前端对工作时间偏好做一个判断，偏好工作时间段开始时间和结束时间不能相同
// * <p>
// * 不是bug的疑问：
// * 偏好缺省情况
// */
//
//@RestController
//@RequestMapping("/autoSchedule")
//public class AutoScheduling {
//    @Autowired
//    EmployeeService employeeService;
//    @Autowired
//    PreferenceService preferenceService;
//    @Autowired
//    RuleService ruleService;
//    @Autowired
//    StoreService storeService;
//
//    HashMap<String, Boolean> ruleExist = new HashMap<String, Boolean>() {{
//        put("开店规则", false);
//        put("关店规则", false);
//        put("客流规则", false);
//        put("班次规则", false);
//    }};
//
//    @GetMapping("/{storeId}")
//    public List<Object> AutoSchedule(@PathVariable("storeId") String storeId) {
//        /**
//         * 1`根据storeId获取所有员工,并将偏好数值化存入对象
//         */
//        List<Employee> employees = employeeService.getAllEmployee(storeId);
//        for (int i = 0; i < employees.size(); i++) {
//            Employee newE = employees.get(i);
//
//            //一位员工的所有偏好
//            List<Preference> aEPrefers = preferenceService.getEmployeePrefer(newE.getEmployeeId());
//            newE.setPreference(getPreferCode(aEPrefers));
//            employees.set(i, newE);
//        }
//
//        /**
//         * 2`获取店铺规则，无规则时调用通用规则
//         */
//        Store generalRule = getRule(storeId, ruleService.getGeneralRule(), false);   //通用规则
//        Store storeRule = getRule(storeId, ruleService.getStoreRule(storeId), true);   //店铺个别规则
//        storeRule = getStoreRule(storeRule, generalRule);   //未设置规则部分采用通用规则
//        System.out.println(Math.ceil(1 / 2));
//        /**
//         * 3`
//         */
//
//        int days = 7;
////        for (int day = 1; day <= days; day++) {
////            use_GA(employees, storeRule, day);//哪天
////        }
//        ArrayList<Chromosome> aDaySchedule = use_GA(employees, storeRule, 1);
//        return getScheduleData(aDaySchedule, employees);
//    }
//
//    /**
//     * 将数据库中存储的偏好转换为List<Integer>列表，并数字化
//     *
//     * @param prefers 数据库偏好列表
//     * @return 一位员工的偏好数值
//     */
//    public ArrayList<Integer> getPreferCode(List<Preference> prefers) {
//        ArrayList<Integer> preferCode = new ArrayList<>();
//        // 初始化偏好数值列表全为0
//        // 没有偏好的下标的值全置为0，表示某偏好类型下标对应含义无偏好
//        for (int i = 0; i < 40; i++) {   //下标为0的位置表示有多少种偏好
//            preferCode.add(0);
//        }
//
//        for (Preference prefer : prefers) {   //prefer数据库一行，即一位员工一种偏好
//            if ("工作日偏好".equals(prefer.getPreferType())) {
//                preferCode.set(0, preferCode.get(0) + 1);
//
//                //一位员工一种偏好的偏好值数组
//                String[] values = (prefer.getPreferValue()).split(",");
//                for (String value : values) {   //下标1-7位工作日偏好，值为1时偏好，值为0时不偏好
//                    preferCode.set(Integer.parseInt(value), 1);
//                }
//            } else if (("工作时间偏好").equals(prefer.getPreferType())) {
//                preferCode.set(0, preferCode.get(0) + 2);
//
//                //将所有开始时间/结束时间所对应下标的值置为1
//                for (String period : (prefer.getPreferValue()).split(",")) {   //分割多选时间段
//                    String[] start_end = (period).split("-");   //分割时间段
//                    String[] start_hour_minute = (start_end[0]).split(":");   //分割小时和分钟
//                    String[] end_hour_minute = (start_end[1]).split(":");
//                    int start_hour = Integer.parseInt(start_hour_minute[0]);
//                    int end_hour = Integer.parseInt(end_hour_minute[0]);
//
//                    if ("30".equals(start_hour_minute[1])) {
//                        preferCode.set((start_hour - 7) * 2 + 9, 1);
//                    } else {
//                        preferCode.set((start_hour - 7) * 2 + 8, 1);
//                    }
//                    if ("30".equals(end_hour_minute[1])) {
//                        preferCode.set((end_hour - 7) * 2 + 9, 1);
//                    } else {
//                        preferCode.set((end_hour - 7) * 2 + 8, 1);
//                    }
//                }
//                for (int i = 8; i <= 35; i++) {   //工作日代表7-24（包括开店前最多2小时，关店后最多2小时）
//                    if (preferCode.get(i) == 1) {   //将开始时间-结束时间之间的值全置为1
//                        while ((++i) <= 35 && preferCode.get(i) != 1) {
//                            preferCode.set(i, 1);
//                        }
//                    }
//                }
//            } else if ("班次时长偏好".equals(prefer.getPreferType())) {   ////////没做缺省
//                preferCode.set(0, preferCode.get(0) + 4);
//
//                JSONObject value = JSONObject.parseObject(prefer.getPreferValue());
//                String day = value.get("day").toString();
//                String week = value.get("week").toString();
//                int day_max = 8, week_max = 40;
//                if (!"".equals(day) && Integer.parseInt(day) < day_max) {
//                    day_max = Integer.parseInt(day);
//                }
//                if (!"".equals(week) && Integer.parseInt(week) < week_max) {
//                    week_max = Integer.parseInt(week);
//                }
//                preferCode.set(36, day_max);
//                preferCode.set(38, week_max);
//            }
//        }
//
//        return preferCode;
//    }
//
//    /**
//     * 将数据库存储值数值化
//     *
//     * @param storeId
//     * @param rules
//     * @return
//     */
//    public Store getRule(String storeId, List<Rule> rules, Boolean isIndividual) {
//        Store store = storeService.getStoreById(storeId);
//
//        for (Rule rule : rules) {
//            JSONObject value = JSONObject.parseObject(rule.getRuleValue());
//
//            ArrayList<String> positions = new ArrayList<>();
//            if (("开店规则".equals(rule.getRuleType()) || "关店规则".equals(rule.getRuleType()) || "班次规则".equals(rule.getRuleType()))
//                    && !"".equals(value.getString("position"))) {
//                //设置员工职位限制
//                String[] pos = value.getString("position").split(",");
//                List pos1 = Arrays.asList(pos);
//                positions = new ArrayList<>(pos1);
//            }
//
//            if ("开店规则".equals(rule.getRuleType())) {
//                if (isIndividual)
//                    ruleExist.put("开店规则", true);
//
//                //设置开店前所需时间
//                store.setPreTimeNeed(value.getFloatValue("before"));
//                //设置开店前所需员工公式
//                store.setPreEFormula(value.getString("formula"));
//                //设置开店前所需员工，最少1人
//                int formulaNeed = (int) Math.ceil((new Calculate()).dealExp(value.getString("formula"), store.getSize()));
//                store.setPreENeed((formulaNeed > 1) ? formulaNeed : 1);
//                //设置开店前员工职位限制
//                store.setPrePosLimit(positions);
//            } else if ("关店规则".equals(rule.getRuleType())) {
//                if (isIndividual)
//                    ruleExist.put("关店规则", true);
//
//                //设置关店后所需时间
//                store.setAftTimeNeed(value.getFloatValue("after"));
//                //设置关店后所需员工公式
//                store.setAftEFormula(value.getString("formula"));
//                //设置关店后所需员工，最少1人
//                int formulaNeed = (int) Math.ceil((new Calculate()).dealExp(value.getString("formula"), store.getSize()));
//                store.setAftENeed((formulaNeed > 1) ? formulaNeed : 1);
//                //设置关店后员工职位限制
//                store.setAftPosLimit(positions);
//            } else if ("客流规则".equals(rule.getRuleType())) {
//                if (isIndividual)
//                    ruleExist.put("客流规则", true);
//
//                //设置客流量规则公式
//                store.setFlowFormula(value.getString("formula"));
//                //设置客流最少所员工
//                store.setMinNeed(value.getIntValue("minNeed"));
//            } else if ("班次规则".equals(rule.getRuleType())) {
//                if (isIndividual)
//                    ruleExist.put("班次规则", true);
//
//                //设置日常班次员工职位限制
//                store.setPosLimit(positions);
//            }
//        }
//
//        return store;
//    }
//
//    /**
//     * 店铺未设置个别规则，则使用通用规则
//     *
//     * @param storeRule
//     * @param generalRule
//     * @return
//     */
//    public Store getStoreRule(Store storeRule, Store generalRule) {
//        for (Object key : ruleExist.keySet()) {
//            if (ruleExist.get(key) == false) {
//                if ("开店规则".equals(key)) {
//                    ruleExist.put("开店规则", true);
//
//                    //设置开店前所需时间
//                    storeRule.setPreTimeNeed(generalRule.getPreTimeNeed());
//                    //设置开店前所需员工公式
//                    storeRule.setPreEFormula(generalRule.getPreEFormula());
//                    //设置开店前所需员工，最少1人
//                    int formulaNeed = (int) Math.ceil((new Calculate()).dealExp(generalRule.getPreEFormula(), storeRule.getSize()));
//                    storeRule.setPreENeed((formulaNeed > 1) ? formulaNeed : 1);
//                    //设置开店前员工职位限制
//                    storeRule.setPrePosLimit(generalRule.getPrePosLimit());
//                } else if ("关店规则".equals(key)) {
//                    ruleExist.put("关店规则", true);
//
//                    //设置关店后所需时间
//                    storeRule.setAftTimeNeed(generalRule.getAftTimeNeed());
//                    //设置关店后所需员工公式
//                    storeRule.setAftEFormula(generalRule.getAftEFormula());
//                    //设置关店后所需员工，最少1人
//                    int formulaNeed = (int) Math.ceil((new Calculate()).dealExp(generalRule.getAftEFormula(), storeRule.getSize()));
//                    storeRule.setAftENeed((formulaNeed > 1) ? formulaNeed : 1);
//                    //设置关店后员工职位限制
//                    storeRule.setAftPosLimit(generalRule.getAftPosLimit());
//                } else if ("客流规则".equals(key)) {
//                    ruleExist.put("客流规则", true);
//
//                    //设置客流量规则公式
//                    storeRule.setFlowFormula(generalRule.getFlowFormula());
//                    //设置客流最少所员工
//                    storeRule.setMinNeed(generalRule.getMinNeed());
//                } else if ("班次规则".equals(key)) {
//                    ruleExist.put("班次规则", true);
//
//                    //设置日常班次员工职位限制
//                    storeRule.setPosLimit(generalRule.getPosLimit());
//                }
//            }
//        }
//
//        return storeRule;
//    }
//
//    //调用GA算法排班
//    public ArrayList<Chromosome> use_GA(List<Employee> employees, Store storeRule, int day) {
//        int pre = (int) (storeRule.getPreTimeNeed() / 0.5);   //半小时一班
//        int aft = (int) (storeRule.getAftTimeNeed() / 0.5);
//        int len = pre + 24 + aft;
//
//        ArrayList<Chromosome> chromos = new ArrayList<>();
//        for (int i = 0; i < employees.size(); i++) {
//            Employee employee = employees.get(i);
//            Chromosome chromo = new Chromosome(len);
//            employee.setChromo(chromo);
//            employees.set(i, employee);
//
//            chromos.add(chromo);
//        }
//
//        GeneticAlgorithm ga = new GeneticAlgorithm(chromos);
//        ga.setLen(len);
//        ga.setScale(employees.size());
//
//        /**
//         * 传客流模型
//         */
//        //客流模型初始化
//        ArrayList<Double> p_flows = new ArrayList<>();
//        p_flows.add(0.0);
//        p_flows.add(1.3);
//        p_flows.add(5.7);
//        p_flows.add(11.1);
//        p_flows.add(13.4);
//        p_flows.add(13.3);
//        p_flows.add(17.3);
//        p_flows.add(22.8);
//        p_flows.add(26.9);
//        p_flows.add(21.6);
//        p_flows.add(23.4);
//        p_flows.add(28.7);
//        p_flows.add(29.3);
//        p_flows.add(26.4);
//        p_flows.add(22.1);
//        p_flows.add(17.2);
//        p_flows.add(15.2);
//        p_flows.add(13.3);
//        p_flows.add(11.6);
//        p_flows.add(7.2);
//        p_flows.add(5.6);
//        p_flows.add(2.1);
//        p_flows.add(1.3);
//        p_flows.add(1.0);
//        //一天的预测，一个月就重复调用就行了，都是一样的数据！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
//
//        //计算需要人数
//        int preENeed = storeRule.getPreENeed();
//        int minNeed = storeRule.getMinNeed();
//        int aftENeed = storeRule.getAftENeed();
//
//        ArrayList<Integer> PassFlowNum = new ArrayList<>();
//        for (double p_flow : p_flows) {
//            int formulaNum = (int) Math.ceil((new Calculate()).dealExp(storeRule.getFlowFormula(), p_flow));
//            PassFlowNum.add(formulaNum > minNeed ? formulaNum : minNeed);
//        }
//
//        ArrayList<ArrayList<Integer>> prefers = new ArrayList<>();
//        for (Employee employee : employees) {
//            prefers.add(employee.getPreference());
//        }
//
//        //开始公式计算并传参进行GA,完成排班
//        chromos = ga.GA(preENeed, aftENeed, PassFlowNum, pre, aft, day, prefers);
//
//        return chromos;
//    }
//
//    public List<Object> getScheduleData(ArrayList<Chromosome> chromos, List<Employee> employees) {
//        List<Object> data = new ArrayList<>();
//
//        for (int i = 0; i < chromos.get(0).getLen(); i++) {
////            List<Employee> employees1 = new ArrayList<>();
//            List<String> employees1 = new ArrayList<>();
//            for (int j = 0; j < chromos.size(); j++) {
//                if (chromos.get(j).chromosome.get(i) == 1) {   //有班次
//                    employees1.add(employees.get(j).getEmployeeName());
//                }
//            }
//            data.add(employees1);
//        }
//
//        return data;
//    }
//}
