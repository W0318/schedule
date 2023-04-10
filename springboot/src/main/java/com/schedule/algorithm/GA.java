package com.schedule.algorithm;

import java.util.ArrayList;
import java.util.List;

public class GA {
    private List<Chromo> group;   //染色体集，即种群
    private int scale = 50;   //种群规模
    private int len;   //染色体长度
    private int n_iterate = 20000;   //迭代次数
    private double p_cross = 0.8;   //交叉概率
    private double p_mutate = 0.05;   //变异概率

    public GA(int len_period, int len_stuff) {
        this.len = len_period * len_stuff;
        for (int i = 0; i < scale; i++) {
            group.add(new Chromo(len_period * len_stuff));
        }
    }

    public GA(int scale, int len_period, int len_stuff) {
        this.scale = scale;
        this.len = len_period * len_stuff;
        for (int i = 0; i < scale; i++) {
            group.add(new Chromo(len_period * len_stuff));
        }
    }

    /**
     * 计算选择概率
     * 染色体i被选择的概率 = 染色体i的适应度 / 所有染色体的适应度之和
     */
    public void calSelectP() {
        int sum = 0;
        for (int i = 0; i < scale; i++) {
            sum += group.get(i).fitness;
        }
        for (Chromo chromo : group) {
            chromo.p_select = chromo.fitness / sum;
        }
    }

    /**
     * 计算期望概率
     */
    public void calExpectP() {
        for (Chromo chromo : group) {
            chromo.p_expect = chromo.p_select * scale;
        }
    }

    /**
     * 计算染色体序列是否较优，较优则被选择，进入下一代
     */
    public void calIsSelected() {
        int needSelect = scale;
        for (Chromo chromo : group) {
            if (chromo.p_expect < 1) {
                chromo.isSelected++;
                needSelect--;
            }
        }

        int j = 0;
        while (needSelect != 0) {
            for (Chromo chromo : group) {
                if ((int) (chromo.p_expect * 10) == j) {
                    chromo.isSelected++;
                    needSelect--;
                    if (needSelect == 0) break;
                }
            }
            j++;
        }
    }

    public void calFitness() {
        for (Chromo chromo : group) {

        }
    }

    /**
     * 交叉主体函数
     */
    public void crossover() {
        int pop = (int) (scale * p_cross / 2);
        while (pop > 0) {
            int x = (int) (Math.random() * scale);
            int y = (int) (Math.random() * scale);

            executeCross(x, y);
            pop--;
        }
    }

    public void executeCross(int x, int y) {
        int diffNum = 0;
        List<Integer> diffs = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            if (group.get(x).chromosome.get(i) != group.get(y).chromosome.get(i)) {
                diffs.add(group.get(x).chromosome.get(i));
                group.get(x).chromosome.set(i, -1);
                group.get(y).chromosome.set(i, -1);
                diffNum++;
            }
        }
    }
}
