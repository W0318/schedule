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
                <el-col :span="6">
                  <el-form-item label="规则名称：">
                    <el-input placeholder="请输入规则名称"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="适用范围：">
                    <el-cascader
                      v-model="value"
                      :options="options"
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
                <el-button plain type="primary" @click="addRules"
                  >新建规则</el-button
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
              <el-table-column prop="ruleName" label="规则名称" width="180" />
              <el-table-column prop="useRange" label="适用范围" width="100" />
              <el-table-column prop="type" label="类型" />
              <el-table-column prop="state" label="状态">
                <template v-slot="scope">
                  <el-tag type="success" v-if="scope.row.state === '已启用'"
                    >已启用</el-tag
                  >
                  <el-tag type="danger" v-else>已停用</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="creator" label="创建人" />
              <el-table-column label="操作">
                <template v-slot="scope">
                  <el-link type="primary">编辑</el-link>
                  <el-link
                    type="primary"
                    :icon="Delete"
                    @click="handleDelete"
                    title="删除规则内容"
                    >删除</el-link
                  >
                  <el-link
                    type="primary"
                    :icon="Enable"
                    @click="handleEnable"
                    title="启用规则内容"
                    v-if="scope.row.state === '已停用'"
                    >启用</el-link
                  >
                  <el-link
                    type="primary"
                    :icon="Deactivate"
                    @click="handleDeactivate"
                    title="停用规则内容"
                    v-else
                    >停用</el-link
                  >
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-main>
      </el-container>
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
        {
          ruleName: "规则1",
          useRange: "浙江分公司",
          type: "分公司",
          state: "已启用",
          creator: "User1",
        },
        {
          ruleName: "规则1",
          useRange: "浙江分公司",
          type: "分公司",
          state: "已停用",
          creator: "User1",
        },
        {
          ruleName: "规则1",
          useRange: "浙江分公司",
          type: "分公司",
          state: "已停用",
          creator: "User1",
        },
        {
          ruleName: "规则1",
          useRange: "浙江分公司",
          type: "分公司",
          state: "已停用",
          creator: "User1",
        },
        {
          ruleName: "规则1",
          useRange: "浙江分公司",
          type: "分公司",
          state: "已启用",
          creator: "User1",
        },
        {
          ruleName: "规则1",
          useRange: "浙江分公司",
          type: "分公司",
          state: "已启用",
          creator: "User1",
        },
        {
          ruleName: "规则1",
          useRange: "浙江分公司",
          type: "分公司",
          state: "已启用",
          creator: "User1",
        },
        {
          ruleName: "规则1",
          useRange: "浙江分公司",
          type: "分公司",
          state: "已停用",
          creator: "User1",
        },
        {
          ruleName: "规则1",
          useRange: "浙江分公司",
          type: "分公司",
          state: "已停用",
          creator: "User1",
        },
        {
          ruleName: "规则1",
          useRange: "浙江分公司",
          type: "分公司",
          state: "已停用",
          creator: "User1",
        },
        {
          ruleName: "规则1",
          useRange: "浙江分公司",
          type: "分公司",
          state: "已启用",
          creator: "User1",
        },
      ],
    };
  },
  methods: {
    addRules() {
      this.$router.push({
        name: "addRules",
      });
    },
    //点击删除button删除规则
    handleDelete() {
      ElMessageBox.confirm("该规则将永久被删除，确定吗?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      });
    },
    //点击启用button启用规则
    handleEnable() {
      ElMessageBox.confirm("该规则将被启用，确定吗?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      });
    },
    //点击停用button停用规则
    handleDeactivate() {
      ElMessageBox.confirm("该规则将被停用，确定吗?", "Warning", {
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