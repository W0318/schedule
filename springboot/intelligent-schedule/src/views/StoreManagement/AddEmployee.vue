<template>
  <el-card>
    <el-form ref="addFormRef" :model="addForm" :rules="addFormRules">
      <h4>基础信息</h4>
      <!-- <el-row>
        <el-col :span="8">
          <el-form-item label="门店名称：" prop="ruleName">
            <el-input v-model="addForm.ruleName" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="门店类型：" prop="useRange1">
            <el-cascader
              v-model="value"
              :options="addForm.useRange1"
              :props="{ expandTrigger: 'hover' }"
              @change="handleChange"
              collapse-tags
              clearable
              multiple
            ></el-cascader>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="门店人数：" prop="peopleNum">
            <el-input v-model="addForm.peopleNum" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="门店面积：" prop="storeArea">
            <el-input v-model="addForm.storeArea" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="门店地址：" prop="storeAddress">
            <el-input v-model="addForm.storeAddress" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row class="state">
        <el-col :span="8">
          <el-form-item label="状态：" prop="state">
            <el-cascader
              v-model="value"
              :options="addForm.state"
              :props="{ expandTrigger: 'hover' }"
              @change="handleChange"
              collapse-tags
              clearable
              multiple
            ></el-cascader>
          </el-form-item>
        </el-col>
      </el-row> -->
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
              <el-form-item label="员工姓名：" prop="field101">
                <el-input
                  v-model="formData.field101"
                  placeholder="请输入员工姓名"
                  clearable
                  :style="{ width: '100%' }"
                >
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="员工编号：" prop="field102">
                <el-input
                  v-model="formData.field102"
                  placeholder="请输入员工编号"
                  clearable
                  :style="{ width: '100%' }"
                >
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row class="two">
            <el-col :span="12">
              <el-form-item label="岗位：" prop="field103">
                <el-select
                  v-model="formData.field103"
                  placeholder="请输入岗位"
                  clearable
                  :style="{ width: '100%' }"
                >
                  <el-option
                    v-for="(item, index) in field103Options"
                    :key="index"
                    :label="item.label"
                    :value="item.value"
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
              <el-form-item label="所属门店：" prop="field106">
                <el-select
                  v-model="formData.field106"
                  placeholder="请选择所属门店"
                  clearable
                  :style="{ width: '100%' }"
                >
                  <el-option
                    v-for="(item, index) in field106Options"
                    :key="index"
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
          <el-col :span="19">
            <el-form-item label="描述：" prop="field108">
              <el-input
                v-model="formData.field108"
                type="textarea"
                placeholder="请输入描述"
                :autosize="{ minRows: 4, maxRows: 4 }"
                :style="{ width: '100%' }"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态：" prop="field109">
              <el-select
                v-model="formData.field109"
                placeholder="请选择状态"
                clearable
                :style="{ width: '100%' }"
              >
                <el-option
                  v-for="(item, index) in field109Options"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
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
export default {
  components: {},
  props: [],
  data() {
    return {
      formData: {
        field101: undefined,
        field102: undefined,
        field103: undefined,
        field105: undefined,
        field106: undefined,
        field107: undefined,
        field108: undefined,
        field109: undefined,
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
        field108: [
          {
            required: true,
            message: "请输入描述",
            trigger: "blur",
          },
        ],
        field109: [
          {
            required: true,
            message: "请选择状态",
            trigger: "change",
          },
        ],
      },
      field103Options: [
        {
          label: "总经理",
          value: 1,
        },
        {
          label: "销售经理",
          value: 2,
        },
        {
          label: "销售员",
          value: 3,
        },
        {
          label: "清洁工",
          value: 4,
        },
      ],
      field106Options: [
        {
          label: "浙江分公司",
          value: 1,
        },
        {
          label: "上海分公司",
          value: 2,
        },
        {
          label: "江苏分公司",
          value: "",
        },
      ],
      field109Options: [
        {
          label: "已启用",
          value: 1,
        },
        {
          label: "已停用",
          value: 2,
        },
      ],
    };
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    submitForm() {
      this.$refs["elForm"].validate((valid) => {
        if (!valid) return;
        // TODO 提交表单
      });
    },
    resetForm() {
      // this.$refs["elForm"].resetFields();
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
</style>