package com.schedule.algorithm;
//import com.sun.source.tree.CompilationUnitTree;

import java.util.ArrayList;
import java.util.Random;

/**
 * 使用说明：
 * ①无法保证每周40小时的排班，只能通过适当人员的增加来完成这个适合度
 * ②group是一天内所有员工的时间染色体集，也就是种群，用于遗传
 * ③chromosome是染色体
 */
public class GA_group {
    //规则是用来固定len和限制适应度函数fitness的
    //固定规则：
    //①周一~周五 9-21  12小时
    //②周末    10-22  12小时
    //③每天最多8
    //④每班最少2，最多4 可以多个连续班次，也就是说可以连续两个2
    //⑤最长连续工作时间4，必须休息
    //⑥工作时间完全覆盖饭点的员工安排吃饭时间
    //⑦午餐：12：00~13：00  晚餐：18：00~19：00  休息至少半小时
    //用户定义：
    //①开店前a小时准备 门店面积/b = 需要人数
    //②每天至少安排不超过4小时的班次 客流/c = 需要人数
    //③没有客流的时候 至少d人员值班
    //④关门后需要e小时收尾 门店买诺记/f + g = 人数

    public ArrayList<Chromo> group;//种群，染色体集
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //动态数组记录状态
    public ArrayList<ArrayList<Integer>> check;
    //check需要是二维数组来确认整个种群上的染色体点位
    public ArrayList<ArrayList<Integer>> status;//状态，最开始都为待机wait，除了偏好员工外
    public ArrayList<ArrayList<Integer>> workingTime;//0下标表示连续工作时间，1下标表示日工作时间 2下标表示周工作时间

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private double pm = 0.5;//变异概率，//一般以上概率不能手动改变，只能程序员改代码
    private int len = 34;//染色体长度，需要传入上班前工作时间，下班后工作时间
    private int Size = 33;//可上班员工数目，种群规模

    public GA_group(ArrayList<Chromo> group) {
        this.group = group;


    }//

    public void setLen(int len) {
        this.len = len;
    }

    public void setSize(int size) {
        Size = size;
    }

    /**
     * 某个班次的上班人数
     */
    private int countPopulations(int index) {
        int res = 0;
        for (int i = 0; i < Size; i++) {
            if (group.get(i).chromosome.get(index) == 1) res++;
        }
        return res;//多少个人
    }//

    /**
     * 持续上班时间
     */
    private int persistentTime(int index) {//持续时间
        return workingTime.get(index).get(0);
    }//

    /**
     * 日工作时间
     */
    private int DayTime(int index) {
        return workingTime.get(index).get(1);
    }//

    private void resetPerTime(int index)//下班后重置
    {
        ArrayList<Integer> tmp = workingTime.get(index);
        tmp.set(0, 0);//持续时间重置0
        workingTime.set(index, tmp);
    }

    private int checkPopulation(int index)//还差多少人
    {
        int res = 0;
        for (int i = 0; i < Size; i++) {
            if (check.get(i).get(index) == 1) res++;
        }
        return res;//多少个人
    }

