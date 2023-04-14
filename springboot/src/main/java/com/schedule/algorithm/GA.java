//package com.schedule.algorithm;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GA {
//    public ArrayList<Chromosome> group;   //染色体集，即种群
//    public ArrayList<ArrayList<Integer>> check;   //确认整个种群上的染色体点位
//    public ArrayList<ArrayList<Integer>> status;   //状态，最开始都为待机wait，除了偏好员工外
//    public ArrayList<ArrayList<Integer>> workingTime;   //0下标表示连续工作时间，1下标表示日工作时间 2下标表示周工作时间
//
//    private double P_mutation = 0.5;   //变异概率
//    private int len = 32;   //染色体长度：开店前工作时间+日常工作时间【12h】+关店后工作时间
//    private int scale = 20;   //可上班员工数目，即种群规模
//
//    public GeneticAlgorithm(ArrayList<Chromosome> group) {
//        this.group = group;
//    }
//
//    /**
//     * 某个班次上班人数
//     *
//     * @param index
//     * @return
//     */
//    private int countShiftE(int index) {
//        int count = 0;
//        for (int i = 0; i < scale; i++) {
//            if (group.get(i).chromosome.get(index) == 1) count++;
//        }
//
//        return count;
//    }
//
//    /**
//     * 某个班次还差多少人
//     *
//     * @param index
//     * @return
//     */
//    private int checkLackE(int index) {
//        int lack = 0;
//        for (int i = 0; i < scale; i++) {
//            if (check.get(i).get(index) == 1) lack++;
//        }
//        return lack;
//    }
//
//    /**
//     * 获取持续上班时间
//     *
//     * @param index
//     * @return
//     */
//    private int getContTime(int index) {
//        int t = workingTime.get(index).get(0);
//        return t;
//    }
//
//    /**
//     * 获取日工作时间
//     *
//     * @param index
//     * @return
//     */
//    private int getDayTime(int index) {
//        return workingTime.get(index).get(1);
//    }
//
//    /**
//     * 下班后重置持续工作时间
//     *
//     * @param index
//     */
//    private void resetContTime(int index) {
//        ArrayList<Integer> newContTime = workingTime.get(index);
//        newContTime.set(0, 0);
//        workingTime.set(index, newContTime);
//    }
//
//    private void init() {
//        //初始化check
//        check = new ArrayList<>();
//        for (int i = 0; i < scale; i++) {
//            ArrayList<Integer> tmp = new ArrayList<>();
//            for (int j = 0; j < len; j++) {
//                tmp.add(0);
//            }
//            check.add(tmp);
//        }
//
//        //初始化状态status
//        status = new ArrayList<>();
//        //1代表waiting 准备上班
//        //2代表working 上班中
//        //3代表resting 休息中/吃饭中，这个时候不能派班
//        //4代表不偏好
//        for (int i = 0; i < scale; i++) {
//            ArrayList<Integer> tmp = new ArrayList<>();
//            for (int j = 0; j < len; j++) {
//                tmp.add(1);
//            }
//            status.add(tmp);
//        }
//
//        //初始化工作时间workingTime
//        workingTime = new ArrayList<>();
//        for (int i = 0; i < scale; i++) {
//            ArrayList<Integer> tmp = new ArrayList<>();
//            for (int j = 0; j < 2; j++) {
//                tmp.add(0);
//            }
//            workingTime.add(tmp);
//        }
//    }
//
//    /**
//     * //变异,对某一点位全部随机变异
//     *
//     * @param pos
//     */
//    private void mutate(int pos) {
//        //取反即可
//        Random r = new Random();
//        for (int i = 0; i < scale; i++) {//
////            System.out.println(check.get(i).get(pos));
////            System.out.println(status.get(i).get(pos));
//            if (Math.abs(r.nextInt() % 10000 / 10000.0) < getP_mutation() && status.get(i).get(pos) == 1) {//未确认，在waiting才能变异上班,并且满足概率
////                System.out.println(i + ":yes");
//                if (group.get(i).chromosome.get(pos) == 0) {
//                    Chromosome chro = group.get(i);
//                    chro.chromosome.set(pos, 1);
//                    group.set(i, chro);
//                } else {
//                    Chromosome chro = group.get(i);
//                    chro.chromosome.set(pos, 0);
//                    group.set(i, chro);
//                }
//            }
//        }
//
////        System.out.println(group);
//    }
//
//    private boolean fitness(int day, int PreNum, int AftNum, List<Integer> PassFlowNum, int pre, int later, ArrayList<ArrayList<Integer>> loves_arr) {
//        //如果现在len是28，代表 pre 24 later的一个时间构造，解析上班时间的时候，就需要传上下班这个参数来确定
//        System.out.println(PassFlowNum);
//
//        Random r = new Random();
//
//        for (int i = 0; i < group.size(); i++) {
//            Chromosome tmp = group.get(i);
//            for (int j = 0; j < len; j++) {
//                tmp.chromosome.set(j, 0);
//            }
//            group.set(i, tmp);
//        }
//        //每次排班重置group，不然都是一样的
//
//        while (countShiftE(0) != PreNum) {//PreNum个人工作前打扫
//            mutate(0);//变异第一列
//        }
//
//        //需参数设置初始化
//        for (int i = 1; i < pre; i++) {
//            for (int j = 0; j < scale; j++) {
//                Chromosome chromo = group.get(j);
//                chromo.chromosome.set(i, chromo.chromosome.get(i - 1));
//                group.set(j, chromo);
//            }
//            System.out.println("基因位" + i + "已确认");
//        }
//
//        //确认第一行后进行check，status，workingTime的修改
//        for (int i = 0; i < pre; i++) {
//            for (int j = 0; j < scale; j++) {//j需要参数
//                if (group.get(j).chromosome.get(i) == 1) {//如果第一班次被选中
////                    ArrayList<Integer> tmp = check.get(j);
////                    tmp.set(i, 1);//check 1代表确认
////                    check.set(i, tmp);
//                    ArrayList<Integer> e_status = status.get(j);
//                    e_status.set(i, 2);//status 2代表上班中
//                    status.set(j, e_status);
//
//                    ArrayList<Integer> e_workingTime = workingTime.get(j);
//                    e_workingTime.set(0, getContTime(j) + 1);
//                    e_workingTime.set(1, getDayTime(j) + 1);
//                    workingTime.set(j, e_workingTime);
//                }
//                //虽然其他的未被确定，但是也用不上了，只需要确认需要上班的即可
//            }
//        }
//
//        //第一班次设完之后，后面就是人数多，那么如果时间没<8,就上  人数少，那么随机挑选持续时间<8的n个人上
//        //大循环
//        //int ran=0;
//        for (int i = pre; i < len; i++) {
//            //ran++;
//            int PopulationsNeed;
//            if (i < len - later) PopulationsNeed = PassFlowNum.get(i - pre);
//            else PopulationsNeed = AftNum;
//
//            /*if(i==2) {
//                System.out.println(pre);
//                System.out.println((int)Math.ceil(PopulationsNeed));
//                System.exit(0);
//            }*/
//
//            //正常休息
//            for (int j = 0; j < scale; j++) {//j是人，i是位点
//                //连续工作时间 >= 4小时,休息
//                if (getContTime(j) >= 8) {
////                    ArrayList<Integer> tmp = check.get(j);
////                    tmp.set(i, 1);//确认
////                    if (i + 1 < scale)
////                        tmp.set(i + 1, 1);
////                    check.set(j, tmp);
//
//                    ArrayList<Integer> e_status = status.get(j);
//                    e_status.set(i, 3);   //休息
////                    if (i + 1 < scale)//???????????????????
////                        tmp.set(i + 1, 3);//休息
//                    status.set(j, e_status);
//                    resetContTime(j);//重置这个人的连续工作时间
//                }
//                //(日工作时间 >= 8) 或 (日工作时间 >= 班次日时长偏好),今日下班
//                if ((getDayTime(j) >= 16) ||
//                        ((loves_arr.get(j).get(0) == 4 || loves_arr.get(j).get(0) == 5 || loves_arr.get(j).get(0) == 6 || loves_arr.get(j).get(0) == 7)
//                                && getDayTime(j) >= loves_arr.get(j).get(32))
//                ) {
//                    for (int k = i; k < len; k++) {   //从当前时间点开始今天都不工作
//                        resetContTime(j);
////                        ArrayList<Integer> tmp = check.get(j);
////                        tmp.set(k, 1);//确认
////                        check.set(j, tmp);
//                        ArrayList<Integer> e_status = status.get(j);
//                        e_status.set(k, 3);   //休息
//                        status.set(j, e_status);
//                    }
//                }
//            }
//
//
////            //随机2-4小时的员工休息半小时
////            /*while (ran == 7 ) {
////                int index = Math.abs(r.nextInt() % Size);
////                for(int j=index;j<Size;j++) {
////                    if (status.get(index).get(i) == 1 && persistentTime(index) >= 4 && persistentTime(index) < 8) {
////                        ArrayList<Integer> tmp = check.get(index);
////                        tmp.set(i, 1);//确认
////                        check.set(index, tmp);
////                        tmp = status.get(index);
////                        tmp.set(i, 3);//休息中
////                        status.set(index, tmp);
////                        resetPerTime(index);//重置这个人的连续工作时间
////                        ran = 0;
////                    }
////                }
////            }*/
////
////
////            //首先是确定需要的人数
//            if (countShiftE(i) < PopulationsNeed) {//还需要人上班，可以上的都上去
//                for (int j = 0; j < scale; j++) {//循环每一个
//                    if (countShiftE(i) == PopulationsNeed) break;
//                    System.out.print(getContTime(j) + ", ");
//                    System.out.print(group.get(j).chromosome.get(i) + ", ");
//                    System.out.println(status.get(j).get(i));
//                    if (status.get(j).get(i) == 1) {//等待上班，并且可以上
//                        int preferLen;
//                        if (day <= 5) {
//                            System.out.println(loves_arr.get(j) + "【" + (i - pre + 8) + "】");
//                            System.out.println("num:" + loves_arr.get(j).get(i - pre + 8));
//                            preferLen = loves_arr.get(j).get(i - pre + 8);
//                        } else {
//                            System.out.println(loves_arr.get(j) + "【" + (i - pre + 10) + "】");
//                            System.out.println("num:" + loves_arr.get(j).get(i - pre + 10));
//                            preferLen = loves_arr.get(j).get(i - pre + 10);
//                        }
//
//                        ArrayList<Integer> e_workingTime = workingTime.get(j);
//                        if (preferLen >= 4) {
//                            if (group.get(j).chromosome.get(i - 1) == 1 && getContTime(j) + 4 >= 8
//                                    || i > scale - 1) ;
//                            else {
//                                Chromosome work = group.get(j);
//                                ArrayList<Integer> e_status = status.get(j);
//
//                                for (int k = i; k < i + 4; k++) {
//                                    work.chromosome.set(k, 1);
//                                    e_status.set(k, 2);//上班中
//                                }
//                                group.set(j, work);
//                                status.set(j, e_status);
//                                e_workingTime.set(1, getDayTime(j) + 4);
//                                if (group.get(j).chromosome.get(i - 1) == 0) //上一个时间段不在工作
//                                    e_workingTime.set(0, 4);
//                                else
//                                    e_workingTime.set(0, getContTime(j) + 4);
//                                workingTime.set(j, e_workingTime);
//                            }
//                        }
//                    }
//                }
//
//                //还缺多少人，随机上
//                int max = 0;
//                while (countShiftE(i) < PopulationsNeed) {//人不够,随机选在wait的人上班,每次选一个
//                    if (max++ > 10000) {
//                        System.out.println("员工不足");
//                        break;
//                    }
//                    int index = (int) (Math.random() * scale);
////                    System.out.print(index + ", ");
////                    System.out.print(check.get(index).get(i) + ", ");
////                    System.out.print(status.get(index).get(i) + ", ");
////                    System.out.println(getDayTime(index) + ", ");
//                    if (status.get(index).get(i) == 1 || status.get(index).get(i) == 4) {
//                        ArrayList<Integer> e_workingTime = workingTime.get(index);
//                        if (group.get(index).chromosome.get(i - 1) == 1 && getContTime(index) + 4 < 8
//                                || i > scale - 1) ;
//                        else {
//                            Chromosome work = group.get(index);
//                            ArrayList<Integer> e_status = status.get(index);
//
//                            for (int k = i; k < i + 4; k++) {
//                                work.chromosome.set(k, 1);
//                                e_status.set(k, 2);//上班中
//                            }
//                            group.set(index, work);
//                            status.set(index, e_status);
//                            e_workingTime.set(1, getDayTime(index) + 4);
//                            if (group.get(index).chromosome.get(i - 1) == 0) //上一个时间段不在工作
//                                e_workingTime.set(0, 4);
//                            else
//                                e_workingTime.set(0, getContTime(index) + 4);
//                            workingTime.set(index, e_workingTime);
//                        }
//
//                    }
//                }
//                //已凑上
//            }
////            else { //决定执行工作时长<2时就算人太多也不下班
////                for (int j = 0; j < scale; j++) {
////                    if (group.get(j).chromosome.get(i - 1) == 1 && getContTime(j) < 4 && getContTime(j) > 0) {
////                        Chromosome work = group.get(j);
////                        work.chromosome.set(i, 1);
////                        group.set(j, work);
////
////                        ArrayList<Integer> tmp = check.get(j);
////                        tmp.set(i, 1);//确认上班
////                        check.set(j, tmp);
////
////                        tmp = status.get(j);
////                        tmp.set(i, 2);//上班中
////                        status.set(j, tmp);
////
////                        tmp = workingTime.get(j);
////                        tmp.set(0, getContTime(j) + 1);
////                        tmp.set(1, getDayTime(j) + 1);
////                        workingTime.set(j, tmp);
////                    }
////                }
////                while (countShiftE(i) < PopulationsNeed) {
////                    System.out.println(countShiftE(i) + " " + PopulationsNeed + " " + 2);
////                    int index = Math.abs(r.nextInt() % scale);
////                    if (check.get(index).get(i) == 0 && status.get(index).get(i) == 1 && getDayTime(index) < 16 && getContTime(index) < 8) {
////                        //index上班，其他的下班(重置持续时间)
////                        Chromosome work = group.get(index);
////                        work.chromosome.set(i, 1);
////                        group.set(index, work);
////
////                        ArrayList<Integer> tmp = check.get(index);
////                        tmp.set(i, 1);//确认上班
////                        check.set(index, tmp);
////
////                        tmp = status.get(index);
////                        tmp.set(i, 2);//上班中
////                        status.set(index, tmp);
////
////                        tmp = workingTime.get(index);
////                        tmp.set(0, getContTime(index) + 1);
////                        tmp.set(1, getDayTime(index) + 1);
////                        workingTime.set(index, tmp);
////                    }
////                }
////                for (int j = 0; j < scale; j++) {
////                    if (check.get(j).get(i) == 0) {
////                        resetContTime(j);//重置它的持续时间
////                    }
////                }
////
////            }
//            System.out.println("基因位" + i + "已确认");
//        }
//        return true;
//    }
//
//    //偏好数组和星期几，用于check
//    public ArrayList<Chromosome> GA(int PreNum, int AftNum, List<Integer> PassFlowNum, int pre, int later, int day, ArrayList<ArrayList<Integer>> loves_arr) {
//        ArrayList<Chromosome> group1 = group;
//        ArrayList<Chromosome> group2 = group;
//
//        init();
////        System.out.println(check);
////        System.out.println(status);
//
//        //初始化完了之后使用loves_arr进行check
//        for (int a = 0; a < loves_arr.size(); a++) {//a是哪个人
//            System.out.print(a + "【");
//            System.out.print(loves_arr.get(a).get(0) + "】偏好:");
//            System.out.println(loves_arr.get(a));
//            System.out.println("status1:" + status.get(a));
//
//            //工作日偏好
//            if (loves_arr.get(a).get(0) == 1 || loves_arr.get(a).get(0) == 3
//                    || loves_arr.get(a).get(0) == 5 || loves_arr.get(a).get(0) == 7) {
//                for (int i = 1; i <= 7; i++) {
//                    if (loves_arr.get(a).get(i) == 0 && (day == i)) {
////                        ArrayList<Integer> tmp1 = check.get(a);
//                        ArrayList<Integer> e_status = status.get(a);
//                        for (int j = 0; j < e_status.size(); j++) {
////                            tmp1.set(j, 1);
//                            e_status.set(j, 4);
//                        }
////                        check.set(a, tmp1);
//                        status.set(a, e_status);
//                        break;
//                    } else if (loves_arr.get(a).get(i) == 1 && (day == i)) {
//                        //工作时间偏好
//                        if (loves_arr.get(a).get(0) == 2 || loves_arr.get(a).get(0) == 3
//                                || loves_arr.get(a).get(0) == 6 || loves_arr.get(a).get(0) == 7) {
//                            ArrayList<Integer> e_status = status.get(a);
//                            for (int j = 8; j <= 31; j++) {
//                                if (i > 5 && j - 10 < 0) continue;
//                                if (loves_arr.get(a).get(j) == 0) {//说明偏好为不想上班
////                                    ArrayList<Integer> tmp1 = check.get(a);
////                                    tmp1.set(pre + j - 12, 1);
//                                    if (i <= 5)
//                                        e_status.set(pre + (j - 8), 4);
//                                    else
//                                        e_status.set(pre + (j - 10), 4);
////                                    check.set(a, tmp1);
//                                }
//                            }
//                            status.set(a, e_status);
//                        }
//                        break;
//                    }
//                }
//            }
//
//            //工作时间偏好
//            else if (loves_arr.get(a).get(0) == 2 || loves_arr.get(a).get(0) == 3
//                    || loves_arr.get(a).get(0) == 6 || loves_arr.get(a).get(0) == 7) {
//                ArrayList<Integer> e_status = status.get(a);
//                for (int j = 8; j <= 31; j++) {
//                    if (day > 5 && j - 10 < 0) continue;
//                    if (loves_arr.get(a).get(j) == 0) {//说明偏好为不想上班
////                                    ArrayList<Integer> tmp1 = check.get(a);
////                                    tmp1.set(pre + j - 12, 1);
//                        if (day <= 5)
//                            e_status.set(pre + (j - 8), 4);
//                        else
//                            e_status.set(pre + (j - 10), 4);
////                                    check.set(a, tmp1);
//                    }
//                }
//                status.set(a, e_status);
//            }
//
//            System.out.println("status2:" + status.get(a));
//        }
//
////        System.out.println(check);
////        System.out.println(status);
//
//        System.out.println("请稍后，正在GA中......");
//        //1.初始化种群
//        //2.while次迭代以全局适应
//
//        while (fitness(day, PreNum, AftNum, PassFlowNum, pre, later, loves_arr) != true)//while循环中一直在进行种群更新
//        {
//            //在fitness中进行变异
//        }
////
//        //完成GA，显示结果
//        System.out.println("Day" + day + "已完成排班，结果如下：");
//        for (int i = 0; i < scale; i++) {
//            System.out.println("员工" + i);
//            for (int j = 0; j < len; j++) {
//                System.out.print(group.get(i).chromosome.get(j).intValue());
//                System.out.print(" ");
//            }
//            System.out.println();
//        }
//        System.out.println("结果显示完成!!!");
//        group1 = group;
//        group = group2;
//        return group1;
//        //为什么每天GA的结果都是一样
//    }
//}
