<template>
  <el-card>
    <el-form ref="addFormRef" :model="addForm" :rules="addFormRules">
      <h4>基础信息</h4>
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
              <el-form-item label="员工编号：" prop="employeeId">
                <el-input
                  v-model="formData.employeeId"
                  placeholder="请输入员工编号"
                  clearable
                  :style="{ width: '100%' }"
                >
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="员工姓名：" prop="employeeName">
                <el-input
                  v-model="formData.employeeName"
                  placeholder="请输入员工姓名"
                  clearable
                  :style="{ width: '100%' }"
                >
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row class="two">
            <el-col :span="12">
              <el-form-item label="所属门店：" prop="store">
                <el-select
                  v-model="formData.store"
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
              <el-form-item label="员工电话：" prop="phone">
                <el-input
                  v-model="formData.phone"
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
              <el-form-item label="岗位：" prop="position">
                <el-select
                  v-model="formData.position"
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
              <el-form-item label="员工电邮：" prop="email">
                <el-input
                  v-model="formData.email"
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
      <div class="btns">
        <el-button @click="resetForm">取消</el-button>
        <el-button type="primary" @click="submitForm">保存</el-button>
      </div>
    </el-form>
  </el-card>
</template>

<script>
import { ref } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  insertEmployee,
  insertPreferenceHaveOne,
  insertPreferenceHaveTwo,
  insertPreferenceHaveThree,
  getFindIfExistEmployee,
  getAllStore,
} from "@/api";
export default {
  components: {},
  props: [],
  data() {
    return {
      a: "",
      fullTime: "",
      startTime: "",
      endTime: "",
      startTime1: "",
      endTime1: "",
      value3: ref([]),
      formData: {
        employeeName: "",
        employeeId: "",
        store: "",
        phone: "",
        position: "",
        email: "",
        field108: "",
        field109: "",
      },
      rules: {
        employeeName: [
          {
            required: true,
            message: "请输入员工姓名：",
            trigger: "blur",
          },
        ],
        employeeId: [
          {
            required: true,
            message: "请输入员工编号",
            trigger: "blur",
          },
        ],
        store: [
          {
            required: true,
            message: "请选择岗位",
            trigger: "change",
          },
        ],
        phone: [
          {
            required: true,
            message: "请输入员工电话",
            trigger: "blur",
          },
        ],
        position: [
          {
            required: true,
            message: "请选择所属门店",
            trigger: "change",
          },
        ],
        email: [
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
    };
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {
    getAllStore().then((datas) => {
      this.field106Options = datas.data;
      console.log(this.field106Options);
    });
  },
  methods: {
    submitForm() {
      getFindIfExistEmployee(this.formData.employeeId).then((datas) => {
        if (datas.data.length != 0) {
          ElMessage({
            type: "warning",
            message: "员工编号已存在",
          });
        } else {
          if (
            this.formData.employeeId === "" ||
            this.formData.employeeName === "" ||
            this.formData.store.storeId === "" ||
            this.formData.email === "" ||
            this.formData.position === "" ||
            this.formData.phone === ""
          ) {
            ElMessage({
              type: "warning",
              message: "请完整填写员工信息",
            });
            return;
          }
          insertEmployee(
            this.formData.employeeId,
            this.formData.employeeName,
            this.formData.store.storeId,
            this.formData.email,
            this.formData.position,
            this.formData.phone
          ).then(() => {
            if (this.value3.length != 0) {
              insertPreferenceHaveOne(
                this.formData.employeeId,
                this.value3.join(",")
              );
            }
            if (
              this.startTime != "" &&
              this.endTime != "" &&
              this.startTime1 != "" &&
              this.endTime1 != ""
            ) {
              this.fullTime =
                this.startTime +
                "-" +
                this.endTime +
                "," +
                this.startTime1 +
                "-" +
                this.endTime1;
              insertPreferenceHaveTwo(this.formData.employeeId, this.fullTime);
            } else if (this.startTime != "" && this.endTime != "") {
              this.fullTime = this.startTime + "-" + this.endTime;
              insertPreferenceHaveTwo(this.formData.employeeId, this.fullTime);
            } else if (this.startTime1 != "" && this.endTime1 != "") {
              this.fullTime = this.startTime1 + "-" + this.endTime1;
              insertPreferenceHaveTwo(this.formData.employeeId, this.fullTime);
            }
            if (this.formData.field108 != "" && this.formData.field109 != "") {
              this.a = JSON.stringify({
                day: parseInt(this.formData.field109),
                week: parseInt(this.formData.field108),
              });
              insertPreferenceHaveThree(this.formData.employeeId, this.a);
            } else if (
              this.formData.field108 != "" &&
              this.formData.field109 == ""
            ) {
              this.a = JSON.stringify({
                week: parseInt(this.formData.field108),
              });
              insertPreferenceHaveThree(this.formData.employeeId, this.a);
            } else if (
              this.formData.field108 == "" &&
              this.formData.field109 != ""
            ) {
              this.a = JSON.stringify({
                day: parseInt(this.formData.field109),
              });
              insertPreferenceHaveThree(this.formData.employeeId, this.a);
            }
          });
          ElMessage({
            type: "success",
            message: "新建员工成功",
          });
          this.$router.push({
            name: "employee",
          });
        }
      });
    },
    resetForm() {
      this.$router.push({
        name: "employee",
      });
    },
  },
};
</script>

<style scoped>
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
.state {
  margin-left: 25px;
}
.btns {
  margin-top: 20px;
  margin-left: 800px;
}
.el-button {
  margin-right: 10px;
}
.demo-range .el-date-editor {
  margin: 8px;
}

.demo-range .el-range-separator {
  box-sizing: content-box;
}
</style>