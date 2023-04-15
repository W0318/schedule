package com.schedule.controller;

import com.alibaba.fastjson.JSONObject;
import com.schedule.algorithm.Calculate;
import com.schedule.algorithm.Chromosome;
import com.schedule.algorithm.GeneticAlgorithm;
import com.schedule.beans.EmployeeBan;
import com.schedule.config.MyCalendar;
import com.schedule.entity.Employee;
import com.schedule.entity.Preference;
import com.schedule.entity.Rule;
import com.schedule.entity.Store;
import com.schedule.method.Methods;
import com.schedule.service.EmployeeService;
import com.schedule.service.PreferenceService;
import com.schedule.service.RuleService;
import com.schedule.service.StoreService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.schedule.config.RankTransfer.transpose;
//import static com.schedule.config.RankTransfer.show;

/**
 * bug备注：
 * 前端对工作时间偏好做一个判断，偏好工作时间段开始时间和结束时间不能相同
 * <p>
 * 不是bug的疑问：
 * 偏好缺省情况
 */

@RestController
@RequestMapping("/autoSchedule")
public class AutoScheduling {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    PreferenceService preferenceService;
    @Autowired
    RuleService ruleService;
    @Autowired
    StoreService storeService;
    HashMap<String, Boolean> ruleExist = new HashMap<String, Boolean>() {{
        put("开店规则", false);
        put("关店规则", false);
        put("客流规则", false);
        put("班次规则", false);
    }};

    List<List<String>> setArray(String storeId) {
        List<List<String>> result = new ArrayList<>();
        List<Double> generalTime = (new Methods()).getTime(ruleService.getGeneralRule());
        List<Double> storeTime = (new Methods()).getTime(ruleService.getStoreRule(storeId));
        for (int i = 0; i < storeTime.size(); i++) {
            if (storeTime.get(i) < 0) storeTime.set(i, generalTime.get(i));
        }
        Double before = storeTime.get(0), after = storeTime.get(1);

//        int periodLen = 26 + (int) (before / 0.5) + (int) (after / 0.5);
//        int shiftLen = 24 + (int) (before / 0.5) + (int) (after / 0.5);
        List<String> weekdayStart = new ArrayList<>();
        List<String> weekendStart = new ArrayList<>();
        List<String> weekday = new ArrayList<>();
        List<String> weekend = new ArrayList<>();
        List<String> all = new ArrayList<>();

        for (double i = (9 - before); i < (22 + after); i += 0.5) {
            if (i - (int) i != 0) {
                if (i < 21 + after) {
                    weekdayStart.add(((int) i < 10 ? "0" : "") + (int) i + ":30:00");
                    weekday.add(((int) i < 10 ? "0" : "") + (int) i + ":30-" + ((int) i + 1 < 10 ? "0" : "") + ((int) i + 1) + ":00");
                }
                if (i >= 10 - before) {
                    weekendStart.add(((int) i < 10 ? "0" : "") + (int) i + ":30:00");
                    weekend.add(((int) i < 10 ? "0" : "") + (int) i + ":30-" + ((int) i + 1 < 10 ? "0" : "") + ((int) i + 1) + ":00");
                }
                all.add(((int) i < 10 ? "0" : "") + (int) i + ":30-" + ((int) i + 1 < 10 ? "0" : "") + ((int) i + 1) + ":00");
            } else {
                if (i < 21 + after) {
                    weekdayStart.add(((int) i < 10 ? "0" : "") + (int) i + ":00:00");
                    weekday.add(((int) i < 10 ? "0" : "") + (int) i + ":00-" + ((int) i < 10 ? "0" : "") + (int) i + ":30");
                }
                if (i >= 10 - before) {
                    weekendStart.add(((int) i < 10 ? "0" : "") + (int) i + ":00:00");
                    weekend.add(((int) i < 10 ? "0" : "") + (int) i + ":00-" + ((int) i < 10 ? "0" : "") + (int) i + ":30");
                }
                all.add(((int) i < 10 ? "0" : "") + (int) i + ":00-" + ((int) i < 10 ? "0" : "") + ((int) i) + ":30");
            }
        }

//        System.out.println("all:" + all);
//        System.out.println("weekday:" + weekday);
//        System.out.println("weekend:" + weekend);
//        System.out.println("weekdayStart:" + weekdayStart);
//        System.out.println("weekendStart:" + weekendStart);
        result.add(weekday);
        result.add(weekend);
        result.add(all);
//        result.add(weekdayStart);
//        result.add(weekendStart);

        return result;
    }

