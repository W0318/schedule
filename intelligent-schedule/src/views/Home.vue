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
      <el-card class="component2">
        <div ref="echarts">

        </div>
      </el-card>
    </el-row>
    <el-row class="button">
      <el-card class="component2"></el-card>
      <el-card class="component1"></el-card>
    </el-row>
    <!-- <div class="button"></div> -->
  </div>
</template>
  
<script setup>
import { ref,reactive } from "vue";

const value = ref("");

const options = [
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
];
// const getCountData = async()=>{
//     le
// }
const countData = [
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
];

//关于echarts表格的渲染
let xOptions = reactive({

})

//饼状图
let pieOptions =reactive({

})
let orderData = reactive({
    xData:[],
    series:[],
})
let userData = reactive({

})
let videoData = reactive({

})
//获取数据
// const getEchartsData = async() =>{
//  let result = await proxy
// }
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
    }
  }
  .button {
    display: flex;
    flex: 1;
    margin-top: 10px;
    .component1 {
      background-color: #fff;
      border-radius: 15px;
      display: flex;
      flex: 1;
      flex-direction: column;
      margin-left: 10px;
      justify-content: center;
      align-items: center;
      .select-border {
        display: flex;
        height: 60px;
        border: 1px solid var(--el-border-color);
        border-radius: 15px;
        padding: 10px;
        background-color: #6292f4;
        justify-content: center;
        align-items: center;
        .select {
          border-radius: 15px;
        }
        .text-name {
          color: #fff;
          font-weight: 500;
          font-size: 18px;
          margin: 5px;
        }
      }
      .text-title {
        color: #000;
        font-weight: bold;
        font-size: 18px;
        margin: 20px;
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
    }
  }
}
</style>