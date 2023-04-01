<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <el-card class="right-header">
          <el-form>
            <el-row :gutter="30">
              <el-col :span="6">
                <el-form-item label="日程名称：">
                  <el-input placeholder="请输入日程名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="类型：">
                  <el-cascader
                    v-model="value"
                    :options="useRange"
                    :props="{ expandTrigger: 'hover' }"
                    @change="handleChange"
                  ></el-cascader>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item class="form-btn">
                  <el-button type="primary">查询</el-button>
                  <el-button>重置</el-button>
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
              <el-button plain type="primary" @click="addShiftManagement"
                >新建日程</el-button
              >
            </div>
          </div>
          <!-- 列表区域 -->
          <el-table :data="tableData" style="width: 100%" border stripe>
            <el-table-column
              label="序号"
              type="index"
              width="100%"
              align="center"
            />
            <el-table-column
              prop="storeName"
              label="日程名称"
              width="200%"
              align="center"
            />
            <el-table-column
              prop="type"
              label="类型"
              width="200%"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="zip"
              label="修改时间"
              width="250%"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="createTime"
              label="创建时间"
              width="250%"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="createTime"
              label="创建人"
              width="250%"
              align="center"
            ></el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="220%"
              align="center"
            >
              <template v-slot="scope">
                <el-link
                  type="primary"
                  :icon="Edit"
                  @click="handleEdit"
                  title="编辑日程内容"
                  >编辑</el-link
                >
                <el-link
                  type="primary"
                  :icon="Delete"
                  @click="handleDelete"
                  title="删除日程内容"
                  >删除</el-link
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
      <template v-slot:footer>
        <el-button @click="dialogUpdateVisible = false">取消</el-button>
        <el-button type="primary" @click="handelUpdate">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>


<script>
import { ElMessageBox } from "element-plus";
export default {
  // //编辑弹窗
  // inheritAttrs: false,
  // components: {},
  // props: [],
  data() {
    return {
      //类型下拉框
      useRange: [
        {
          value: "全部",
          label: "全部",
        },
      ],
      //列表数据
      tableData: [
        // {
        //   storeName: "浙江分公司",
        //   useRange: "浙江分公司",
        //   type: "分公司",
        //   state: "已启用",
        //   creator: "User1",
        // },
      ],
      //编辑弹窗
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
      dialogUpdateVisible: false,
    };
  },
  methods: {
    //跳转到添加日程页
    addShiftManagement() {
      this.$router.push({ name: "addShiftManagement" });
    },
    //点击编辑button编辑日程
    handleEdit() {
      this.dialogUpdateVisible = true;
    },
    handelUpdate() {},
    //点击删除button删除日程
    handleDelete() {
      ElMessageBox.confirm("该日程将永久被删除，确定吗?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      });
    },
    //点击启用button启用日程
    handleEnable() {
      ElMessageBox.confirm("该日程将被启用，确定吗?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      });
    },
    //点击停用button停用日程
    handleDeactivate() {
      ElMessageBox.confirm("该日程将被停用，确定吗?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // let state = '已停用';
          ElMessage({
            type: "success",
            message: "停用成功",
          });
        })
        .catch(() => {
          ElMessage({
            type: "info",
            message: "取消停用",
          });
        });
    },
  },
};
</script>
<style scoped>
.el-main {
  padding: 0;
  margin-left: 20px;
  margin-top: 20px;
  width: 97%;
}
.el-header {
  padding: 0;
  width: 97%;
}

.right-header {
  margin-left: 20px;
  width: 100%;
  justify-content: space-around;
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
