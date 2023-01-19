<template>
  <div class="container">
    <el-row class="top">
      <el-card class="component1">
        <el-clo>
          <div class="select-border">
            <text class="text-name">门 店 名 称 :</text>
            <el-select v-model="value" class="select" placeholder="浙江分公司" size="large">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </div>
          <div class="classify">
            <text class="text-title">排班统计</text>
            <el-card
              class="card"
              shadow="never"
              v-for="item in countData"
              :key="item.name"
              :body-style="{display: 'flex',padding:0 }"
            >
              <div class="detail">
                <div class="my-icon" :style="{background : item.color}"></div>
                <p class="desc">{{item.name}}</p>
                <p class="num">{{item.value}}</p>
              </div>
            </el-card>
          </div>
        </el-clo>
      </el-card>
      <el-card class="component2" :body-style="{padding:0 }">
        <!-- 柱状图 -->
        <text class="text-title">客流量预测</text>
        <div ref="echarts2" style="height:480px"></div>
      </el-card>
    </el-row>
    <el-row class="button">
      <el-card class="component2" :body-style="{padding:0 }">
        <text class="text-title">员工安排统计</text>
        <!-- 折线图 -->
        <div ref="echarts1" style="height:480px"></div>
      </el-card>
      <el-card class="component1" :body-style="{padding:0 }">
        <text class="text-title">偏好排名前列</text>
        <!-- 折线图 -->
        <div ref="echarts3" style="height:480px"></div>
      </el-card>
    </el-row>
    <!-- <div class="button"></div> -->
  </div>
</template>
  
<script>
import { getData } from "@/api";
import * as echarts from "echarts";
export default {
  data() {
    return {
      tableData: [],
      options: [
        {
          value: "浙江分公司",
          label: "浙江分公司"
        },
        {
          value: "上海分公司",
          label: "上海分公司"
        },
        {
          value: "江苏分公司",
          label: "江苏分公司"
        }
      ],
      countData: [
        {
          name: "本月总班数",
          value: 870,
          color: "#409EFF"
        },
        {
          name: "已值班数",
          value: 17,
          color: "#f28d3c"
        },
        {
          name: "剩余值班数",
          value: 4,
          color: "#f23c3c"
        },
        {
          name: "缺勤班数",
          value: 1,
          color: "#409EFF"
        }
      ]
    };
  },
  mounted() {
    getData().then(({ data }) => {
      console.log(data);
      // const{tableData} = data.data
      // this.tableData = tableData
      //准备基础dom ，初始化echarts实例
      const echarts1 = echarts.init(this.$refs.echarts1);
      //指定图标配置项和数据
      var echarts1Option = {};
      const { orderData } = data.data;
      echarts1Option.grid = {
        left: "3%",
        right: "4%",
        bottom: "3%",
        containLabel: true
      };
      // const xAxis = ["周一", "周二", "周三", "周四", "周五", "周六", "周日"];
      const legendData = ["数据1", "数据2"];
      const xAxisData = {
        type: "category",
        boundaryGap: false,
        data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"]
      };
      echarts1Option.legend = {
        data: legendData
      };
      echarts1Option.tooltip = {
        trigger: "axis",
        axisPointer: {
          type: "cross",
          label: {
            backgroundColor: "#6a7985"
          }
        }
      };
      echarts1Option.toolbox = {
        feature: {
          saveAsImage: {}
        }
      };
      echarts1Option.xAxis = xAxisData;
      echarts1Option.yAxis = {
        type: "value"
      };
      echarts1Option.series = [];
      legendData.forEach(key => {
        echarts1Option.series.push({
          name: key,
          type: "line",
          areaStyle: {},
          emphasis: {
            focus: "series"
          },
          data: orderData.data.map(item => item[key])
        });
      });
      // console.log(echarts1Option);
      // 使用刚指定的配置项和数据显示图表。
      echarts1.setOption(echarts1Option);

      //柱状图
      const echarts2 = echarts.init(this.$refs.echarts2);
      const echarts2Option = {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
            label: {
              backgroundColor: "#283b56"
            }
          }
        },
        legend: {},
        toolbox: {
          show: true,
          feature: {
            dataView: { readOnly: false },
            restore: {},
            saveAsImage: {}
          }
        },
        dataZoom: {
          show: false,
          start: 0,
          end: 100
        },
        xAxis: [
          {
            type: "category",
            boundaryGap: true,
            data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"]
            // ['8:00-10:00','10:00-12:00','12:00-14:00','14:00-16:00','16:00-18:00','18:00-20:00']
          }
        ],
        yAxis: [
          {
            type: "value",
            scale: true,
            name: "Price",
            max: 250,
            min: 0,
            boundaryGap: [0.2, 0.2]
          },
          {
            type: "value",
            scale: true,
            name: "Order",
            max: 200,
            min: 0,
            boundaryGap: [0.2, 0.2]
          }
        ],
        series: [
          {
            name: "数据a",
            type: "bar",
            // xAxisIndex: 1,
            yAxisIndex: 1,
            data: [100, 140, 230, 130, 100, 90, 130]
          },
          {
            name: "数据b",
            type: "line",
            yAxisIndex: 1,
            data: [150, 100, 200, 140, 130, 110, 130]
          }
        ]
      };
      echarts2.setOption(echarts2Option);

      const echarts3Option = {
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
          data: [
            "偏好1",
            "偏好2",
            "偏好3",
            "其他",
          ]
        },
        series: [
          {
            name: "Access From",
            type: "pie",
            radius: ["45%", "60%"],
            labelLine: {
              length: 30
            },
            label: {
              // {a|{a}}{abg|}\n{hr|}\n
              formatter: "{b|{b}：}{c}  {per|{d}%}  ",
              backgroundColor: "#F6F8FC",
              borderColor: "#8C8D8E",
              borderWidth: 1,
              borderRadius: 4,
              rich: {
                // a: {
                //   color: "#6E7079",
                //   lineHeight: 22,
                //   align: "center"
                // },
                // hr: {
                //   borderColor: "#8C8D8E",
                //   width: "100%",
                //   borderWidth: 1,
                //   height: 0
                // },
                b: {
                  color: "#4C5058",
                  fontSize: 14,
                  fontWeight: "bold",
                  lineHeight: 33
                },
                per: {
                  color: "#fff",
                  backgroundColor: "#4C5058",
                  padding: [3, 4],
                  borderRadius: 4
                }
              }
            },
            data: [
              { value: 1048, name: "偏好1" },
              { value: 335, name: "偏好2" },
              { value: 310, name: "偏好3" },
              { value: 251, name: "其他" },
            ]
          }
        ]
      };
      const echarts3 = echarts.init(this.$refs.echarts3);
      echarts3.setOption(echarts3Option);
    });
  }
};
</script>
  <style lang="less" scoped>