    @SneakyThrows
    @GetMapping("/{storeId}/{limited}/{Monday}")
    public Object[][][] AutoSchedule(@PathVariable("storeId") String storeId, @PathVariable("limited") String limited, @PathVariable("Monday") String Monday) {
        /**
         * 1`根据storeId获取所有员工,并将偏好数值化存入对象
         */
        //标记店铺个体规则
        ruleExist.put("开店规则", false);
        ruleExist.put("关店规则", false);
        ruleExist.put("客流规则", false);
        ruleExist.put("班次规则", false);

        List<Employee> employees = employeeService.getAllEmployee(storeId);
        List<Employee> newEmployees = new ArrayList<>();
        System.out.println(employees.size());
        for (int i = 0; i < employees.size(); i++) {
            Employee newE = employees.get(i);

            //一位员工的所有偏好
            List<Preference> aEPrefers = preferenceService.getEmployeePrefer(newE.getEmployeeId());
            newE.setPreferCode(aEPrefers);
            System.out.println(newE.getPreferCode());
            if (i < Integer.parseInt(limited)) newEmployees.add(newE);
            employees.set(i, newE);
        }

        /**
         * 2`获取店铺规则，无规则时调用通用规则
         */
        Store generalRule = getRule(storeId, ruleService.getGeneralRule(), false);   //通用规则
        Store storeRule = getRule(storeId, ruleService.getStoreRule(storeId), true);   //店铺个别规则
        storeRule = getStoreRule(storeRule, generalRule);   //未设置规则部分采用通用规则
        System.out.println(Math.ceil(1 / 2));

        boolean flag = true;
        int days = 7;
        Date ddtate = MyCalendar.StrToDate(Monday);
        for (int day = 0; day < days; day++) {
            if (use_GA(newEmployees, storeRule, MyCalendar.DateToStr(MyCalendar.addDays(ddtate, day)), day)) {
                System.out.println("至味null");
                flag = false;
                break;
            }

        }

        ArrayList<EmployeeBan> backEmployee = new ArrayList<>();
        for (Employee e : newEmployees) {
            EmployeeBan a = new EmployeeBan();
            a.setEmployeeId(e.getEmployeeId());
            a.setEmployeeName(e.getEmployeeName());
            a.setStoreId(e.getStoreId());
            a.setEmail(e.getEmail());
            a.setPhone(e.getPhone());
            a.setPosition(e.getPosition());
            a.setRoot(e.getRoot());
            backEmployee.add(a);
        }

        int len = newEmployees.get(0).getChromo().get(0).size();
        List<List<String>> peroid = new ArrayList<>(setArray(storeId));
        Object[][][] myArray = new Object[7][len][newEmployees.size()];
        Object[][][] returnArray = new Object[7][peroid.get(2).size()][3];
        Object[][] array_a = new Object[1][];
        System.out.println("peroid" + peroid);
        if (flag) {
            for (int j = 0; j < 7; j++) {
                for (int i = 0; i < newEmployees.size(); i++) {
                    array_a[0] = newEmployees.get(i).getChromo().get(j).toArray();
                    Object[][] transposeMatrix = transpose(array_a);
                    for (int k = 0; k < len; k++) {
                        myArray[j][k][i] = transposeMatrix[k][0];
                    }
                }
            }

            for (int j = 0; j < 7; j++) {
                System.out.println("Day" + j + " ");
                if (j < 5) {
                    for (int k = peroid.get(0).size(); k < peroid.get(2).size(); k++) {
                        returnArray[j][k][0] = null;
                        returnArray[j][k][1] = null;
                        returnArray[j][k][2] = peroid.get(2).get(k);
                    }
                } else {
                    for (int k = 0; k < peroid.get(2).size() - peroid.get(1).size(); k++) {
                        returnArray[j][k][0] = null;
                        returnArray[j][k][1] = null;
                        returnArray[j][k][2] = peroid.get(2).get(k);
                    }
                }
                int start = j < 5 ? 0 : peroid.get(2).size() - peroid.get(1).size();
                for (int k = start, l = 0; k < len + start; k++, l++) {
                    System.out.print("基因组" + k + " ");
                    ArrayList<EmployeeBan> myEmployeeBans = new ArrayList<>();
                    for (int i = 0; i < newEmployees.size(); i++) {
                        System.out.print(myArray[j][l][i] + " ");
                        if (Objects.equals(myArray[j][l][i].toString(), "1")) {
                            myEmployeeBans.add(backEmployee.get(i));
                        }

                    }
                    returnArray[j][k][0] = myEmployeeBans;
                    returnArray[j][k][1] = null;
                    if (j < 5) {
                        returnArray[j][k][2] = peroid.get(0).get(l);
                    } else {
                        returnArray[j][k][2] = peroid.get(1).get(l);
                    }
                    System.out.println();
                }
                System.out.println();
            }


//        for (int j = 0; j < 7; j++) {
//            System.out.println("Day"+j+" ");
//            for(int k = 0;k<len;k++){
//                System.out.print("基因组"+k+" ");
//                for (int i = 0; i < 2; i++) {
//                    System.out.print(returnArray[j][k][i]+" ");
//                }
//                System.out.println();
//            }
//            System.out.println();
//        }
        }

        System.out.println(flag);
        System.out.println(returnArray[0][0][0] == null);
        return returnArray;
    }

