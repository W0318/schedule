<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <el-card class="right-header">
          <el-form>
            <el-row :gutter="30">
              <el-col :span="6">
                <el-form-item label="门店名称：">
                  <el-input placeholder="请输入门店名称"></el-input>
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
              <el-button plain type="primary" @click="addStore"
                >新建门店</el-button
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
              label="门店名称"
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
              prop="city"
              label="人数"
              width="100%"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="address"
              label="面积"
              width="150%"
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
              prop="state"
              label="状态"
              width="100%"
              align="center"
            >
              <template v-slot="scope">
                <el-tag type="success" v-if="scope.row.state === '已启用'"
                  >已启用</el-tag
                >
                <el-tag type="danger" v-else>已停用</el-tag>
              </template>
            </el-table-column>
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
                  title="编辑门店内容"
                  >编辑</el-link
                >
                <el-link
                  type="primary"
                  :icon="Delete"
                  @click="handleDelete"
                  title="删除门店内容"
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
          <el-col :span="100">
            <el-form-item label="门店名称" prop="field101">
              <el-input
                v-model="formData.field101"
                placeholder="请输入门店名称"
                clearable
                style="width: 340px"
              >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="100">
            <el-form-item label="门店类型" prop="field102">
              <el-select
                v-model="formData.field102"
                placeholder="请选择门店类型"
                clearable
                style="width: 300px"
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
          <el-col :span="100">
            <el-form-item label="门店人数" prop="field103">
              <el-input
                v-model="formData.field103"
                placeholder="请输入门店人数"
                clearable
                style="width: 340px"
              >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="100">
            <el-form-item label="门店面积" prop="field104">
              <el-input
                v-model="formData.field104"
                placeholder="请输入门店面积"
                clearable
                style="width: 340px"
              >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="100">
            <el-form-item label="门店地址" prop="field105">
              <el-input
                v-model="formData.field105"
                type="textarea"
                placeholder="请输入门店地址"
                :autosize="{ minRows: 4, maxRows: 4 }"
                style="width: 340px"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="100">
            <el-form-item label="状态" prop="field108">
              <el-select
                v-model="formData.field108"
                placeholder="请选择状态"
                clearable
                style="width: 300px"
              >
                <el-option
                  v-for="(item, index) in field108Options"
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
        <el-button @click="dialogUpdateVisible=false">取消</el-button>
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
      //列表数据
      tableData: [
        {
          storeName: "浙江分公司",
          useRange: "浙江分公司",
          type: "分公司",
          state: "已启用",
          creator: "User1",
        },
        {
          storeName: "上海分公司",
          useRange: "浙江分公司",
          type: "分公司",
          state: "已启用",
          creator: "User1",
        },
        {
          storeName: "江苏分公司",
          useRange: "浙江分公司",
          type: "分公司",
          state: "已启用",
          creator: "User1",
        },
      ],
      //编辑弹窗
      formData: {
        field101: undefined,
        field102: "",
        field103: undefined,
        field104: undefined,
        field105: undefined,
        field108: undefined,
      },
      rules: {
        field101: [
          {
            required: true,
            message: "请输入门店名称",
            trigger: "blur",
          },
        ],
        field102: [
          {
            required: true,
            message: "请选择门店类型",
            trigger: "change",
          },
        ],
        field103: [
          {
            required: true,
            message: "请输入门店人数",
            trigger: "blur",
          },
        ],
        field104: [
          {
            required: true,
            message: "请输入门店面积",
            trigger: "blur",
          },
        ],
        field105: [
          {
            required: true,
            message: "请输入门店地址",
            trigger: "blur",
          },
        ],
        field108: [
          {
            required: true,
            message: "请选择状态",
            trigger: "change",
          },
        ],
      },
      field102Options: [
        {
          label: "总公司",
          value: "总公司",
        },
        {
          label: "分公司",
          value: "分公司",
        },
        {
          label: "部门",
          value: "部门",
        },
      ],
      field108Options: [
        {
          label: "已启用",
          value: "已启用",
        },
        {
          label: "已停用",
          value: "已停用",
        },
      ],
      dialogUpdateVisible: false,
    };
  },
  methods: {
    // addStore() {
    //   this.$router.push({
    //     name: "addStore",
    //   });
    // },
    // addStore(event, number) {
    //   console.log(event, number);
    //   alert("同学你好！！");
    // },
    //跳转到添加门店页
    addStore() {
      this.$router.push({ name: "addStore" });
    },
    //点击编辑button编辑门店
    handleEdit() {
      this.dialogUpdateVisible = true;
    },
    handelUpdate() {

    },
    //点击删除button删除门店
    handleDelete() {
      ElMessageBox.confirm("该门店将永久被删除，确定吗?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      });
    },
    //点击启用button启用门店
    handleEnable() {
      ElMessageBox.confirm("该门店将被启用，确定吗?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      });
    },
    //点击停用button停用门店
    handleDeactivate() {
      ElMessageBox.confirm("该门店将被停用，确定吗?", "Warning", {
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