.container {
  display: flex;
  flex-direction: column;
  height: 89vh;

  .top {
    display: flex;
    flex: 1;
    margin-bottom: 10px;
    .component1 {
      background-color: #fff;
      border-radius: 15px;
      display: flex;
      flex: 3;
      flex-direction: column;
      margin-right: 10px;
      align-items: center;

      .select-border {
        display: flex;
        height: 60px;
        width: 400px;
        border: 1px solid var(--el-border-color);
        border-radius: 15px;
        padding: 10px;
        margin-top: 20px;
        background-color: #6292f4;

        align-items: center;
        .select {
          border-radius: 15px;
        }
        .text-name {
          color: #fff;
          font-weight: 700;
          font-size: 20px;
          margin-left: 20px;
          margin-right: 20px;
        }
      }
      .classify {
        display: flex;
        flex-direction: column;
        .text-title {
          color: #000;
          font-weight: bold;
          font-size: 18px;
          margin-top: 20px;
          margin-bottom: 20px;
        }
        .card {
          padding: 6px;
          margin: 0;
          border: 1px solid white;
          border-block-end-color: var(--el-card-border-color);
          border-block-start-color: var(--el-card-border-color);
          .my-icon {
            width: 10px;
            height: 40px;
            margin-top: 10px;
            margin-right: 20px;
          }
          .detail {
            display: flex;
            .desc {
              line-height: 20px;
              height: 20px;
              width: 340px;
              font-size: 20px;
            }
            .num {
              line-height: 20px;
              height: 20px;
              font-size: 20px;
            }
          }
        }
      }
    }
    .component2 {
      margin-left: 10px;
      background-color: #fff;
      border-radius: 15px;
      display: flex;
      flex: 7;
      flex-direction: column;
      padding: 20px;
      .text-title {
        color: #000;
        font-weight: bold;
        font-size: 18px;
        margin: 20px;
      }
    }
  }
  .button {
    display: flex;
    flex: 1;
    margin-top: 10px;
    .component1 {
      margin-left: 10px;
      background-color: #fff;
      border-radius: 15px;
      display: flex;
      flex: 1;
      flex-direction: column;
      padding: 20px;
      .text-title {
        color: #000;
        font-weight: bold;
        font-size: 18px;
        margin-top: 20px;
        margin-bottom: 20px;
      }
    }
    .component2 {
      margin-right: 10px;
      background-color: #fff;
      border-radius: 15px;
      display: flex;
      flex: 2;
      flex-direction: column;
      padding: 20px;
      .text-title {
        color: #000;
        font-weight: bold;
        font-size: 18px;
        margin-top: 20px;
        margin-bottom: 20px;
      }
    }
  }
}
</style>