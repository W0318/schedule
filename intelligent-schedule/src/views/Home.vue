<template>
  <div class="container">
    <el-row class="top">
      <el-card class="component1">
        <el-clo>
          <text class="text-title">排班统计</text>
          <div class="select-border">
            <text class="text-name">门店名称:</text>

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
        <text class="text-title">历史客流量</text>
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
  postStoreId,
} from "@/api";
import * as echarts from "echarts";
// import {ref} from "vue";


var arr = [];//用来存放最近七天的时间

function getBeforeDate(n) {
  var n = n;
  console.log(n);//
  var d = new Date();
  var year = d.getFullYear();
  var mon = d.getMonth() + 1;
  var day = d.getDate();
  if(day <= n) {
    if(mon > 1) {
      mon = mon - 1;
    } else {
      year = year - 1;
      mon = 12;
    }
  }
  d.setDate(d.getDate() + n); //很重要，+n取得是前一天的时间
  year = d.getFullYear();
  mon = d.getMonth() + 1;
  day = d.getDate();
  var s = (mon < 10 ? ('0' + mon) : mon) + "-" + (day < 10 ? ('0' + day) : day);
  return s;
}

for(var i = 0; i > -30; i--) {
  arr.push(getBeforeDate(i));

}


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
      console.log(e.storeId)
       postStoreId(e.storeId);

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
      const legendData = ["历史客流量数据"];
      const xAxisData = {
        type: "category",
        boundaryGap: false,
        data: arr.reverse(),
          //   ["8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30",
          // "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00"]
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
            data: ["9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30",
              "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00"]
                // ["周一", "周二", "周三", "周四", "周五", "周六", "周日"]
          }
        ],
        yAxis: [
          {
            type: "value",
            scale: true,
            name: "预计时段所需人数",
            max: 10,
            min: 0,
            boundaryGap: [0.2, 0.2]
          },
          {
            type: "value",
            scale: true,
            name: "昨日客流数据",
            max: 40,
            min: 0,
            boundaryGap: [0.2, 0.2]
          }
        ],
        series: [
          {
            name: "预计时段所需人数",
            type: "bar",
            // xAxisIndex: 1,
            yAxisIndex: 0,
            data: [0.0, 0.3, 1.5, 2.9, 3.5, 3.5, 4.6, 6.0, 7.1, 5.7, 6.2, 7.6, 7.7, 6.9, 5.8, 4.5, 4.0, 3.5, 3.1, 1.9, 1.5, 0.6, 0.3, 0.3]
          },
          {
            name: "昨日客流数据",
            type: "line",
            yAxisIndex: 1,
            data: [0.0, 1.3, 5.7, 11.1, 13.4, 13.3, 17.3, 22.8, 26.9, 21.6, 23.4, 28.7, 29.3, 26.4, 22.1, 17.2, 15.2, 13.3, 11.6, 7.2, 5.6, 2.1, 1.3, 1.0]
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
            name: "偏好",
            type: "pie",
            radius: ["45%", "60%"],
            // labelLine: {
            //   length: 30
            // },
            // label: {
            //   // {a|{a}}{abg|}\n{hr|}\n
            //   // formatter: "{b|{b}：}{c}  {per|{d}%}  ",
            //   backgroundColor: "#F6F8FC",
            //   borderColor: "#8C8D8E",
            //   borderWidth: 1,
            //   borderRadius: 4,
            //   rich: {
            //     b: {
            //       color: "#4C5058",
            //       fontSize: 14,
            //       fontWeight: "bold",
            //       lineHeight: 33
            //     },
            //     per: {
            //       color: "#fff",
            //       backgroundColor: "#4C5058",
            //       padding: [3, 4],
            //       borderRadius: 4
            //     }
            //   }
            // },
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
body{
  min-width:1920px;
}
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
      //position: absolute;
      //left: 0;
      background-color: #fff;
      border-radius: 15px;
      display: flex;
      flex: 3;
      flex-direction: column;
      margin-right: 10px;
      //align-items: center;
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
        //width: 300px;
        border: 1px solid var(--el-border-color);
        border-radius: 15px;
        padding: 5px;
        margin-top: 20px;
        background-color: #6292f4;

        align-items: center;

        .select {
          border-radius: 15px;
          width: 180px;
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