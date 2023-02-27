<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="180px">
        <el-card class="left-nav">
          <el-tree
            :data="storeData"
            :props="defaultProps"
            @node-click="handleNodeClick"
          ></el-tree>
        </el-card>
      </el-aside>
      <el-container>
        <el-header>
          <el-card class="right-header">
            <el-form>
              <el-row :gutter="30">
                <el-col :span="5">
                  <el-form-item label="姓名：">
                    <el-input placeholder="请输入姓名"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item label="岗位：">
                    <el-cascader
                      v-model="value1"
                      :options="options1"
                      :props="{ expandTrigger: 'hover' }"
                      @change="handleChange1"
                    ></el-cascader>
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item label="类型：">
                    <el-cascader
                      v-model="value2"
                      :options="options2"
                      :props="{ expandTrigger: 'hover' }"
                      @change="handleChange2"
                    ></el-cascader>
                  </el-form-item>
                </el-col>
                <el-col :span="9">
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
                <el-button plain type="primary" @click="addEmployee"
                  >新建员工</el-button
                >
              </div>
              <div class="right-btn">
                <el-button plain type="primary">导入</el-button>
                <el-button plain type="primary">导出</el-button>
              </div>
            </div>
            <!-- 列表区域 -->
            <el-table :data="tableData" style="width: 100%" border stripe>
              <el-table-column label="序号" type="index" width="60px" />
              <el-table-column prop="ruleName" label="姓名" width="100" />
              <el-table-column prop="useRange" label="门店" width="150" />
              <el-table-column prop="type" label="工号" />
              <el-table-column prop="type" label="岗位" />
              <el-table-column prop="state" label="状态">
                <template v-slot="scope">
                  <el-tag type="success" v-if="scope.row.state === '已启用'"
                    >已启用</el-tag
                  >
                  <el-tag type="danger" v-else>已停用</el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="200px">
                <template v-slot="scope">
                  <el-link
                    type="primary"
                    :icon="Edit"
                    @click="handleEdit"
                    title="编辑员工内容"
                    >编辑</el-link
                  >
                  <el-link
                    type="primary"
                    :icon="Delete"
                    @click="handleDelete"
                    title="删除员工内容"
                    >删除</el-link
                  >
                  <el-link
                    type="primary"
                    :icon="Enable"
                    @click="handleEnable"
                    title="启用门店内容"
                    v-if="scope.row.state === '已停用'"
                    >启用</el-link
                  >
                  <el-link
                    type="primary"
                    :icon="Deactivate"
                    @click="handleDeactivate"
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
        <template v-slot:footer>
          <el-button @click="dialogUpdateVisible = false">取消</el-button>
          <el-button type="primary" @click="handelUpdate">确定</el-button>
        </template>
      </el-dialog>
    </el-container>
  </div>
</template>


<script>
import { ElMessageBox } from "element-plus";
export default {
  data() {
    return {
      // 左侧导航数据
      storeData: [
        {
          label: "零售门店",
          children: [
            {
              label: "集团公司",
              children: [
                {
                  label: "浙江分公司",
                },
                {
                  label: "上海分公司",
                },
                {
                  label: "江苏分公司",
                },
              ],
            },
          ],
        },
      ],
      defaultProps: {
        children: "children",
        label: "label",
      },
      //列表数据
      tableData: [
        // {
        //   ruleName: "规则1",
        //   useRange: "浙江分公司",
        //   type: "分公司",
        //   state: "已启用",
        // },
        // {
        //   ruleName: "规则1",
        //   useRange: "浙江分公司",
        //   type: "分公司",
        //   state: "已停用",
        // },
        // {
        //   ruleName: "规则1",
        //   useRange: "浙江分公司",
        //   type: "分公司",
        //   state: "已停用",
        // },
        // {
        //   ruleName: "规则1",
        //   useRange: "浙江分公司",
        //   type: "分公司",
        //   state: "已停用",
        // },
        // {
        //   ruleName: "规则1",
        //   useRange: "浙江分公司",
        //   type: "分公司",
        //   state: "已启用",
        // },
        // {
        //   ruleName: "规则1",
        //   useRange: "浙江分公司",
        //   type: "分公司",
        //   state: "已启用",
        // },
        // {
        //   ruleName: "规则1",
        //   useRange: "浙江分公司",
        //   type: "分公司",
        //   state: "已启用",
        // },
        // {
        //   ruleName: "规则1",
        //   useRange: "浙江分公司",
        //   type: "分公司",
        //   state: "已停用",
        // },
        // {
        //   ruleName: "规则1",
        //   useRange: "浙江分公司",
        //   type: "分公司",
        //   state: "已停用",
        // },
        // {
        //   ruleName: "规则1",
        //   useRange: "浙江分公司",
        //   type: "分公司",
        //   state: "已停用",
        // },
        // {
        //   ruleName: "规则1",
        //   useRange: "浙江分公司",
        //   type: "分公司",
        //   state: "已启用",
        // },
      ],
      //编辑弹窗
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
      dialogUpdateVisible: false,
    };
  },
  methods: {
    //跳转到添加员工页
    addEmployee() {
      this.$router.push({
        name: "addEmployee",
      });
    },
    //点击编辑button编辑员工
    handleEdit() {
      this.dialogUpdateVisible = true;
    },
    //点击删除button删除员工
    handleDelete() {
      ElMessageBox.confirm("该员工将永久被删除，确定吗?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      });
    },
    //点击启用button启用员工
    handleEnable() {
      ElMessageBox.confirm("该员工将被启用，确定吗?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      });
    },
    //点击停用button停用员工
    handleDeactivate() {
      ElMessageBox.confirm("该员工将被停用，确定吗?", "Warning", {
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