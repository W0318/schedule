<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="180px">
        <el-card class="left-nav">
          <el-tree
            :data="storeData"
            :props="defaultProps"
            default-expand-all
            highlight-current
            node-key="nodeId"
            @node-click="handleNodeClick"
          ></el-tree>
        </el-card>
      </el-aside>
      <el-container>
        <el-header>
          <el-card class="right-header">
            <el-form>
              <el-row :gutter="30">
                <el-col :span="6">
                  <el-form-item label="姓名：">
                    <el-input
                      v-model="inputEmployeeName"
                      placeholder="请输入姓名"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="岗位：">
                    <el-cascader
                      v-model="value1"
                      :options="options1"
                      :props="{ expandTrigger: 'hover' }"
                      @change="handleChange1"
                    ></el-cascader>
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item class="form-btn">
                    <el-button @click="getEmployees" type="primary"
                      >查询</el-button
                    >
                    <el-button @click="resetTextAndSelect">重置</el-button>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </el-card>
        </el-header>
        <el-main>
          <el-card class="main-card">
            <!-- 按钮区域 -->
            <div class="btns">
              <div class="left-btn">
                <el-button plain type="primary" @click="addEmployee"
                  >新建员工</el-button
                >
              </div>
            </div>
            <!-- 列表区域 -->
            <el-table :data="tableData" style="width: 100%" border stripe>
              <el-table-column
                label="编号"
                prop="employeeId"
                width="60px"
                align="center"
              />
              <el-table-column
                prop="employeeName"
                label="姓名"
                width="100"
                align="center"
              />
              <el-table-column prop="position" label="岗位" align="center" />
              <el-table-column prop="email" label="邮箱" align="center" />
              <el-table-column prop="phone" label="联系电话" align="center" />
              <el-table-column
                prop="state"
                label="状态"
                width="100"
                align="center"
              >
                <template v-slot="scope">
                  <el-tag type="success" v-if="scope.row.state === 1"
                    >已启用</el-tag
                  >
                  <el-tag type="danger" v-else>已停用</el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="180px" align="center">
                <template v-slot="scope">
                  <el-link
                    type="primary"
                    :icon="Edit"
                    @click="handleEdit(scope.row)"
                    title="编辑员工内容"
                    >编辑</el-link
                  >
                  <el-link
                    type="primary"
                    :icon="Delete"
                    @click="handleDelete(scope.row)"
                    title="删除员工内容"
                    >删除</el-link
                  >
                  <el-link
                    type="primary"
                    :icon="Enable"
                    @click="handleEnable(scope.row)"
                    title="启用门店内容"
                    v-if="scope.row.state === 0"
                    >启用</el-link
                  >
                  <el-link
                    type="primary"
                    :icon="Deactivate"
                    @click="handleDeactivate(scope.row)"
                    title="停用门店内容"
                    v-else
                    >停用</el-link
                  >
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-main>
      </el-container>

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
    </el-container>
  </div>
</template>


