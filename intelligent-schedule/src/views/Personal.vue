<template>
  <el-card class="father">
    <h4>个人中心</h4>
    <div class="leftright">
      <div class="left-bar">
        <span class="el-dropdown-link">
          <img class="user" src="../assets/my.png" alt />
        </span>
      </div>
      <div class="right-bar">
        <div class="one">
          <p>{{ employeeId }}</p>
          <p>{{ employeeName }}</p>
          <p>{{ storeName }}</p>
          <p>{{ position }}</p>
        </div>
        <div class="two">
          <p>{{ email }}</p>
          <p>{{ phone }}</p>
          
        </div>
        <div class="three">
          <p>{{ one }}</p>
          <p>{{ two }}</p>
          <p>{{ three }}</p>
          <p>{{ four }}</p>
        </div>
      </div>
      <div class="btns">
        <el-button type="primary" @click="handleEdit">编辑</el-button>
      </div>
    </div>

    <!-- <div class="message-box" :inline="true">
      <p>{{ email }}</p>
      <p>{{ phone }}</p>
      <p>{{ one }}</p>
      <p>{{ two }}</p>
      <p>{{ three }}</p>
      <p>{{ four }}</p>
    </div> -->

    <!-- 编辑弹窗 -->
    <el-dialog title="编辑" v-model="dialogUpdateVisible">
      <el-row :gutter="15">
        <el-form
          ref="elForm"
          :model="formData"
          :rules="rules"
          size="medium"
          label-width="100px"
        >
          <el-row class="one">
            <el-col :span="12">
              <el-form-item label="员工编号：" prop="field102">
                <el-input
                  v-model="formData.field102"
                  disabled
                  placeholder="请输入员工编号"
                  clearable
                  :style="{ width: '100%' }"
                >
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="员工姓名：" prop="field101">
                <el-input
                  v-model="formData.field101"
                  disabled
                  placeholder="请输入员工姓名："
                  clearable
                  :style="{ width: '100%' }"
                >
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row class="two">
            <el-col :span="12">
              <el-form-item label="所属门店：" prop="field106">
                <el-select
                  v-model="formData.field106"
                  disabled
                  value-key="storeId"
                  placeholder="请选择所属门店"
                  clearable
                  :style="{ width: '100%' }"
                >
                  <el-option
                    v-for="item in field106Options"
                    :key="item.storeId"
                    :label="item.storeName"
                    :value="item"
                    :disabled="item.disabled"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="员工电话：" prop="field105">
                <el-input
                  v-model="formData.field105"
                  placeholder="请输入员工电话"
                  clearable
                  :style="{ width: '100%' }"
                >
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row class="three">
            <el-col :span="12">
              <el-form-item label="岗位：" prop="field103">
                <el-select
                  v-model="formData.field103"
                  disabled
                  placeholder="请输入岗位"
                  clearable
                  :style="{ width: '100%' }"
                >
                  <el-option
                    v-for="(item, employeeId) in field103Options"
                    :key="employeeId"
                    :label="item.label"
                    :value="item.value"
                    :disabled="item.disabled"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="员工电邮：" prop="field107">
                <el-input
                  v-model="formData.field107"
                  placeholder="请输入员工电邮"
                  clearable
                  :style="{ width: '100%' }"
                >
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row class="four">
            <el-col :span="20">
              <el-form-item label="工作日偏好：" prop="value3">
                <el-select
                  v-model="value3"
                  clearable
                  multiple
                  collapse-tags
                  collapse-tags-tooltip
                  placeholder="请选择工作日偏好"
                  :style="{ width: '100%' }"
                >
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row class="five">
            <el-col :span="12">
              <el-form-item label="时间偏好一：" prop="startTime">
                <el-time-select
                  v-model="startTime"
                  :max-time="endTime"
                  class="mr-4"
                  placeholder="开始时间"
                  start="08:00"
                  step="00:30"
                  end="23:00"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="endTime">
                <el-time-select
                  v-model="endTime"
                  :min-time="startTime"
                  :max-time="startTime1"
                  placeholder="结束时间"
                  start="08:00"
                  step="00:30"
                  end="23:00"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row class="five">
            <el-col :span="12">
              <el-form-item label="时间偏好二：" prop="startTime1">
                <el-time-select
                  v-model="startTime1"
                  :min-time="endTime"
                  :max-time="endTime1"
                  class="mr-4"
                  placeholder="开始时间"
                  start="08:00"
                  step="00:30"
                  end="23:00"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="endTime1">
                <el-time-select
                  v-model="endTime1"
                  :min-time="startTime1"
                  placeholder="结束时间"
                  start="08:00"
                  step="00:30"
                  end="23:00"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row class="six">
            <el-col :span="12">
              <el-form-item label="周工作时长：" prop="field108">
                <el-input
                  v-model="formData.field108"
                  placeholder="请输入周工作时长偏好"
                  clearable
                  :style="{ width: '100%' }"
                >
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="日工作时长：" prop="field109">
                <el-input
                  v-model="formData.field109"
                  placeholder="请输入日工作时长偏好"
                  clearable
                  :style="{ width: '100%' }"
                >
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-row>
      <template v-slot:footer>
        <el-button @click="cancleUpdate">取消</el-button>
        <el-button type="primary" @click="handelUpdate">确定</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script>
