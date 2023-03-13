<template>
  <el-card>
    <el-form ref="addFormRef" :model="addForm" :rules="addFormRules">
      <h4>基础信息</h4>
      <el-row>
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
      </el-row>
      <div class="btns">
        <el-button @click="myStore">取消</el-button>
        <el-button type="primary" @click="myStore">保存</el-button>
      </div>
    </el-form>
  </el-card>
</template>

<script>
export default {
  data() {
    return {
      radio: "1",
      chosedDate: "",
      value: [],
      addForm: {
        ruleName: "",
        useRange1: [
          {
            value: "总公司",
            label: "总公司",
          },
          {
            value: "分公司",
            label: "分公司",
          },
          {
            value: "部门",
            label: "部门",
          },
        ],
        peopleNum: "",
        storeArea: "",
        storeAddress: "",
        state: [
          {
            value: "1",
            label: "已启用",
          },
          {
            value: "0",
            label: "已停用",
          },
        ],
      },
      addFormRules: {
        ruleName: [
          { required: true, message: "请输入规则名称", trigger: "blur" },
        ],
        useRange1: [{ required: true }],
        peopleNum: [
          { required: true, message: "请输入门店人数", trigger: "blur" },
        ],
        storeArea: [
          { required: true, message: "请输入门店面积", trigger: "blur" },
        ],
        storeAddress: [
          { required: true, message: "请输入门店地址", trigger: "blur" },
        ],
        state: [{ required: true }],
      },
    };
  },
  methods: {
    myStore() {
      this.$router.push({
        name: "myStore",
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