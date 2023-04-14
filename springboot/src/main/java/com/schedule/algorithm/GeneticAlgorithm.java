package com.schedule.algorithm;

import com.schedule.entity.Employee;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class GeneticAlgorithm {
    public ArrayList<Chromosome> group;   //染色体集，即种群
    public ArrayList<ArrayList<Integer>> check;   //确认整个种群上的染色体点位
    public ArrayList<ArrayList<Integer>> status;   //状态，最开始都为待机wait，除了偏好员工外
    public ArrayList<ArrayList<Integer>> workingTime;   //0下标表示连续工作时间，1下标表示日工作时间 2下标表示周工作时间

    private double P_mutation = 0.5;   //变异概率
//    private double P_mutation = ;   //变异概率


    private int len = 34;   //染色体长度：开店前工作时间+日常工作时间【12h】+关店后工作时间
    private int scale = 20;   //可上班员工数目，即种群规模

    public GeneticAlgorithm(ArrayList<Chromosome> group) {
        this.group = group;
    }

    /**
     * 某个班次上班人数
     *
     * @param index
     * @return
     */
    private int countShiftE(int index) {
        int count = 0;
        for (int i = 0; i < scale; i++) {
            if (group.get(i).chromosome.get(index) == 1) count++;
        }

        return count;
    }

    /**
     * 获取持续上班时间
     *
     * @param index
     * @return
     */
    private int getContTime(int index) {
        return workingTime.get(index).get(0);
    }

    /**
     * 获取日工作时间
     *
     * @param index
     * @return
     */
    private int getDayTime(int index) {
        return workingTime.get(index).get(1);
    }

    /**
     * 下班后重置持续工作时间
     *
     * @param index
     */
    private void resetContTime(int index) {
        ArrayList<Integer> newContTime = workingTime.get(index);
        newContTime.set(0, 0);
        workingTime.set(index, newContTime);
    }

    private void init() {
        //初始化check
        check = new ArrayList<>();
        for (int i = 0; i < scale; i++) {
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int j = 0; j < len; j++) {
                tmp.add(0);
            }
            check.add(tmp);
        }

        //初始化状态status
        status = new ArrayList<>();
        //1代表waiting 准备上班
        //2代表working 上班中
        //3代表resting 休息中/吃饭中，这个时候不能派班
        for (int i = 0; i < scale; i++) {
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int j = 0; j < len; j++) {
                tmp.add(1);
            }
            status.add(tmp);
        }

        //初始化工作时间workingTime
        workingTime = new ArrayList<>();
        for (int i = 0; i < scale; i++) {
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                tmp.add(0);
            }
            workingTime.add(tmp);
        }
    }

    /**
     * //变异,对某一点位全部随机变异
     *
     * @param pos
     */
    private void mutate(int pos,ArrayList<Employee> loves_arr,int PreNum) {
        //取反即可
        Random r = new Random();
        double pm= (double) PreNum/loves_arr.size();
        for (int i = 0; i < scale; i++) {//
//            if (Math.abs(r.nextInt() % 10000 / 10000.0) < getP_mutation() && check.get(i).get(pos) == 0 && status.get(i).get(pos) == 1) {//未确认，在waiting才能变异上班,并且满足概率
            if (Math.abs(r.nextInt() % 10000 / 10000.0) < pm  && check.get(i).get(pos) == 0 && status.get(i).get(pos) == 1) {//未确认，在waiting才能变异上班,并且满足概率

                if (group.get(i).chromosome.get(pos) == 0) {
                    Chromosome chro = group.get(i);
                    chro.chromosome.set(pos, 1);
                    group.set(i, chro);
                } else {
                    Chromosome chro = group.get(i);
                    chro.chromosome.set(pos, 0);
                    group.set(i, chro);
                }
            }
        }
//        System.out.println(group);
    }

    private boolean fitness(int PreNum, int AftNum, List<Integer> PassFlowNum, int pre, int later, ArrayList<Employee> loves_arr) {
        //如果现在len是28，代表 pre 24 later的一个时间构造，解析上班时间的时候，就需要传上下班这个参数来确定
        boolean flag = true;
//        System.out.println(PassFlowNum);

        Random r = new Random();

        for (int i = 0; i < group.size(); i++) {
            Chromosome tmp = group.get(i);
            for (int j = 0; j < len; j++) {
                tmp.chromosome.set(j, 0);
            }
            group.set(i, tmp);
        }
        //每次排班重置group，不然都是一样的

        while (countShiftE(0) != PreNum) {//PreNum个人工作前打扫
            mutate(0,loves_arr,PreNum);//变异第一列
        }

        //需参数设置初始化
        for (int i = 1; i < pre; i++) {
            for (int j = 0; j < scale; j++) {
                Chromosome chromo = group.get(j);
                chromo.chromosome.set(i, chromo.chromosome.get(i - 1));
                group.set(j, chromo);
            }
            System.out.println("基因位" + i + "已确认");
        }

        //确认第一行后进行check，status，workingTime的修改
        for (int i = 0; i < pre; i++) {
            for (int j = 0; j < scale; j++) {//j需要参数
                if (group.get(j).chromosome.get(i) == 1) {//如果第一班次被选中
                    ArrayList<Integer> tmp = check.get(j);
                    tmp.set(i, 1);//check 1代表确认
                    check.set(i, tmp);
                    tmp = status.get(j);
                    tmp.set(i, 2);//status 2代表上班中
                    status.set(j, tmp);
                    tmp = workingTime.get(j);
                    tmp.set(0, getContTime(j) + 1);
                    tmp.set(1, getDayTime(j) + 1);
                    workingTime.set(j, tmp);
//                    loves_arr.get(j).setWeekTime(loves_arr.get(j).getWeekTime()+getDayTime(j));
                }
                //虽然其他的未被确定，但是也用不上了，只需要确认需要上班的即可
            }
        }


        //第一班次设完之后，后面就是人数多，那么如果时间没<8,就上  人数少，那么随机挑选持续时间<8的n个人上
        //大循环
        //int ran=0;
        for (int i = pre; i < len; i++) {
            //ran++;
            double PopulationsNeed = 0;
            if (i >= pre && i < len - later) PopulationsNeed = PassFlowNum.get(i - pre);
            else if (i >= len - later) PopulationsNeed = AftNum;

            /*if(i==2) {
                System.out.println(pre);
                System.out.println((int)Math.ceil(PopulationsNeed));
                System.exit(0);
            }*/

            //正常休息
            for (int j = 0; j < scale; j++) {//j是人，i是位点
                if (getContTime(j) >= 8) {
                    ArrayList<Integer> tmp = check.get(j);
                    tmp.set(i, 1);//确认
                    if (i + 1 < len)
                        tmp.set(i + 1, 1);
                    check.set(j, tmp);
                    tmp = status.get(j);
                    tmp.set(i, 3);//休息中
                    if (i + 1 < len)
                        tmp.set(i + 1, 3);//休息
                    status.set(j, tmp);
                    resetContTime(j);//重置这个人的连续工作时间
                }//安排休息没问题
                if ((getDayTime(j) >= 16) || (loves_arr.get(j).getPreferCode().get(0) == 3 && getDayTime(j) == loves_arr.get(j).getPreferCode().get(32))) {
                    for (int k = i; k < len; k++) {
                        resetContTime(j);
                        ArrayList<Integer> tmp = check.get(j);
                        tmp.set(k, 1);//确认
                        check.set(j, tmp);
                        tmp = status.get(j);
                        tmp.set(k, 3);//休息中
                        status.set(j, tmp);
                    }
                }//今天就工作完了
            }


//            //随机2-4小时的员工休息半小时
//            /*while (ran == 7 ) {
//                int index = Math.abs(r.nextInt() % Size);
//                for(int j=index;j<Size;j++) {
//                    if (status.get(index).get(i) == 1 && getContTime(index) >= 4 && getContTime(index) < 8) {
//                        ArrayList<Integer> tmp = check.get(index);
//                        tmp.set(i, 1);//确认
//                        check.set(index, tmp);
//                        tmp = status.get(index);
//                        tmp.set(i, 3);//休息中
//                        status.set(index, tmp);
//                        resetPerTime(index);//重置这个人的连续工作时间
//                        ran = 0;
//                    }
//                }
//            }*/
//
            if (countShiftE(i - 1) <= (int) Math.ceil(PopulationsNeed)) {//还需要人上班，可以上的都上去
                for (int j = 0; j < scale; j++) {//循环每一个
                    if (getContTime(j) > 0 && getContTime(j) < 8 && group.get(j).chromosome.get(i - 1) == 1 && status.get(j).get(i) == 1&&loves_arr.get(j).getWeekTime()<64) {//等待上班，并且可以上
                        Chromosome work = group.get(j);
                        work.chromosome.set(i, 1);
                        group.set(j, work);

                        ArrayList<Integer> tmp = check.get(j);
                        tmp.set(i, 1);//确认上班
                        check.set(j, tmp);

                        tmp = status.get(j);
                        tmp.set(i, 2);//上班中
                        status.set(j, tmp);

                        tmp = workingTime.get(j);
                        tmp.set(0, getContTime(j) + 1);
                        tmp.set(1, getDayTime(j) + 1);
                        workingTime.set(j, tmp);
//                        loves_arr.get(j).setWeekTime(loves_arr.get(j).getWeekTime()+getDayTime(j));
                    }
                    int count=0;
                    //还缺多少人，随机上
                    while (countShiftE(i) < (int) Math.ceil(PopulationsNeed)) {//人不够,随机选在wait的人上班,每次选一个
                        count++;
                        if(count>5000) break;
                        System.out.println("这是什么1？" + countShiftE(i) + " " + (int) Math.ceil(PopulationsNeed) + " " + 1);
                        int index = Math.abs((int) (Math.random() * (scale - 1) + 1));
                        if (check.get(index).get(i) == 0 && status.get(index).get(i) == 1 && getDayTime(index) < 16 && getContTime(index) < 8&&loves_arr.get(index).getWeekTime()<64) {//满足要求，上班
                            Chromosome work = group.get(index);
                            work.chromosome.set(i, 1);
                            group.set(index, work);

                            ArrayList<Integer> tmp = check.get(index);
                            tmp.set(i, 1);//确认上班
                            check.set(index, tmp);
                            tmp = status.get(index);
                            tmp.set(i, 2);//上班中
                            status.set(index, tmp);
                            tmp = workingTime.get(index);
                            tmp.set(0, getContTime(index) + 1);
                            tmp.set(1, getDayTime(index) + 1);
                            workingTime.set(index, tmp);
//                            loves_arr.get(index).setWeekTime(loves_arr.get(index).getWeekTime()+getDayTime(index));
                        }
                    }
                    //已凑上
                }
            } else if (countShiftE(i - 1) > (int) Math.ceil(PopulationsNeed)) {
                for (int j = 0; j < scale; j++) {
                    if (group.get(j).chromosome.get(i - 1) == 1 && getContTime(j) < 4 && getContTime(j) > 0&&loves_arr.get(j).getWeekTime()<64) {
                        Chromosome work = group.get(j);
                        work.chromosome.set(i, 1);
                        group.set(j, work);

                        ArrayList<Integer> tmp = check.get(j);
                        tmp.set(i, 1);//确认上班
                        check.set(j, tmp);

                        tmp = status.get(j);
                        tmp.set(i, 2);//上班中
                        status.set(j, tmp);

                        tmp = workingTime.get(j);
                        tmp.set(0, getContTime(j) + 1);
                        tmp.set(1, getDayTime(j) + 1);
                        workingTime.set(j, tmp);
//                        loves_arr.get(j).setWeekTime(loves_arr.get(j).getWeekTime()+getDayTime(j));
                    }
                }
                int count =0;
                while (countShiftE(i) < (int) Math.ceil(PopulationsNeed)) {
                    count++;
                    if(count>5000) break;
                    System.out.println("这是什么2？" + countShiftE(i) + " " + (int) Math.ceil(PopulationsNeed) + " " + 2);
                    int index = Math.abs((int) (Math.random() * (scale - 1) + 1));
//                        int index = Math.abs(r.nextInt()%Size);
                    if (check.get(index).get(i) == 0 && status.get(index).get(i) == 1 && getDayTime(index) < 16 && getContTime(index) < 8 &&loves_arr.get(index).getWeekTime()<64) {
                        //index上班，其他的下班(重置持续时间)
                        Chromosome work = group.get(index);
                        work.chromosome.set(i, 1);
                        group.set(index, work);

                        ArrayList<Integer> tmp = check.get(index);
                        tmp.set(i, 1);//确认上班
                        check.set(index, tmp);

                        tmp = status.get(index);
                        tmp.set(i, 2);//上班中
                        status.set(index, tmp);

                        tmp = workingTime.get(index);
                        tmp.set(0, getContTime(index) + 1);
                        tmp.set(1, getDayTime(index) + 1);
                        workingTime.set(index, tmp);
//                        loves_arr.get(index).setWeekTime(loves_arr.get(index).getWeekTime()+getDayTime(index));
                    }


                }
                for (int j = 0; j < scale; j++) {
                    if (check.get(j).get(i) == 0) {
                        resetContTime(j);//重置它的持续时间
                    }
                }
            }
            System.out.println("基因位" + i + "已确认");
        }
        for (int j = 0; j < scale; j++) {
            if (getContTime(j) < 4 && getContTime(j) != 0) {
//                flag = false;
                return false;
            }
        }

        for (int j = 0; j < scale; j++) {
            loves_arr.get(j).setWeekTime(loves_arr.get(j).getWeekTime()+getDayTime(j));
        }
        return flag;
    }

    //偏好数组和星期几，用于check