import { ref } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  getPreferenceHaveOne,
  getPreferenceHaveTwo,
  getPreferenceHaveThree,
  getAllStore,
  getEmployeeStoreName,
  updateEmployee,
  insertPreferenceHaveOne,
  updatePreferenceHaveOne,
  deletePreferenceHaveOne,
  insertPreferenceHaveThree,
  updatePreferenceHaveThree,
  deletePreferenceHaveThree,
  insertPreferenceHaveTwo,
  updatePreferenceHaveTwo,
  deletePreferenceHaveTwo,
} from "@/api";
export default {
  data() {
    return {
      employeeId: "",
      employeeName: "",
      storeName: "",
      position: "",
      email: "",
      phone: "",
      one: "工作日偏好:\u2003",
      two: "工作时间偏好:\u2003",
      three: "周工作时长偏好:\u2003",
      four: "日工作时长偏好:\u2003",
      value3: ref([]),
      a: "",
      likeDay: [],
      likeTime: [],
      dayAndWeek: "",
      fullTime: "",
      startTime: "",
      endTime: "",
      startTime1: "",
      endTime1: "",
      field106Options: [],
      formData: {
        field101: "", //员工姓名
        field102: "", //员工编号
        field103: "", //岗位
        field105: "", //员工电话
        field106: {}, //所属门店
        field107: "", //员工电邮
        field108: "", //周工作时长
        field109: "", //日工作时长
      },
      rules: {
        field101: [
          {
            required: true,
            message: "请输入员工姓名：",
            trigger: "blur",
          },
        ],
        field102: [
          {
            required: true,
            message: "请输入员工编号",
            trigger: "blur",
          },
        ],
        field103: [
          {
            required: true,
            message: "请输入岗位",
            trigger: "change",
          },
        ],
        field105: [
          {
            required: true,
            message: "请输入员工电话",
            trigger: "blur",
          },
        ],
        field106: [
          {
            required: true,
            message: "请选择所属门店",
            trigger: "change",
          },
        ],
        field107: [
          {
            required: true,
            message: "请输入员工电邮",
            trigger: "blur",
          },
        ],
      },
      options: [
        {
          value: "1",
          label: "星期一",
        },
        {
          value: "2",
          label: "星期二",
        },
        {
          value: "3",
          label: "星期三",
        },
        {
          value: "4",
          label: "星期四",
        },
        {
          value: "5",
          label: "星期五",
        },
        {
          value: "6",
          label: "星期六",
        },
        {
          value: "7",
          label: "星期日",
        },
      ],
      dialogUpdateVisible: false,
    };
  },
  mounted() {
    this.employeeId =
      "员工编号:\u2003" +
      JSON.parse(sessionStorage.getItem("employee")).employeeId;
    this.employeeName =
      "员工姓名:\u2003" +
      JSON.parse(sessionStorage.getItem("employee")).employeeName;
    this.storeName =
      "所属门店:\u2003" +
      JSON.parse(sessionStorage.getItem("employee")).employeeName;
    this.position =
      "岗位:\u2003" + JSON.parse(sessionStorage.getItem("employee")).position;
    this.email =
      "邮箱:\u2003" + JSON.parse(sessionStorage.getItem("employee")).email;
    this.phone =
      "联系电话:\u2003" + JSON.parse(sessionStorage.getItem("employee")).phone;
    getPreferenceHaveOne(
      JSON.parse(sessionStorage.getItem("employee")).employeeId
    ).then((datas) => {
      if (datas.data.length != 0) {
        this.likeDay = datas.data[0].preferValue.split(",");
        this.one = "工作日偏好:\u2003" + this.likeDay;
      }
    });
    getPreferenceHaveTwo(
      JSON.parse(sessionStorage.getItem("employee")).employeeId
    ).then((datas) => {
      if (datas.data.length != 0) {
        this.likeTime = datas.data[0].preferValue;
        this.two = "工作时间偏好:\u2003" + this.likeTime;
      }
    });
    getPreferenceHaveThree(
      JSON.parse(sessionStorage.getItem("employee")).employeeId
    ).then((datas) => {
      if (datas.data.length != 0) {
        this.three =
          "周工作时长偏好:\u2003" +
          eval("(" + datas.data[0].preferValue + ")").week;
        this.four =
          "日工作时长偏好:\u2003" +
          eval("(" + datas.data[0].preferValue + ")").day;
      }
    });
  },
  methods: {
    //点击编辑button编辑员工
    handleEdit() {
      this.dialogUpdateVisible = true;
      getAllStore().then((datas) => {
        this.field106Options = datas.data;
        getEmployeeStoreName(
          JSON.parse(sessionStorage.getItem("employee")).employeeId
        ).then((datas) => {
          this.field106Options.forEach((element) => {
            if (element.storeId === datas.data.storeId) {
              this.formData.field106 = element;
            }
          });
        });
      });
      this.formData.field102 = JSON.parse(
        sessionStorage.getItem("employee")
      ).employeeId;
      this.formData.field101 = JSON.parse(
        sessionStorage.getItem("employee")
      ).employeeName;
      this.formData.field105 = JSON.parse(
        sessionStorage.getItem("employee")
      ).phone;
      this.formData.field103 = JSON.parse(
        sessionStorage.getItem("employee")
      ).position;
      this.formData.field107 = JSON.parse(
        sessionStorage.getItem("employee")
      ).email;
      getPreferenceHaveOne(
        JSON.parse(sessionStorage.getItem("employee")).employeeId
      ).then((datas) => {
        if (datas.data.length != 0) {
          this.likeDay = datas.data[0].preferValue.split(",");
          this.value3 = this.likeDay;
        }
      });
      getPreferenceHaveTwo(
        JSON.parse(sessionStorage.getItem("employee")).employeeId
      ).then((datas) => {
        if (datas.data.length != 0) {
          this.likeTime = datas.data[0].preferValue.split(",");
        }
        if (this.likeTime.length == 1) {
          this.startTime = this.likeTime[0].slice(0, 5);
          this.endTime = this.likeTime[0].slice(6, 11);
        } else if (this.likeTime.length == 2) {
          this.startTime = this.likeTime[0].slice(0, 5);
          this.endTime = this.likeTime[0].slice(6, 11);
          this.startTime1 = this.likeTime[1].slice(0, 5);
          this.endTime1 = this.likeTime[1].slice(6, 11);
        }
      });
      getPreferenceHaveThree(
        JSON.parse(sessionStorage.getItem("employee")).employeeId
      ).then((datas) => {
        if (datas.data.length != 0) {
          this.formData.field108 = eval(
            "(" + datas.data[0].preferValue + ")"
          ).week;
          this.formData.field109 = eval(
            "(" + datas.data[0].preferValue + ")"
          ).day;
        }
      });
    },
    //点击确认button确认编辑
    handelUpdate() {
      //更新员工信息
      updateEmployee(
        this.formData.field102,
        this.formData.field101,
        this.formData.field106.storeId,
        this.formData.field107,
        this.formData.field103,
        this.formData.field105
      );
      //更新员工工作日偏好
      getPreferenceHaveOne(this.formData.field102).then((datas) => {
        // console.log(1);
        if (this.value3.length != 0 && datas.data.length == 0) {
          insertPreferenceHaveOne(
            this.formData.field102,
            this.value3.join(",")
          );
        } else if (this.value3.length != 0 && datas.data.length != 0) {
          updatePreferenceHaveOne(
            this.formData.field102,
            this.value3.join(",")
          );
        } else if (this.value3.length == 0 && datas.data.length != 0) {
          deletePreferenceHaveOne(this.formData.field102);
        }
      });
      //更新员工工作时间偏好
      getPreferenceHaveTwo(this.formData.field102).then((datas) => {
        console.log(2);
        if (
          this.startTime != "" &&
          this.endTime != "" &&
          this.startTime1 != "" &&
          this.endTime1 != "" &&
          datas.data.length == 0
        ) {
          this.fullTime =
            this.startTime +
            "-" +
            this.endTime +
            "," +
            this.startTime1 +
            "-" +
            this.endTime1;
          insertPreferenceHaveTwo(this.formData.field102, this.fullTime);
        } else if (
          this.startTime != "" &&
          this.endTime != "" &&
          datas.data.length == 0
        ) {
          this.fullTime = this.startTime + "-" + this.endTime;
          insertPreferenceHaveTwo(this.formData.field102, this.fullTime);
        } else if (
          this.startTime1 != "" &&
          this.endTime1 != "" &&
          datas.data.length == 0
        ) {
          this.fullTime = this.startTime1 + "-" + this.endTime1;
          insertPreferenceHaveTwo(this.formData.field102, this.fullTime);
        } else if (
          this.startTime != "" &&
          this.endTime != "" &&
          this.startTime1 != "" &&
          this.endTime1 != "" &&
          datas.data.length != 0
        ) {
          this.fullTime =
            this.startTime +
            "-" +
            this.endTime +
            "," +
            this.startTime1 +
            "-" +
            this.endTime1;
          updatePreferenceHaveTwo(this.formData.field102, this.fullTime);
        } else if (
          this.startTime != "" &&
          this.endTime != "" &&
          datas.data.length != 0
        ) {
          this.fullTime = this.startTime + "-" + this.endTime;
          updatePreferenceHaveTwo(this.formData.field102, this.fullTime);
        } else if (
          this.startTime1 != "" &&
          this.endTime1 != "" &&
          datas.data.length != 0
        ) {
          this.fullTime = this.startTime1 + "-" + this.endTime1;
          updatePreferenceHaveTwo(this.formData.field102, this.fullTime);
        } else if (
          datas.data.length != 0 &&
          this.startTime == "" &&
          this.endTime == "" &&
          this.startTime1 == "" &&
          this.endTime1 == ""
        ) {
          deletePreferenceHaveTwo(this.formData.field102);
        }
      });
      //更新员工工作时长偏好
      getPreferenceHaveThree(this.formData.field102).then((datas) => {
        console.log(3);
        if (
          this.formData.field108 != "" &&
          this.formData.field109 != "" &&
          datas.data.length == 0
        ) {
          this.a = JSON.stringify({
            day: parseInt(this.formData.field109),
            week: parseInt(this.formData.field108),
          });
          insertPreferenceHaveThree(this.formData.field102, this.a);
        } else if (this.formData.field108 != "" && datas.data.length == 0) {
          this.a = JSON.stringify({
            week: parseInt(this.formData.field108),
          });
          insertPreferenceHaveThree(this.formData.field102, this.a);
        } else if (this.formData.field109 != "" && datas.data.length == 0) {
          this.a = JSON.stringify({
            day: parseInt(this.formData.field109),
          });
          insertPreferenceHaveThree(this.formData.field102, this.a);
        } else if (
          this.formData.field108 != "" &&
          this.formData.field109 != "" &&
          datas.data.length != 0
        ) {
          this.a = JSON.stringify({
            day: parseInt(this.formData.field109),
            week: parseInt(this.formData.field108),
          });
          updatePreferenceHaveThree(this.formData.field102, this.a);
        } else if (
          datas.data.length != 0 &&
          this.formData.field108 != "" &&
          this.formData.field109 == ""
        ) {
          this.a = JSON.stringify({
            week: parseInt(this.formData.field108),
          });
          updatePreferenceHaveThree(this.formData.field102, this.a);
        } else if (
          datas.data.length != 0 &&
          this.formData.field109 != "" &&
          this.formData.field108 == ""
        ) {
          this.a = JSON.stringify({
            day: parseInt(this.formData.field109),
          });
          updatePreferenceHaveThree(this.formData.field102, this.a);
        } else if (
          this.formData.field108 == "" &&
          this.formData.field109 == "" &&
          datas.data.length != 0
        ) {
          deletePreferenceHaveThree(this.formData.field102);
        }
      });
      this.dialogUpdateVisible = false;
      // this.value3 = ref([]);
      // this.startTime = "";
      // this.startTime1 = "";
      // this.endTime = "";
      // this.endTime1 = "";
      // this.formData.field108 = "";
      // this.formData.field109 = "";
      // this.likeTime = [];
      // location.reload();
      // this.$router.go(0);
    },
    //点击取消button取消编辑
    cancleUpdate() {
      this.dialogUpdateVisible = false;
      this.value3 = ref([]);
      this.startTime = "";
      this.startTime1 = "";
      this.endTime = "";
      this.endTime1 = "";
      this.formData.field108 = "";
      this.formData.field109 = "";
      this.likeTime = [];
    },
  },
};
</script>