<script>
import { ref } from "vue";
import { ElMessageBox, ElMessage } from "element-plus";
import {
  getStoreName,
  getEmployee,
  updateEmployeeStateOne,
  updateEmployeeStateZero,
  getEmployeeByEmployeeName,
  getEmployeeByPosition,
  getEmployeeByEmployeeNameAndPosition,
  deleteEmployee,
  deletePreferenceByEmployeeId,
  getEmployeeStoreName,
  updateEmployee,
  getEmployeeByStoreId,
  getPreferenceHaveThree,
  insertPreferenceHaveThree,
  updatePreferenceHaveThree,
  deletePreferenceHaveThree,
  getPreferenceHaveTwo,
  insertPreferenceHaveTwo,
  updatePreferenceHaveTwo,
  deletePreferenceHaveTwo,
  getPreferenceHaveOne,
  insertPreferenceHaveOne,
  updatePreferenceHaveOne,
  deletePreferenceHaveOne,
  getAllStore,
} from "@/api";
export default {
  inject: ["reload"],
  data() {
    return {
      a: "",
      likeDay: [],
      likeTime: [],
      dayAndWeek: "",
      fullTime: "",
      startTime: "",
      endTime: "",
      startTime1: "",
      endTime1: "",
      value3: ref([]),
      inputEmployeeName: "",
      value1: "",
      // 岗位下拉框
      options1: [
        {
          value: "全部",
          label: "全部",
        },
        {
          value: "门店经理",
          label: "门店经理",
        },
        {
          value: "副经理",
          label: "副经理",
        },
        {
          value: "小组长",
          label: "小组长",
        },
        {
          value: "店员",
          label: "店员",
        },
      ],
      // 左侧导航数据
      storeData: [],
      defaultProps: {
        children: "children",
        label: "label",
      },
      //列表数据
      tableData: [],
      tableDatas: {
        employeeId: "",
        employeeName: "",
        // storeId: "",
        position: "",
        email: "",
        phone: "",
        state: "",
      },
      //编辑弹窗
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
      field103Options: [
        {
          label: "门店经理",
          value: "门店经理",
        },
        {
          label: "副经理",
          value: "副经理",
        },
        {
          label: "小组长",
          value: "小组长",
        },
        {
          label: "店员",
          value: "店员",
        },
      ],
      field106Options: [],
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
  methods: {
    //设置树的选中状态
    handleNodeClick(data) {
      this.$nextTick(() => {
        // this.$refs.tree.setCurrentKey(data.nodeId);
        // console.log("我的" + data.label);
        if (data.label === "全国零售门店") {
          getEmployee().then((datas) => {
            this.tableData = datas.data;
            // console.log(datas.data);
          });
        } else {
          getEmployeeByStoreId(data.label).then((datas) => {
            this.tableData = datas.data;
            // console.log(datas.data);
          });
        }
      });
    },

    //跳转到添加员工页
    addEmployee() {
      this.$router.push({
        name: "addEmployee",
      });
    },
    //点击编辑button编辑员工
    handleEdit(row) {
      this.dialogUpdateVisible = true;
      getAllStore().then((datas) => {
        this.field106Options = datas.data;
        getEmployeeStoreName(row.employeeId).then((datas) => {
          this.field106Options.forEach((element) => {
            if (element.storeId === datas.data.storeId) {
              this.formData.field106 = element;
            }
          });
        });
      });
      this.formData.field102 = row.employeeId;
      this.formData.field101 = row.employeeName;
      this.formData.field105 = row.phone;
      this.formData.field103 = row.position;
      this.formData.field107 = row.email;
      getPreferenceHaveOne(row.employeeId).then((datas) => {
        if (datas.data.length != 0) {
          this.likeDay = datas.data[0].preferValue.split(",");
          this.value3 = this.likeDay;
        } else {
          this.value3 = this.$options.data().value3;
        }
      });
      getPreferenceHaveTwo(row.employeeId).then((datas) => {
        if (datas.data.length != 0) {
          this.likeTime = datas.data[0].preferValue.split(",");
        } else {
          this.startTime = this.$options.data().startTime;
          this.startTime1 = this.$options.data().startTime1;
          this.endTime = this.$options.data().endTime;
          this.endTime1 = this.$options.data().endTime1;
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
      getPreferenceHaveThree(row.employeeId).then((datas) => {
        if (datas.data.length != 0) {
          this.formData.field108 = eval(
            "(" + datas.data[0].preferValue + ")"
          ).week;
          this.formData.field109 = eval(
            "(" + datas.data[0].preferValue + ")"
          ).day;
        } else {
          this.formData.field108 = this.$options.data().formData.field108;
          this.formData.field109 = this.$options.data().formData.field109;
        }
      });
    },
    //点击确认button确认编辑
    async handelUpdate() {
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
      await this.reload();
      ElMessage({
        type: "success",
        message: "编辑员工成功",
      });
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
      // console.log(this.formData.field106.storeId);
      // console.log(this.value3);
      // console.log(this.startTime);
      // console.log(this.startTime1);
      // console.log(this.endTime);
      // console.log(this.endTime1);
      // console.log(this.formData.field108);
      // console.log(this.formData.field109);
    },
    // 查询门店
    getEmployees() {
      if (this.value1 == "全部") {
        if (this.inputEmployeeName == "") {
          getEmployee().then((datas) => {
            this.tableData = datas.data;
            console.log(datas.data);
          });
        } else if (this.inputEmployeeName != "") {
          getEmployeeByEmployeeName(this.inputEmployeeName).then((datas) => {
            this.tableData = datas.data;
            console.log(datas.data);
          });
        }
      } else {
        if (this.inputEmployeeName == "" && this.value1 == "") {
          getEmployee().then((datas) => {
            this.tableData = datas.data;
            console.log(datas.data);
          });
        } else if (this.value1 == "" && this.inputEmployeeName != "") {
          getEmployeeByEmployeeName(this.inputEmployeeName).then((datas) => {
            this.tableData = datas.data;
            console.log(datas.data);
          });
        } else if (this.inputEmployeeName == "" && this.value1 != "") {
          getEmployeeByPosition(this.value1).then((datas) => {
            this.tableData = datas.data;
            console.log(datas.data);
          });
        } else {
          getEmployeeByEmployeeNameAndPosition(
            this.inputEmployeeName,
            this.value1
          ).then((datas) => {
            this.tableData = datas.data;
            console.log(this.value);
            console.log(datas.data);
          });
        }
      }
    },
    //点击删除button删除员工
    handleDelete(row) {
      console.log(row.employeeId);
      ElMessageBox.confirm("该员工将永久被删除，确定吗?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(async () => {
        deletePreferenceByEmployeeId(row.employeeId);
        deleteEmployee(row.employeeId);
        await this.reload();
        ElMessage({
          type: "success",
          message: "删除员工成功",
        });
      });
    },
    //点击启用button启用员工
    handleEnable(row) {
      console.log(row.employeeId);
      ElMessageBox.confirm("该员工将被启用，确定吗?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(async () => {
        updateEmployeeStateZero(row.employeeId);
        await this.reload();
        ElMessage({
          type: "success",
          message: "启用员工成功",
        });
      });
    },
    //点击停用button停用员工
    handleDeactivate(row) {
      console.log(row.employeeId);
      ElMessageBox.confirm("该员工将被停用，确定吗?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(async () => {
        updateEmployeeStateOne(row.employeeId);
        await this.reload();
        ElMessage({
          type: "success",
          message: "停用员工成功",
        });
      });
    },
    // 重置门店
    resetTextAndSelect() {
      this.value1 = "";
      this.inputEmployeeName = "";
    },
  },

  mounted() {
    // 获取所有门店信息
    getEmployee().then((datas) => {
      this.tableData = datas.data;
      // console.log(datas.data);
    });
    getStoreName().then((datas) => {
      this.storeData = [
        {
          label: "全国零售门店",
          children: [...datas.data],
        },
      ];
      // console.log(...datas.data);
    });
  },
};
</script>
<style scoped>
.main-box {
  display: flex;
  justify-content: space-between;
}
.el-aside {
  overflow: hidden;
}
.el-main {
  padding: 0;
  margin-left: 20px;
  margin-top: 20px;
  width: 1050px;
}
.el-header {
  padding: 0;
  width: 1050px;
}
.left-nav {
  width: 100%;
  height: 600px;
}
.right-header {
  margin-left: 20px;
  width: 100%;
  height: 70px;
  padding: 0;
}

.btns {
  display: flex;
  justify-content: space-between;
}

.el-table {
  margin-top: 20px;
  height: 425px;
}
.el-link {
  margin-left: 10px;
}
</style>