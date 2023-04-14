package com.schedule.entity;

import com.schedule.algorithm.Calculate;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.relational.core.sql.In;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Store {
    private String storeId;
    private String storeName;
    private String storeType;
    private String address;
    private float size;
    private String manger;
    private int workers;
    private int state;
    private String label;

    private float preTimeNeed = 2;   // 开店前所需时间,最多2小时
    private String preEFormula;   //开店前所需员工公式
    private int preENeed = 1;   //开店前所需员工,最少为1
    private ArrayList<String> prePosLimit;   //开店前员工职位限制
    private float aftTimeNeed = 2;   // 关店后所需时间,最多2小时
    private String aftEFormula;   //关店后所需员工公式
    private int aftENeed = 1;   //关店后所需员工,最少为1
    private ArrayList<String> aftPosLimit;   //关店后员工职位限制
    private String flowFormula;   //客流规则公式
    private int minNeed = 1;   //客流最少所需员工,最少为1
    private ArrayList<String> posLimit;   //日常班次员工职位限制

    private List<Integer> flowNeeds;

    /**
     * 从excel表中获取数据
     *
     * @param day
     * @param storeId
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> getFlow(String day, String storeId) throws Exception {
        List<Map<String, Object>> mapList = new ArrayList<>();

        ClassPathResource classPathResource = new ClassPathResource("flow/" + day + "_" + storeId + ".xlsx");
        InputStream inputStream = classPathResource.getInputStream();

        XSSFWorkbook sheets = new XSSFWorkbook(inputStream);   // 读取整个Excel
        XSSFSheet sheetAt = sheets.getSheetAt(0);   // 获取第一个表单Sheet
        XSSFRow titleRow = sheetAt.getRow(0);   //默认第一行为标题行，i = 0

        for (int i = 1; i < sheetAt.getPhysicalNumberOfRows(); i++) {   // 循环获取每一行数据
            XSSFRow row = sheetAt.getRow(i);
            // 读取每一列内容
            Map<String, Object> map = new HashMap<>();
            for (int index = 0; index < 5; index++) {   //???????????????????????????????bug列数等于6
                XSSFCell titleCell = titleRow.getCell(index);
                XSSFCell cell = row.getCell(index);
                if (cell != null) {
                    String titleName = titleCell.getStringCellValue();   //表头数据
                    if (titleName.equals("日期")) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String value = sdf.format(cell.getDateCellValue());
                        map.put(titleName, value);
                    } else if (titleName.equals("开始时间") || titleName.equals("结束时间")) {
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        String value = sdf.format(cell.getDateCellValue());
                        map.put(titleName, value);
                    } else {
                        cell.setCellType(CellType.STRING);
                        map.put(titleName, cell.getStringCellValue());
                    }
                }
            }
            mapList.add(map);
        }

        return mapList;
    }

    /**
     * 整合、减少时间段，生成班次及班次所需人数
     *
     * @param date
     * @param day
     */
    @SneakyThrows
    public void setFlowNeeds(String date, int day) {
        String[] times = {"09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00",
                "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
                "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30"};

        List<Map<String, Object>> flows = getFlow(date, storeId);
        List<Integer> flowNeeds = new ArrayList<>();
        int index = 0, maxIndex = times.length, flowIndex = 0;
        if (day > 5) index = 2;   //周末从10点开始
        else maxIndex = times.length - 2;   //工作日最晚到21点

//        Boolean flag = false;
//        int minNeed = getMinNeed();   //客流最少所需员工
//        int index = 0, maxIndex = times.length, flowIndex = 0;
//        int num = -1, startIndex = 0, sum = 0, len = 0;
//        if (day > 5) index = 2;   //周末从10点开始
//        else maxIndex = times.length - 2;   //工作日最晚到21点


        for (int i = index; i < maxIndex; i++) {
            //开头时间段客流未设
            while (flowIndex < flows.size() && times[index].compareTo(flows.get(flowIndex).get("开始时间").toString()) > 0)
                flowIndex++;
            //时间段已设
            int need;
            if (flowIndex < flows.size() && times[index].equals(flows.get(flowIndex).get("开始时间"))) {
                //根据公式计算客流所需员工人数
                double predictFlow = Double.parseDouble(flows.get(flowIndex).get("预测顾客流量").toString());
                double num_double = (new Calculate()).dealExp(getFlowFormula(), predictFlow);

                int formulaNum = (int) Math.round(num_double);   //四舍五入
                need = formulaNum > minNeed ? formulaNum : minNeed;   //所需人数不能小于规定最小客流所需人数
                flowIndex++;
            } else {
                need = minNeed;
            }
            flowNeeds.add(need);
            index++;
        }
        System.out.println(flowNeeds);
        this.flowNeeds = flowNeeds;
    }
}