    private boolean fitness(int PreNum, int FreeNum, int AftNum, ArrayList<Double> PassFlowNum, int pre, int later, ArrayList<ArrayList<Integer>> loves_arr) {
        //如果现在len是30，代表 pre 24 later的一个时间构造，解析上班时间的时候，就需要传上下班这个参数来确定
        boolean flag = true;
        Random r = new Random();

        for (int i = 0; i < group.size(); i++) {
            Chromo tmp = group.get(i);
            for (int j = 0; j < len; j++) {
                tmp.chromosome.set(j, 0);
            }
            group.set(i, tmp);
        }
        //每次排班重置group，不然都是一样的

        while (countPopulations(0) != PreNum) {//PreNum个人工作前打扫
            mutate(0);//变异第一列
        }

        //需参数设置初始化
        for (int i = 1; i < pre; i++) {
            for (int j = 0; j < Size; j++) {
                Chromo tmp = group.get(j);
                tmp.chromosome.set(i, tmp.chromosome.get(i - 1));
                group.set(j, tmp);
            }
            System.out.println("基因位" + i + "已确认");
        }


        //确认第一行后进行check，status，workingTime的修改
        for (int i = 0; i < pre; i++) {
            for (int j = 0; j < Size; j++) {//j需要参数
                if (group.get(j).chromosome.get(i) == 1) {//如果第一班次被选中
                    ArrayList<Integer> tmp = check.get(j);
                    tmp.set(i, 1);//check 1代表确认
                    check.set(i, tmp);
                    tmp = status.get(j);
                    tmp.set(i, 2);//status 2代表上班中
                    status.set(j, tmp);
                    tmp = workingTime.get(j);
                    tmp.set(0, persistentTime(j) + 1);
                    tmp.set(1, DayTime(j) + 1);
                    workingTime.set(j, tmp);
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

            if ((int) PopulationsNeed == 0) PopulationsNeed = FreeNum;//没有人时需要1人

            /*if(i==2) {
                System.out.println(pre);
                System.out.println((int)Math.ceil(PopulationsNeed));
                System.exit(0);
            }*/

            //正常休息
            for (int j = 0; j < Size; j++) {//j是人，i是位点
                if (persistentTime(j) >= 8) {
                    ArrayList<Integer> tmp = check.get(j);
                    tmp.set(i, 1);//确认
                    if (i + 1 < Size)
                        tmp.set(i + 1, 1);
                    check.set(j, tmp);
                    tmp = status.get(j);
                    tmp.set(i, 3);//休息中
                    if (i + 1 < Size)
                        tmp.set(i + 1, 3);//休息
                    status.set(j, tmp);
                    resetPerTime(j);//重置这个人的连续工作时间
                }//安排休息没问题
                if ((DayTime(j) >= 16) || (loves_arr.get(j).get(0) == 3 && DayTime(j) == loves_arr.get(j).get(32))) {
                    for (int k = i; k < len; k++) {
                        resetPerTime(j);
                        ArrayList<Integer> tmp = check.get(j);
                        tmp.set(k, 1);//确认
                        check.set(j, tmp);
                        tmp = status.get(j);
                        tmp.set(k, 3);//休息中
                        status.set(j, tmp);
                    }
                }//今天就工作完了
            }


            //随机2-4小时的员工休息半小时
            /*while (ran == 7 ) {
                int index = Math.abs(r.nextInt() % Size);
                for(int j=index;j<Size;j++) {
                    if (status.get(index).get(i) == 1 && persistentTime(index) >= 4 && persistentTime(index) < 8) {
                        ArrayList<Integer> tmp = check.get(index);
                        tmp.set(i, 1);//确认
                        check.set(index, tmp);
                        tmp = status.get(index);
                        tmp.set(i, 3);//休息中
                        status.set(index, tmp);
                        resetPerTime(index);//重置这个人的连续工作时间
                        ran = 0;
                    }
                }
            }*/

            //首先是确定需要的人数
            if (countPopulations(i - 1) <= (int) Math.ceil(PopulationsNeed)) {//还需要人上班，可以上的都上去
                for (int j = 0; j < Size; j++) {//循环每一个
                    if (persistentTime(j) > 0 && persistentTime(j) < 8 && group.get(j).chromosome.get(i - 1) == 1 && status.get(j).get(i) == 1) {//等待上班，并且可以上
                        Chromo work = group.get(j);
                        work.chromosome.set(i, 1);
                        group.set(j, work);

                        ArrayList<Integer> tmp = check.get(j);
                        tmp.set(i, 1);//确认上班
                        check.set(j, tmp);

                        tmp = status.get(j);
                        tmp.set(i, 2);//上班中
                        status.set(j, tmp);

                        tmp = workingTime.get(j);
                        tmp.set(0, persistentTime(j) + 1);
                        tmp.set(1, DayTime(j) + 1);
                        workingTime.set(j, tmp);
                    }
//                    else if(persistentTime(j)>1&&persistentTime(j)<8&&group.get(j).chromosome.get(i-1)==1&&status.get(j).get(i)==1){//等待上班，并且可以上
//                        Chromo work = group.get(j);
//                        work.chromosome.set(i,1);
//                        group.set(j,work);
//
//                        ArrayList<Integer> tmp = check.get(j);
//                        tmp.set(i,1);//确认上班
//                        check.set(j,tmp);
//
//                        tmp = status.get(j);
//                        tmp.set(i,2);//上班中
//                        status.set(j,tmp);
//
//                        tmp = workingTime.get(j);
//                        tmp.set(0,persistentTime(j)+1);
//                        tmp.set(1,DayTime(j)+1);
//                        workingTime.set(j,tmp);
//                    }
//                    else if(persistentTime(j)>0&&persistentTime(j)<8&&group.get(j).chromosome.get(i-1)==1&&status.get(j).get(i)==1){//等待上班，并且可以上
//                        Chromo work = group.get(j);
//                        work.chromosome.set(i,1);
//                        group.set(j,work);
//
//                        ArrayList<Integer> tmp = check.get(j);
//                        tmp.set(i,1);//确认上班
//                        check.set(j,tmp);
//
//                        tmp = status.get(j);
//                        tmp.set(i,2);//上班中
//                        status.set(j,tmp);
//
//                        tmp = workingTime.get(j);
//                        tmp.set(0,persistentTime(j)+1);
//                        tmp.set(1,DayTime(j)+1);
//                        workingTime.set(j,tmp);
//                    }
                }


                //还缺多少人，随机上
                while (countPopulations(i) < (int) Math.ceil(PopulationsNeed)) {//人不够,随机选在wait的人上班,每次选一个
                    System.out.println("这是什么1？" + countPopulations(i) + " " + (int) Math.ceil(PopulationsNeed) + " " + 1);
                    int index = Math.abs((int) (Math.random() * (Size - 1) + 1));
//                    int index = Math.abs(r.nextInt()%Size);
                    if (check.get(index).get(i) == 0 && status.get(index).get(i) == 1 && DayTime(index) < 16 && persistentTime(index) < 8) {//满足要求，上班
                        Chromo work = group.get(index);
                        work.chromosome.set(i, 1);
                        group.set(index, work);

                        ArrayList<Integer> tmp = check.get(index);
                        tmp.set(i, 1);//确认上班
                        check.set(index, tmp);
                        tmp = status.get(index);
                        tmp.set(i, 2);//上班中
                        status.set(index, tmp);
                        tmp = workingTime.get(index);
                        tmp.set(0, persistentTime(index) + 1);
                        tmp.set(1, DayTime(index) + 1);
                        workingTime.set(index, tmp);
                    }
                }
                //已凑上
            }
            //决定执行工作时长<2时就算人太多也不下班
            else if (countPopulations(i - 1) > (int) Math.ceil(PopulationsNeed)) {
                for (int j = 0; j < Size; j++) {
                    if (group.get(j).chromosome.get(i - 1) == 1 && persistentTime(j) < 4 && persistentTime(j) > 0) {
                        Chromo work = group.get(j);
                        work.chromosome.set(i, 1);
                        group.set(j, work);

                        ArrayList<Integer> tmp = check.get(j);
                        tmp.set(i, 1);//确认上班
                        check.set(j, tmp);

                        tmp = status.get(j);
                        tmp.set(i, 2);//上班中
                        status.set(j, tmp);

                        tmp = workingTime.get(j);
                        tmp.set(0, persistentTime(j) + 1);
                        tmp.set(1, DayTime(j) + 1);
                        workingTime.set(j, tmp);
                    }
                }
                while (countPopulations(i) < (int) Math.ceil(PopulationsNeed)) {
                    System.out.println("这是什么2？" + countPopulations(i) + " " + (int) Math.ceil(PopulationsNeed) + " " + 2);
                    int index = Math.abs((int) (Math.random() * (Size - 1) + 1));
//                        int index = Math.abs(r.nextInt()%Size);
                    if (check.get(index).get(i) == 0 && status.get(index).get(i) == 1 && DayTime(index) < 16 && persistentTime(index) < 8) {
                        //index上班，其他的下班(重置持续时间)
                        Chromo work = group.get(index);
                        work.chromosome.set(i, 1);
                        group.set(index, work);

                        ArrayList<Integer> tmp = check.get(index);
                        tmp.set(i, 1);//确认上班
                        check.set(index, tmp);

                        tmp = status.get(index);
                        tmp.set(i, 2);//上班中
                        status.set(index, tmp);

                        tmp = workingTime.get(index);
                        tmp.set(0, persistentTime(index) + 1);
                        tmp.set(1, DayTime(index) + 1);
                        workingTime.set(index, tmp);
                    }


                }
                for (int j = 0; j < Size; j++) {
                    if (check.get(j).get(i) == 0) {
                        resetPerTime(j);//重置它的持续时间
                    }
                }

            }

            System.out.println("基因位" + i + "已确认");
        }

        for (int j = 0; j < Size; j++) {
            if (persistentTime(j) < 4 && persistentTime(j) != 0) {
                flag = false;
            }
        }
        return flag;
//        return true;
    }

    //变异，变异概率pm
    //对某一点位全部随机变异
    private void mutate(int pos) {
        //取反即可
        Random r = new Random();
        for (int i = 0; i < Size; i++) {//
            //System.out.println(i+" "+Size+" "+check.size()+" "+status.size());
            if (Math.abs(r.nextInt() % 10000 / 10000.0) < pm && check.get(i).get(pos) == 0 && status.get(i).get(pos) == 1) {//未确认，在waiting才能变异上班,并且满足概率
                if (group.get(i).chromosome.get(pos) == 0) {
                    Chromo chro = group.get(i);
                    chro.chromosome.set(pos, 1);
                    group.set(i, chro);
                } else {
                    Chromo chro = group.get(i);
                    chro.chromosome.set(pos, 0);
                    group.set(i, chro);
                }
            }
        }
    }


    private void init() {
        //初始化check
        check = new ArrayList<>();
        for (int i = 0; i < Size; i++) {
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
        for (int i = 0; i < Size; i++) {
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int j = 0; j < len; j++) {
                tmp.add(1);
            }
            status.add(tmp);
        }

        //初始化工作时间workingTime
        workingTime = new ArrayList<>();
        //0下标表示连续工作时间，1下标表示日工作时间 2下标表示周工作时间
        for (int i = 0; i < Size; i++) {
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                tmp.add(0);
            }
            workingTime.add(tmp);
        }
    }

    //GA算法主体
    public ArrayList<Chromo> GA(int PreNum, int FreeNum, int AftNum, ArrayList<Double> PassFlowNum, int pre, int later, int day, ArrayList<ArrayList<Integer>> loves_arr)//偏好数组和星期几，用于check
    {
        ArrayList<Chromo> groupTmp1 = group;
        ArrayList<Chromo> groupTmp2 = group;
        //员工开始的随机时间安排
        for (int i = 0; i < Size; i++) {
            System.out.println("员工" + i);
            for (int j = 0; j < len; j++) {
                System.out.print(group.get(i).chromosome.get(j));
                System.out.print(" ");
            }
            System.out.println();
        }

        init();
        /**
         * 初始化完了之后使用loves_arr进行check
         */
        for (int a = 0; a < loves_arr.size(); a++)//a是哪个人
        {
            //每一个爱好数组
            //0设置种类(1 2 3) 1-7 工作日   8-31 工作时间（工作日代表9-21 周末代表10-22）   32 日工作时间偏好
            if (loves_arr.get(a).get(0) == 1) {//工作日
                for (int i = 1; i < 7; i++) {
                    if (loves_arr.get(a).get(i) == 0 && ((day % 7) + 1) == i) {
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
            } else if (loves_arr.get(a).get(0) == 2) {
                for (int i = 8; i <= 31; i++) {//9:00-21:00 或者 10:00-22:00
                    //因为解析decode的时候解析的时间不同，所以说都是8-31，代表周末和工作如
                    if (loves_arr.get(a).get(i) == 0) {//说明偏好为不想上班//工作日
                        ArrayList<Integer> tmp1 = check.get(a);
                        ArrayList<Integer> tmp2 = status.get(a);
                        tmp1.set(i - 8 + pre, 1);
                        tmp2.set(i - 8 + pre, 3);
                        check.set(a, tmp1);
                        status.set(a, tmp2);
                    }
                }
            } else if (loves_arr.get(a).get(0) == 3) {
                //这里什么都不做，第三种偏好需要传给fitness去适应
            }

        }

        System.out.println("请稍后，正在GA中......");
        //1.初始化种群
        //2.while次迭代以全局适应
//        Random r = new Random();

        while (true)//while循环中一直在进行种群更新
        {
            if (fitness(PreNum, FreeNum, AftNum, PassFlowNum, pre, later, loves_arr) == true) {
                System.out.println("Day" + day + "已完成排班，结果如下：");
                for (int i = 0; i < Size; i++) {
                    System.out.println("员工" + i);
                    for (int j = 0; j < len; j++) {
                        System.out.print(group.get(i).chromosome.get(j).intValue());
                        System.out.print(" ");
                    }
                    System.out.println();
                }
                System.out.println("结果显示完成!!!");
                groupTmp1 = group;
                group = groupTmp2;
                return groupTmp1;
            } else {
                return GA(PreNum, FreeNum, AftNum, PassFlowNum, pre, later, day, loves_arr);
            }

            //在fitness中进行变异
        }

//        //完成GA，显示结果
//        System.out.println("Day"+day+"已完成排班，结果如下：");
//        for(int i=0;i<Size;i++)
//        {
//            System.out.println("员工"+i);
//            for(int j=0;j<len;j++)
//            {
//                System.out.print(group.get(i).chromosome.get(j).intValue());
//                System.out.print(" ");
//            }
//            System.out.println();
//        }
//        System.out.println("结果显示完成!!!");
//        groupTmp1=group;
//        group=groupTmp2;
//        return groupTmp1;
//        //为什么每天GA的结果都是一样
    }
}
