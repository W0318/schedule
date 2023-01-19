import Mock from "mockjs";

// 图表数据
let List = [];
export default {
  getStatisticalData: () => {
    for (let i = 0; i < 7; i++) {
      List.push(
        Mock.mock({
          数据1: Mock.Random.float(100, 8000, 0, 0),
          数据2: Mock.Random.float(100, 8000, 0, 0),
        })
      );
    }
    return {
      code: 20000,
      data: {
        // 饼图
        videoData: [
          {
            name: "极高",
            value: Mock.Random.float(0, 100, 0, 2)
          },
          {
            name: "高",
            value: Mock.Random.float(0, 100, 0, 2)
          },
          {
            name: "中",
            value: Mock.Random.float(0, 100, 0, 2)
          },
          {
            name: "低",
            value: Mock.Random.float(0, 100, 0, 2)
          }
        ],
        videoData1: [
          {
            name: "APP",
            value: Mock.Random.float(0, 100, 0, 2)
          }
        ],
        videoData2: [
          {
            name: "网站",
            value: Mock.Random.float(0, 100, 0, 2)
          },
          {
            name: "",
            value: Mock.Random.float(0, 100, 0, 2)
          }
        ],
        mapData: [
          { name: "北京", value: Mock.Random.integer(100, 1000) },
          { name: "天津", value: Mock.Random.integer(100, 1000) },
          { name: "上海", value: Mock.Random.integer(100, 1000) },
          { name: "重庆", value: Mock.Random.integer(100, 1000) },
          { name: "河北", value: Mock.Random.integer(100, 1000) },
          { name: "河南", value: Mock.Random.integer(100, 1000) },
          { name: "云南", value: Mock.Random.integer(100, 1000) },
          { name: "辽宁", value: Mock.Random.integer(100, 1000) },
          { name: "黑龙江", value: Mock.Random.integer(100, 1000) },
          { name: "湖南", value: Mock.Random.integer(100, 1000) },
          { name: "安徽", value: Mock.Random.integer(100, 1000) },
          { name: "山东", value: Mock.Random.integer(100, 1000) },
          { name: "新疆", value: Mock.Random.integer(100, 1000) },
          { name: "江苏", value: Mock.Random.integer(100, 1000) },
          { name: "浙江", value: Mock.Random.integer(100, 1000) },
          { name: "江西", value: Mock.Random.integer(100, 1000) },
          { name: "湖北", value: Mock.Random.integer(100, 1000) },
          { name: "广西", value: Mock.Random.integer(100, 1000) },
          { name: "甘肃", value: Mock.Random.integer(100, 1000) },
          { name: "山西", value: Mock.Random.integer(100, 1000) },
          { name: "内蒙古", value: Mock.Random.integer(100, 1000) },
          { name: "陕西", value: Mock.Random.integer(100, 1000) },
          { name: "吉林", value: Mock.Random.integer(100, 1000) },
          { name: "福建", value: Mock.Random.integer(100, 1000) },
          { name: "贵州", value: Mock.Random.integer(100, 1000) },
          { name: "广东", value: Mock.Random.integer(100, 1000) },
          { name: "青海", value: Mock.Random.integer(100, 1000) },
          { name: "西藏", value: Mock.Random.integer(100, 1000) },
          { name: "四川", value: Mock.Random.integer(100, 1000) },
          { name: "宁夏", value: Mock.Random.integer(100, 1000) },
          { name: "海南", value: Mock.Random.integer(100, 1000) },
          { name: "台湾", value: Mock.Random.integer(100, 1000) },
          { name: "香港", value: Mock.Random.integer(100, 1000) },
          { name: "澳门", value: Mock.Random.integer(100, 1000) }
        ],
        // 柱状图
        userData: [
          {
            date: "2019-8",
            new: Mock.Random.integer(1, 2500000),
            active: Mock.Random.integer(100, 1000)
          },
          {
            date: "2019-9",
            new: Mock.Random.integer(1, 2500000),
            active: Mock.Random.integer(100, 1000)
          },
          {
            date: "2019-10",
            new: Mock.Random.integer(1, 2500000),
            active: Mock.Random.integer(100, 1000)
          },
          {
            date: "2019-11",
            new: Mock.Random.integer(1, 2500000),
            active: Mock.Random.integer(100, 1000)
          },
          {
            date: "2019-12",
            new: Mock.Random.integer(1, 2500000),
            active: Mock.Random.integer(100, 1000)
          }
        ],
        // 柱状图
        userData1: [
          {
            date: "2019-8",
            new: Mock.Random.integer(1, 2500000),
            active: Mock.Random.integer(100, 1000)
          },
          {
            date: "2019-9",
            new: Mock.Random.integer(1, 2500000),
            active: Mock.Random.integer(100, 1000)
          },
          {
            date: "2019-10",
            new: Mock.Random.integer(1, 2500000),
            active: Mock.Random.integer(100, 1000)
          },
          {
            date: "2019-11",
            new: Mock.Random.integer(1, 2500000),
            active: Mock.Random.integer(100, 1000)
          },
          {
            date: "2019-12",
            new: Mock.Random.integer(1, 2500000),
            active: Mock.Random.integer(100, 1000)
          }
        ],
        // 折线图
        orderData: {
          names: ["拦截数量"],
          date: [
            "0",
            "2",
            "4",
            "6",
            "8",
            "10",
            "12",
          ],
          data: List
        },
        tableData: [
          {
            name: "ES6",
            unit: "Kbps",
            todayBuy: Mock.Random.float(100, 1000, 0, 2)
          }
        ]
      }
    };
  }
};