    /**
     * 将数据库存储值数值化
     *
     * @param storeId
     * @param rules
     * @return
     */
    public Store getRule(String storeId, List<Rule> rules, Boolean isIndividual) {
        Store store = storeService.getStoreById(storeId);

        for (Rule rule : rules) {
            JSONObject value = JSONObject.parseObject(rule.getRuleValue());

            ArrayList<String> positions = new ArrayList<>();
            if (("开店规则".equals(rule.getRuleType()) || "关店规则".equals(rule.getRuleType()) || "班次规则".equals(rule.getRuleType()))
                    && !"".equals(value.getString("position"))) {
                //设置员工职位限制
                String[] pos = value.getString("position").split(",");
                List<String> pos1 = Arrays.asList(pos);
                positions = new ArrayList<>(pos1);
            }

            if ("开店规则".equals(rule.getRuleType())) {
                if (isIndividual)
                    ruleExist.put("开店规则", true);

                //设置开店前所需时间
                store.setPreTimeNeed(value.getFloatValue("before"));
                //设置开店前所需员工公式
                store.setPreEFormula(value.getString("formula"));
                //设置开店前所需员工，最少1人
                int formulaNeed = (int) Math.ceil((new Calculate()).dealExp(value.getString("formula"), store.getSize()));
                store.setPreENeed((formulaNeed > 1) ? formulaNeed : 1);
                //设置开店前员工职位限制
                store.setPrePosLimit(positions);
            } else if ("关店规则".equals(rule.getRuleType())) {
                if (isIndividual)
                    ruleExist.put("关店规则", true);

                //设置关店后所需时间
                store.setAftTimeNeed(value.getFloatValue("after"));
                //设置关店后所需员工公式
                store.setAftEFormula(value.getString("formula"));
                //设置关店后所需员工，最少1人
                int formulaNeed = (int) Math.ceil((new Calculate()).dealExp(value.getString("formula"), store.getSize()));
                store.setAftENeed((formulaNeed > 1) ? formulaNeed : 1);
                //设置关店后员工职位限制
                store.setAftPosLimit(positions);
            } else if ("客流规则".equals(rule.getRuleType())) {
                if (isIndividual)
                    ruleExist.put("客流规则", true);

                //设置客流量规则公式
                store.setFlowFormula(value.getString("formula"));
                //设置客流最少所员工
                store.setMinNeed(value.getIntValue("minNeed"));
            } else if ("班次规则".equals(rule.getRuleType())) {
                if (isIndividual)
                    ruleExist.put("班次规则", true);

                //设置日常班次员工职位限制
                store.setPosLimit(positions);
            }
        }

        return store;
    }

