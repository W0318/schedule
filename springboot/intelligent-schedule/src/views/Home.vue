<template>
  <div class="container">
    <el-row class="top">
      <el-card class="component1">
        <el-clo>
          <text class="text-title">排班统计</text>
          <div class="select-border">
            <text class="text-name">门 店 名 称 :</text>

            <el-select v-model="options.value" class="select" placeholder="浙江分公司"  size="large">
              <el-option
                  v-for="item in options"
                  :key="item.storeId"
                  :label="item.storeName"
                  :value="item.storeId"
                  @click="changeInfo(item)"
              >
              </el-option>
            </el-select>
          </div>
          <div class="classify">

            <el-card
                class="card"
                shadow="never"
                v-for="item in countData"
                :key="item.name"
                :body-style="{display: 'flex',padding:0 }"
            >
              <div class="detail">
<!--                <div class="my-icon" :style="{background : item.color}"></div>-->
                <p class="desc">{{ item.name }}</p>
                <p class="num">{{ item.value }}</p>
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
import {
  getData,
  getStore,
} from "@/api";
import * as echarts from "echarts";
// import {ref} from "vue";


// let value;
export default {
  // value:ref(''),
  data() {
    return {
      newStoreId:'',
      StoreData: [],
      tableData: [],
      options: [{
        storeId: '店id',
        storeName: '店名',
        address:'地点',
        manger:"负责人",
        size:'面积',
        workers:'1',
      }],
      countData: [
        {
          name: "公司负责人: ",
          value: "小明",
          color: "#409EFF"
        },
        {
          name: "在值员工数: ",
          value: 23,
          color: "#f28d3c"
        },
        {
          name: "地址: ",
          value: "上塘街道湖州街51号",
          color: "#f23c3c"
        },
        {
          name: "店面面积: ",
          value: 200,
          color: "#409EFF"
        }
      ]
    };
  },
  // setup(){
  //   const changeInfo=(e)=>{
  //     this.$data.newStoreId=e
  //     // this.data.newStoreId=e
  //     console.log(e)
  //   }
  //   return {changeInfo}
  // },
  methods:{
    // eslint-disable-next-line vue/no-dupe-keys
     changeInfo(e){
      this.newStoreId=e
      // this.data.newStoreId=e
      //  console.log(this.newStoreId)
      console.log(e)
       this.countData[0].value=e.manger
       this.countData[2].value=e.address
       this.countData[1].value=e.workers
       this.countData[3].value=e.size
    }
  },
  mounted() {

    getStore().then((datas) => {
      this.options = datas.data;
      // this.options.value = this.options.
      console.log(datas.data)
    });
    getData().then(({data}) => {
      // console.log(data);
      console.log(this.newStoreId)
      const {tableData} = data.data
      this.tableData = tableData
      //准备基础dom ，初始化echarts实例
      const echarts1 = echarts.init(this.$refs.echarts1);
      //指定图标配置项和数据
      var echarts1Option = {};
      const {orderData} = data.data;
      echarts1Option.grid = {
        left: "3%",
        right: "4%",
        bottom: "3%",
        containLabel: true
      };
      // const xAxis = ["周一", "周二", "周三", "周四", "周五", "周六", "周日"];
      const legendData = ["昨天客流数据", "今日客流数据", "明日客流数据"];
      const xAxisData = {
        type: "category",
        boundaryGap: false,
        data: ["8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30",
          "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00"]
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
            dataView: {readOnly: false},
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
            name: "本周每日值班人数",
            type: "bar",
            // xAxisIndex: 1,
            yAxisIndex: 1,
            data: [100, 140, 230, 130, 100, 90, 130]
          },
          {
            name: "上周每日值班人数",
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
              {value: 1048, name: "偏好1"},
              {value: 335, name: "偏好2"},
              {value: 310, name: "偏好3"},
              {value: 251, name: "其他"},
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
    margin-top: 5px;
    margin-bottom: 5px;

    .component1 {
      background-color: #fff;
      border-radius: 15px;
      display: flex;
      flex: 3;
      flex-direction: column;
      margin-right: 10px;
      align-items: center;
      .text-title {
        color: #000;
        font-weight: bold;
        font-size: 18px;
        margin-top: 20px;
        margin-bottom: 20px;
      }
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
              width: 120px;
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
    margin-top: 5px;

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