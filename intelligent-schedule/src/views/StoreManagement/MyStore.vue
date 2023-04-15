<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <el-card class="right-header">
          <el-form>
            <el-row :gutter="30">
              <el-col :span="6">
                <el-form-item label="门店名称：">
                  <el-input
                    v-model="inputStoreName"
                    placeholder="请输入门店名称"
                  ></el-input>
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
                  <el-button @click="getStore" type="primary">查询</el-button>
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
              <el-button plain type="primary" @click="addStore"
                >新建门店</el-button
              >
            </div>
          </div>
          <!-- 列表区域 -->
          <el-table :data="tableData" style="width: 100%" border stripe>
            <el-table-column
              label="序号"
              prop="storeId"
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
              prop="storeType"
              label="类型"
              width="120%"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="address"
              label="地址"
              width="200%"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="size"
              label="面积(m²)"
              width="150%"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="manger"
              label="管理员"
              width="100%"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="workers"
              label="人数"
              width="100%"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="state"
              label="状态"
              width="100%"
              align="center"
            >
              <template v-slot="scope">
                <el-tag type="success" v-if="scope.row.state === 1"
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
                  @click="handleEdit(scope.row)"
                  title="编辑门店内容"
                  >编辑</el-link
                >
                <el-link
                  type="primary"
                  :icon="Delete"
                  @click="handleDelete(scope.row)"
                  title="删除门店内容"
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
          <el-col :span="100">
            <el-form-item label="门店序号" prop="field100">
              <el-input
                v-model="formData.field100"
                disabled
                placeholder="请输入门店序号"
                clearable
                style="width: 450px"
              >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="100">
            <el-form-item label="门店名称" prop="field101">
              <el-input
                v-model="formData.field101"
                placeholder="请输入门店名称"
                clearable
                style="width: 450px"
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
                style="width: 400px"
              >
                <el-option
                  v-for="(item, storeId) in field102Options"
                  :key="storeId"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="100">
            <el-form-item label="门店地址" prop="field105">
              <el-input
                v-model="formData.field105"
                type="textarea"
                placeholder="请输入门店地址"
                :autosize="{ minRows: 2, maxRows: 2 }"
                style="width: 450px"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="100">
            <el-form-item label="门店面积" prop="field104">
              <el-input
                v-model="formData.field104"
                placeholder="请输入门店面积"
                clearable
                style="width: 450px"
              >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="100">
            <el-form-item label="门店人数" prop="field103">
              <el-input
                v-model="formData.field103"
                placeholder="请输入门店人数"
                clearable
                style="width: 450px"
              >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="100">
            <el-form-item label="管理员" prop="field106">
              <el-input
                v-model="formData.field106"
                placeholder="请输入门店管理员"
                clearable
                style="width: 450px"
              >
              </el-input>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="100">
            <el-form-item label="状态" prop="field108">
              <el-select
                v-model="formData.field108"
                placeholder="请选择状态"
                clearable
                style="width: 400px"
              >
                <el-option
                  v-for="(item, storeId) in field108Options"
                  :key="storeId"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col> -->
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
import { ElMessage, ElMessageBox } from "element-plus";