    /**
     * 店铺未设置个别规则，则使用通用规则
     *
     * @param storeRule
     * @param generalRule
     * @return
     */
    public Store getStoreRule(Store storeRule, Store generalRule) {
        for (Object key : ruleExist.keySet()) {
            if (ruleExist.get(key) == false) {
                if ("开店规则".equals(key)) {
                    ruleExist.put("开店规则", true);

                    //设置开店前所需时间
                    storeRule.setPreTimeNeed(generalRule.getPreTimeNeed());
                    //设置开店前所需员工公式
                    storeRule.setPreEFormula(generalRule.getPreEFormula());
                    //设置开店前所需员工，最少1人
                    int formulaNeed = (int) Math.ceil((new Calculate()).dealExp(generalRule.getPreEFormula(), storeRule.getSize()));
                    storeRule.setPreENeed((formulaNeed > 1) ? formulaNeed : 1);
                    //设置开店前员工职位限制
                    storeRule.setPrePosLimit(generalRule.getPrePosLimit());
                } else if ("关店规则".equals(key)) {
                    ruleExist.put("关店规则", true);

                    //设置关店后所需时间
                    storeRule.setAftTimeNeed(generalRule.getAftTimeNeed());
                    //设置关店后所需员工公式
                    storeRule.setAftEFormula(generalRule.getAftEFormula());
                    //设置关店后所需员工，最少1人
                    int formulaNeed = (int) Math.ceil((new Calculate()).dealExp(generalRule.getAftEFormula(), storeRule.getSize()));
                    storeRule.setAftENeed((formulaNeed > 1) ? formulaNeed : 1);
                    //设置关店后员工职位限制
                    storeRule.setAftPosLimit(generalRule.getAftPosLimit());
                } else if ("客流规则".equals(key)) {
                    ruleExist.put("客流规则", true);

                    //设置客流量规则公式
                    storeRule.setFlowFormula(generalRule.getFlowFormula());
                    //设置客流最少所员工
                    storeRule.setMinNeed(generalRule.getMinNeed());
                } else if ("班次规则".equals(key)) {
                    ruleExist.put("班次规则", true);

                    //设置日常班次员工职位限制
                    storeRule.setPosLimit(generalRule.getPosLimit());
                }
            }
        }

        return storeRule;
    }


    //调用GA算法排班
    public boolean use_GA(List<Employee> employees, Store storeRule, String date, int day) {
        storeRule.setFlowNeeds(date, day);   //生成班次及班次所需人数
        int len = (int) (storeRule.getPreTimeNeed() / 0.5) + storeRule.getFlowNeeds().size() + (int) (storeRule.getAftTimeNeed() / 0.5);   //总班次，包括开店前和关店后2个班次

        System.out.println("storeRule.getPreTimeNeed()/0.5" + storeRule.getPreTimeNeed() / 0.5);
        //计算需要人数
        int preENeed = storeRule.getPreENeed();   //开店前所需人数
        int aftENeed = storeRule.getAftENeed();   //关店后所需人数

        ArrayList<Chromosome> chromos1 = new ArrayList<>();
        for (int i = 0; i < employees.size(); i++) {
//            Employee employee = employees.get(i);
//            ArrayList<Chromosome> chromos = new ArrayList<>();
//            for(int j = 0;j<7;j++){
//                chromos.add(new Chromosome(len));
//            }
//            employee.setChromo(chromos);
//            employees.set(i, employee);

            chromos1.add(new Chromosome(len));
        }

        GeneticAlgorithm ga = new GeneticAlgorithm(chromos1);
        ga.setLen(len);   //设置基因长度
        ga.setScale(employees.size());   //设置种群规模

        //            System.out.println(employee.getPreferCode());
        //种群偏好数组
        ArrayList<Employee> preferCodes = new ArrayList<>(employees);
//        ArrayList<ArrayList<Integer>> preferCodes = new ArrayList<>();   //种群偏好数组
//        ArrayList<Employee> preferCodes = new ArrayList<>();   //种群偏好数组
//        for (Employee employee : employees) {
//            System.out.println(employee.getPreferCode());
//            preferCodes.add(employee);
//        }
//
        System.out.println("客流数量" + storeRule.getFlowNeeds().size());

//        int employeesLimit= 20;

//        ga.setScale(employeesLimit);   //设置种群规模
//        int[] employeesLimitArr = new int[employees.size()];
        //对人数进行限制
//        while (true){
//            int index = Math.abs((int)(Math.random()*(employees.size()-1)));
//            boolean falg = false;
//
//            for(int i=1;i<employeesLimit;i++){
//                if (employeesLimitArr[index] != 0) {
//                    falg = true;
//                    break;
//                }
//            }
//            employeesLimitArr[index]++;
//            if (!falg){
//                preferCodes.add(employees.get(index));
//                if(preferCodes.size()==employeesLimit) break;
//            }
//
//        }
//System.out.println();
        //开始公式计算并传参进行GA,完成排班

        ArrayList<Employee> abc = ga.GA(preENeed, aftENeed, storeRule.getFlowNeeds(), (int) (storeRule.getPreTimeNeed() / 0.5), (int) (storeRule.getAftTimeNeed() / 0.5), day, preferCodes);
//        System.out.println("wuyuzi");
//        for (Employee e : abc) {
//            System.out.println(e);
//        }
//        return abc;
        System.out.println("abc" + abc);
        return abc == null;
//        return ga.GA(preENeed, aftENeed, storeRule.getFlowNeeds(), (int)(storeRule.getPreTimeNeed() / 0.5), (int)(storeRule.getAftTimeNeed() / 0.5), day, preferCodes);
//        return null;
    }
}