<style scoped>
.father {
  /* border: 1px solid black; */
  position: relative;
  height: 690px;
}
.leftright {
  /* border: 1px solid red; */
  position: absolute;
  width: 97%;
  box-shadow: 0 0 5px 5px #eae9e9;
}
h4 {
  position: relative;
  padding-left: 10px;
}
h4::before {
  position: absolute;
  left: -5px;
  top: 2px;
  content: "";
  width: 4px;
  height: 18px;
  background-color: skyblue;
}
.left-bar {
  float: left;
  width: 20%;
  height: 100%;
  /* position: absolute; */
  /* overflow: auto; */
  /* border: 1px solid black; */
  margin: 0 auto;
  margin-top: 25px;
}
.one {
  /* margin-top: 50px;
  left: 50px; */
  float: left;
  /* left: 600px; */
  width: 15%;
  height: 100%;
  /* position: absolute; */
  /* overflow: auto; */
  /* border: 1px solid black; */
  margin: 0 auto;
  padding: 70px 0;
  margin-left: 50px;
}
.two {
  /* margin-top: 50px;
  margin-left: 20px; */
  float: left;
  /* left: 600px; */
  width: 24%;
  height: 100%;
  /* position: absolute; */
  /* overflow: auto; */
  /* border: 1px solid black; */
  margin: 0 auto;
  padding: 70px 0;
}
.three {
  /* margin-top: 50px;
  margin-left: 20px; */
  float: left;
  /* left: 600px; */
  width: 30%;
  height: 100%;
  /* position: absolute; */
  /* overflow: auto; */
  /* border: 1px solid black; */
  margin: 0 auto;
  padding: 70px 0;
}
.message-box {
  /* text-align: left; */
  top: 180px;
  left: 18px;
  width: 700px;
  height: 350px;
  border: 1px solid black;
  /* margin: 0 auto; */
  display: flex;
  flex-direction: column;
  /* background-color: rgba(255, 255, 255, 0.8); */
  border-radius: 10px;
  /* box-sizing: border-box; */
  box-shadow: 0 0 5px 5px #eae9e9;
  position: absolute;
}
.state {
  margin-left: 25px;
}
.btns {
  /* position: absolute; */
  position: absolute;
  bottom:20px;
  right: 20px;
}
.el-button {
  margin-right: 10px;
}
</style>