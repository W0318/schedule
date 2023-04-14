<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="180px">
        <el-card class="left-nav">
          <el-tree
            :data="storeData"
            :props="defaultProps"
            default-expand-all
            highlight-current
            node-key="nodeId"
            @node-click="handleNodeClick"
          ></el-tree>
        </el-card>
      </el-aside>
      <el-container>
        <el-header>
          <el-card class="right-header">
            <!-- tab选择 -->
            <el-tabs v-model="activeName" @tab-click="handleClick">
              <el-tab-pane label="开店规则" name="first">
                <open-rules />
              </el-tab-pane>
              <el-tab-pane label="关店规则" name="second">
                <close-rules />
              </el-tab-pane>
              <el-tab-pane label="客流规则" name="third">
                <customer-rules />
              </el-tab-pane>
              <el-tab-pane label="班次规则" name="fourth">
                <classes-rules />
              </el-tab-pane>
            </el-tabs>
          </el-card>
        </el-header>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { ref } from "vue";
import { ElMessageBox } from "element-plus";
import OpenRules from "./components/OpenShopRules.vue";
import CloseRules from "./components/CloseShopRules.vue";
import CustomerRules from "./components/CustomerRules.vue";
import ClassesRules from "./components/ClassesRules.vue";
import { getStoreName, getAllStore } from "@/api";
import OpenShopRules from "./components/OpenShopRules.vue";

export default {
  inject: ["reload"],
  data() {
    return {
      label111: -1,
      lengthh: 0,
      index: 0,
      selectLabel: "",

      // 左侧导航数据
      data: "",
      storeData: [],
      defaultProps: {
        children: "children",
        label: "label",
      },

      activeName: "first",
    };
  },
  components: {
    OpenRules,
    CloseRules,
    CustomerRules,
    ClassesRules,
  },
  methods: {
    // 切换tree上的节点点击事件
    handleNodeClick(data) {
      this.$nextTick(() => {
        // 获取所有门店信息
        getAllStore().then((datas) => {
          this.lengthh = datas.data.length;
          console.log("lengthh:" + this.lengthh);
          for (let i = this.lengthh - 1; i >= 0; i--) {
            console.log("id:" + datas.data[i].storeId);
            // console.log(data.label);
            this.selectLabel = datas.data[i].storeName;
            let label = data.label;
            console.log("label:" + label);
            console.log("selectlabel:" + this.selectLabel);
            if (label == this.selectLabel) {
              console.log("当前公司：" + label);
              this.label111 = datas.data[i].storeId;
              this.reload();
            }
          }
          if (this.label111 == -1) {
            this.reload();
          }
          console.log(this.label111);
          sessionStorage.setItem("label111", this.label111);
          console.log("存的数据" + sessionStorage.getItem("label111"));
        });
      });
    },

    // handleNodeClick(data) {
    //   this.$nextTick(() => {
    //     // this.$refs.tree.setCurrentKey(data.nodeId);
    //     // 获取所有门店信息
    //     getAllStore().then((datas) => {
    //       // this.tableData = datas.data.length;
    //       console.log("length:" + datas.data.length);
    //     });
    //     console.log(data.label);
    //     let label = data.label;
    //     if (label == "浙江总公司") {
    //       this.label111 = 1;
    //       // this.$router.go(0);
    //     } else if (label == "上海分公司") {
    //       this.label111 = 2;
    //       this.$router.go(0);
    //     } else if (label == "江苏分公司") {
    //       this.label111 = 3;
    //       this.$router.go(0);
    //     } else {
    //       this.label111 = -1;
    //       this.$router.go(0);
    //     }
    //     sessionStorage.setItem("label111", this.label111);
    //     console.log("存的数据" + sessionStorage.getItem("label111"));
    //     // getEmployeeByStoreId(data.label).then((datas) => {
    //     //   this.tableData = datas.data;
    //     //   // console.log(datas.data);
    //     // });
    //   });
    // },

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

  mounted() {
    getStoreName().then((datas) => {
      this.storeData = [
        {
          label: "全国零售门店",
          children: [...datas.data],
        },
      ];
      console.log(this.storeData);
    });
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
  height: 600px;
  padding: 0;
}
.el-tree{
  font-size: 17px;
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