//    public ArrayList<Chromosome> GA(int PreNum, int AftNum, List<Integer> PassFlowNum, int pre, int later, int day, ArrayList<ArrayList<Integer>> loves_arr) {

    public ArrayList<Employee> GA(int PreNum, int AftNum, List<Integer> PassFlowNum, int pre, int later, int day, ArrayList<Employee> loves_arr) {
        ArrayList<Chromosome> group1 = group;
        ArrayList<Chromosome> group2 = group;

        //员工开始的随机时间安排
        for (int i = 0; i < scale; i++) {
            System.out.println("员工" + i);
            for (int j = 0; j < len; j++) {
                System.out.print(group.get(i).chromosome.get(j));
                System.out.print(" ");
            }
            System.out.println();
        }

        init();

//        loves_arr.forEach(element -> System.out.println(element));
//        System.out.println(status);

        //初始化完了之后使用loves_arr进行check
        for (int a = 0; a < loves_arr.size(); a++)//a是哪个人
        {
            //每一个爱好数组
            //0设置种类(1 2 3) 1-7 工作日   8-31 工作时间（工作日代表9-21 周末代表10-22）   32 日工作时间偏好
            if (loves_arr.get(a).getPreferCode().get(0) == 1) {//工作日
                for (int i = 1; i < 7; i++) {
                    if (loves_arr.get(a).getPreferCode().get(i) == 0 && ((day % 7) + 1) == i) {
                        ArrayList<Integer> tmp1 = check.get(a);
                        ArrayList<Integer> tmp2 = status.get(a);
                        for (int j = 0; j < tmp1.size(); j++) {
                            tmp1.set(j, 1);
                            tmp2.set(j, 3);
                        }
                        check.set(a, tmp1);
                        status.set(a, tmp2);
                    }
                }
            } else if (loves_arr.get(a).getPreferCode().get(0) == 2) {
                for (int i = 8; i <= 31; i++) {//9:00-21:00 或者 10:00-22:00
                    //因为解析decode的时候解析的时间不同，所以说都是8-31，代表周末和工作如
                    if (loves_arr.get(a).getPreferCode().get(i) == 0) {//说明偏好为不想上班//工作日
                        ArrayList<Integer> tmp1 = check.get(a);
                        ArrayList<Integer> tmp2 = status.get(a);
                        tmp1.set(i - 8 + pre, 1);
                        tmp2.set(i - 8 + pre, 3);
                        check.set(a, tmp1);
                        status.set(a, tmp2);
                    }
                }
            } else if (loves_arr.get(a).getPreferCode().get(0) == 3) {
                //这里什么都不做，第三种偏好需要传给fitness去适应
            }

        }

        System.out.println("请稍后，正在GA中......");
        //1.初始化种群
        //2.while次迭代以全局适应
        Random r = new Random();
        int count =0;
        while (true)//while循环中一直在进行种群更新
        {
            count++;
            if(count>5) return null;
            if (fitness(PreNum, AftNum, PassFlowNum, pre, later, loves_arr) == true) {
                System.out.println("Day" + day + "已完成排班，结果如下：");
//                ArrayList<Employee> new_loves = new  ArrayList<>(loves_arr);
                for (int i = 0; i < scale; i++) {
                    System.out.println("员工" + i);
//                    new_loves.get(i).getChromo();
//                    loves_arr.get(j).setWeekTime(loves_arr.get(j).getWeekTime()+getDayTime(j));
                    ArrayList<Chromosome> newChomes = new ArrayList<>();
                    newChomes.add(group.get(day));
//                    loves_arr.get(i).mySetChromo(day,group.get(i));
//                    System.out.println(loves_arr.get(i).getChromo());
//                    loves_arr.get(i).getChromo().set(day,newChomes.get(0));
//                    ArrayList<Chromosome> temp = loves_arr.get(i).getChromo().add(group.get(i));
//                    System.out.println(temp);
//                    loves_arr.get(i).setChromo(temp);
//                    System.out.println(loves_arr.get(i).getChromo());
//                    System.out.println(loves_arr.get(i).getWeekTime());
                    loves_arr.get(i).getChromo().add(group.get(i).chromosome);
                    loves_arr.get(i).setChromo(loves_arr.get(i).getChromo());
                    System.out.print(loves_arr.get(i).getChromo());
                    for (int j = 0; j < len; j++) {
                        System.out.print(group.get(i).chromosome.get(j).intValue());
//                        System.out.print(loves_arr.get(i).getChromo().get(day).chromosome.get(j).intValue());
//                        System.out.print(loves_arr.get(i).getChromo().get(day).intValue());
                        System.out.print(" ");
                    }
                    System.out.println();
                }
                System.out.println("结果显示完成!!!");
                group1 = group;
                group = group2;

//                return group1;
                return loves_arr;
            } else {
                return GA(PreNum, AftNum, PassFlowNum, pre, later, day, loves_arr);
            }

            //在fitness中进行变异
        }

//
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
        //为什么每天GA的结果都是一样
    }
}