import {
  getAllStore,
  getStoreByStoreName,
  getStoreByStoreType,
  getStoreByStore,
  updateStoreStateOne,
  updateStoreStateZero,
  deleteStore,
  deleteRuleByStoreId,
  deleteSchedulingByStoreId,
  deleteEmployeeStateByStoreId,
  updateStore,
} from "@/api";
export default {
  inject: ["reload"],
  data() {
    return {
      inputStoreName: "",
      value: "",
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
      tableData: [],
      tableDatas: {
        storeId: "",
        storeName: "",
        storeType: "",
        address: "",
        size: "",
        manger: "",
        workers: "",
        state: "",
      },
      //编辑弹窗
      formData: {
        field100: "",
        field101: "",
        field102: "",
        field103: "",
        field104: "",
        field105: "",
        field106: "",
        // field108: "",
      },
      rules: {
        field100: [
          {
            required: true,
            message: "请输入门店编号",
            trigger: "blur",
          },
        ],
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
        field106: [
          {
            required: true,
            message: "请输入门店管理员",
            trigger: "blur",
          },
        ],
        // field108: [
        //   {
        //     required: true,
        //     message: "请选择状态",
        //     trigger: "change",
        //   },
        // ],
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
      dialogUpdateVisible: false,
    };
  },
  methods: {
    //跳转到添加门店页
    addStore() {
      this.$router.push({ name: "addStore" });
    },
    //点击编辑button编辑门店
    handleEdit(row) {
      this.dialogUpdateVisible = true;
      this.formData.field100 = row.storeId;
      this.formData.field101 = row.storeName;
      this.formData.field102 = row.storeType;
      this.formData.field105 = row.address;
      this.formData.field104 = row.size;
      this.formData.field106 = row.manger;
      this.formData.field103 = row.workers;
    },
    //点击确认编辑button确认编辑门店
    async handelUpdate() {
      await updateStore(
        this.formData.field100,
        this.formData.field101,
        this.formData.field102,
        this.formData.field105,
        this.formData.field104,
        this.formData.field106,
        this.formData.field103
      );
      this.dialogUpdateVisible = false;
      await this.reload();
      ElMessage({
        type: "success",
        message: "编辑门店成功",
      });
    },
    //点击删除button删除门店
    handleDelete(row) {
      console.log(row.storeId);
      ElMessageBox.confirm("该门店将永久被删除，确定吗?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(async () => {
        deleteEmployeeStateByStoreId(row.storeId);
        deleteSchedulingByStoreId(row.storeId);
        deleteRuleByStoreId(row.storeId);
        deleteStore(row.storeId);
        await this.reload();
        ElMessage({
          type: "success",
          message: "删除门店成功",
        });
      });
    },
    //点击启用button启用门店
    handleEnable(row) {
      console.log(row.storeId);
      ElMessageBox.confirm("该门店将被启用，确定吗?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(async () => {
        updateStoreStateZero(row.storeId);
        await this.reload();
        ElMessage({
          type: "success",
          message: "启用门店成功",
        });
      });
    },
    //点击停用button停用门店
    handleDeactivate(row) {
      console.log(row.storeId);
      ElMessageBox.confirm("该门店将被停用，确定吗?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(async () => {
        updateStoreStateOne(row.storeId);
        await this.reload();
        ElMessage({
          type: "success",
          message: "停用门店成功",
        });
      });
    },

    // 查询门店
    getStore() {
      if (this.value == "全部") {
        if (this.inputStoreName == "") {
          getAllStore().then((datas) => {
            this.tableData = datas.data;
            console.log(datas.data);
          });
        } else if (this.inputStoreName != "") {
          getStoreByStoreName(this.inputStoreName).then((datas) => {
            this.tableData = datas.data;
            console.log(datas.data);
          });
        }
      } else {
        if (this.inputStoreName == "" && this.value == "") {
          getAllStore().then((datas) => {
            this.tableData = datas.data;
            console.log(datas.data);
          });
        } else if (this.value == "" && this.inputStoreName != "") {
          getStoreByStoreName(this.inputStoreName).then((datas) => {
            this.tableData = datas.data;
            console.log(datas.data);
          });
        } else if (this.inputStoreName == "" && this.value != "") {
          getStoreByStoreType(this.value).then((datas) => {
            this.tableData = datas.data;
            console.log(datas.data);
          });
        } else {
          getStoreByStore(this.inputStoreName, this.value).then((datas) => {
            this.tableData = datas.data;
            console.log(this.value);
            console.log(datas.data);
          });
        }
      }
    },

    // 重置门店
    resetTextAndSelect() {
      this.value = "";
      this.inputStoreName = "";
    },
  },

  mounted() {
    // 获取所有门店信息
    getAllStore().then((datas) => {
      this.tableData = datas.data;
      console.log(datas.data);
    });
  },
};
</script>

<style scoped>
.el-main {
  padding: 0;
  /*margin-left: 20px;*/
  margin-top: 20px;
  width: 100%;
}
.el-header {
  padding: 0;
  width: 100%;
}

.right-header {
  /*margin-left: 20px;*/
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