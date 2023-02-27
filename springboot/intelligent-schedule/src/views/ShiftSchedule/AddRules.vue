<template>
  <el-card>
    <el-form ref="addFormRef" :model="addForm" :rules="addFormRules">
      <h4>基础信息</h4>
      <el-row>
        <el-col :span="8">
          <el-form-item label="规则名称：" prop="ruleName">
            <el-input v-model="addForm.ruleName" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="适用范围：" prop="useRange1">
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
          <el-form-item label="适用范围：" prop="useRange2">
            <el-checkbox-group v-model="addForm.useRange2">
              <div>
                <el-checkbox label="分公司" />
                <el-checkbox label="小组" />
              </div>
              <div>
                <el-checkbox label="部门" />
                <el-checkbox label="个人" />
              </div>
            </el-checkbox-group>
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
      <h4>个性化规则</h4>
      <el-row>
        <el-col>
          <el-input-number
            v-model="num"
            :min="1"
            :max="10"
            controls-position="right"
            @change="handleChange"
            placeholder="请输入"
            class="first"
          />&nbsp;&nbsp;&nbsp;≤单月上班天数(天)≤&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <el-input-number
            v-model="num"
            :min="1"
            :max="10"
            controls-position="right"
            @change="handleChange"
            placeholder="请输入"
          />
          &nbsp;&nbsp;&nbsp;最大连续上班天数：
          <el-input-number
            v-model="num"
            :min="1"
            :max="10"
            controls-position="right"
            @change="handleChange"
            placeholder="请输入"
          />
        </el-col>
      </el-row>
      <br />
      <el-row>
        <el-col>
          <el-input-number
            v-model="num"
            :min="1"
            :max="10"
            controls-position="right"
            @change="handleChange"
            placeholder="请输入"
            class="first"
          />&nbsp;&nbsp;&nbsp;≤休息时长(天)≤&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <el-input-number
            v-model="num"
            :min="1"
            :max="10"
            controls-position="right"
            @change="handleChange"
            placeholder="请输入"
          />
          &nbsp;&nbsp;&nbsp;最早上班时间：
          <el-date-picker v-model="chosedDate" type="date" :size="size" />
        </el-col>
      </el-row>
      <br />
      <el-row>
        <el-col>
          <el-input-number
            v-model="num"
            :min="1"
            :max="10"
            controls-position="right"
            @change="handleChange"
            placeholder="请输入"
            class="first"
          />&nbsp;&nbsp;&nbsp;≤单月排班总时长(小时)≤&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <el-input-number
            v-model="num"
            :min="1"
            :max="10"
            controls-position="right"
            @change="handleChange"
            placeholder="请输入"
          />
          &nbsp;&nbsp;&nbsp;最晚上班时间：
          <el-date-picker v-model="chosedDate" type="date" :size="size" />
        </el-col>
      </el-row>
      <br />
      <el-row>
        <el-col>
          <el-input-number
            v-model="num"
            :min="1"
            :max="10"
            controls-position="right"
            @change="handleChange"
            placeholder="请输入"
            class="first"
          />&nbsp;&nbsp;&nbsp;≤单天累计工作时长(小时)≤&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <el-input-number
            v-model="num"
            :min="1"
            :max="10"
            controls-position="right"
            @change="handleChange"
            placeholder="请输入"
          />
          &nbsp;&nbsp;&nbsp;前5后3上班时长：
          <el-input-number
            v-model="num"
            :min="1"
            :max="10"
            controls-position="right"
            @change="handleChange"
            placeholder="请输入"
          />
        </el-col>
      </el-row>
      <br />
      <el-row class="two">
        <el-col :span="8">
          是否可上天地班：
          <el-radio v-model="radio" label="1">是</el-radio>
          <el-radio v-model="radio" label="2">否</el-radio>
        </el-col>
        <el-col :span="10">
          是否可上夜班：
          <el-radio v-model="radio" label="1">是</el-radio>
          <el-radio v-model="radio" label="2">否</el-radio>
        </el-col>
      </el-row>
      <br />
      <el-row class="three">
        <el-col :span="8">
          可上班日期：
          <el-radio v-model="radio" label="1">是</el-radio>
          <el-radio v-model="radio" label="2">否</el-radio>
        </el-col>
        <el-col :span="10">
          是否为固定夜班：
          <el-radio v-model="radio" label="1">是</el-radio>
          <el-radio v-model="radio" label="2">否</el-radio>
        </el-col>
      </el-row>
      <div class="btns">
        <el-button @click="personalizationRules">取消</el-button>
        <el-button type="primary">保存</el-button>
      </div>
    </el-form>
  </el-card>
</template>

<script>
export default {
  data() {
    return {
      radio: '1',
      chosedDate: "",
      value: [],
      addForm: {
        ruleName: "",
        useRange1: [
          {
            value: "上海",
            label: "上海分公司",
          },
          {
            value: "浙江",
            label: "浙江分公司",
          },
          {
            value: "江苏",
            label: "江苏分公司",
          },
        ],
        useRange2: ["分公司"],
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
        useRange2: [{ required: true }],
        state: [{ required: true }],
      },
    };
  },
  methods: {
    personalizationRules() {
      this.$router.push({
        name: "personalizationRules",
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
.first,
.two,
.three {
  margin-left: 20px;
}
.btns {
  margin-top: 20px;
  margin-left: 800px;
}
.el-button {
  margin-right: 10px;
}
</style>