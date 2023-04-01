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
          <el-col :span="16">
            <el-form-item label="日程名称：" prop="field101">
              <el-input
                v-model="formData.field101"
                placeholder="请输入日程名称："
                clearable
                :style="{ width: '100%' }"
              >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="类型：" prop="field102">
              <el-select
                v-model="formData.field102"
                placeholder="请选择类型："
                clearable
                :style="{ width: '100%' }"
              >
                <el-option
                  v-for="(item, index) in field102Options"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="描述：" prop="field103">
              <el-input
                v-model="formData.field103"
                type="textarea"
                placeholder="请输入描述："
                :autosize="{ minRows: 4, maxRows: 4 }"
                :style="{ width: '100%' }"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="21">
            <el-form-item label="时间范围：" prop="field105">
              <el-time-picker
                v-model="formData.field105"
                is-range
                format="HH:mm:ss"
                value-format="HH:mm:ss"
                :style="{ width: '100%' }"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                range-separator="至"
                clearable
              ></el-time-picker>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div class="btns">
        <el-button @click="shiftManagement">取消</el-button>
        <el-button type="primary">保存</el-button>
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
      radio: "1",
      chosedDate: "",
      value: [],
      formData: {
        field101: undefined,
        field102: undefined,
        field103: undefined,
        field105: ["19:50:02", "20:50:02"],
      },
      rules: {
        field101: [
          {
            required: true,
            message: "请输入日程名称：",
            trigger: "blur",
          },
        ],
        field102: [
          {
            required: true,
            message: "请选择类型：",
            trigger: "change",
          },
        ],
        field103: [
          {
            required: true,
            message: "请输入描述：",
            trigger: "blur",
          },
        ],
        field105: [
          {
            required: true,
            type: "array",
            message: "请至少选择一个field105",
            trigger: "change",
          },
        ],
      },
      field102Options: [
        {
          label: "选项一",
          value: 1,
        },
        {
          label: "选项二",
          value: 2,
        },
      ],
    };
  },
  methods: {
    shiftManagement() {
      this.$router.push({
        name: "shiftManagement",
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
