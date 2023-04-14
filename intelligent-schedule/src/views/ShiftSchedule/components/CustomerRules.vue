<template>
  <div class="rules">
    <div class="title">
      <h2>客流规则</h2>
    </div>
    <div class="rulesList">
      <el-table :data="tableData" style="width: 100%" stripe>
        <el-table-column label="" prop="ruleTitle"> </el-table-column>
        <el-table-column label="" prop="ruleValue"> </el-table-column>
        <el-table-column label="">
          <template v-slot="scope">
            <el-link
              size="mini"
              type="primary"
              @click="handleEdit(scope.$index, scope.row)"
            >
              编辑
            </el-link>
            <el-link
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)"
              >删除</el-link
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 新建规则对话框 -->
    <el-dialog v-model="addDialogVisible" title="编辑规则" width="30%">
      <el-form :model="addForm">
        <el-form-item :label="rulenamee" label-width="60%"> </el-form-item>
        <el-form-item label="规则内容：" prop="rulevaluee">
          <el-input
            v-model="this.rulevaluee"
            placeholder="请输入规则内容"
            clearable
            :style="{ width: '100%' }"
          >
          </el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handelUpdate"> 确定 </el-button>
        </span>
      </template>
    </el-dialog>
        <!-- 提示框 -->
        <el-dialog v-model="NoDelete" title="无法删除" width="30%">
      <el-form :model="deleteForm">
        <el-form-item label="无法删除通用规则" label-width="66%" prop="nono">
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="NoDelete = false">确定</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 提示框 -->
    <el-dialog v-model="NoDelete1" title="无法删除" width="30%">
      <el-form :model="deleteForm1">
        <el-form-item
          label="该分店无独立规则，无法进行删除操作"
          label-width="90%"
          prop="nono"
        >
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="NoDelete1 = false">确定</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 提示框 -->
    <el-dialog v-model="Noemptyy" title="不能为空" width="30%">
      <el-form :model="Noempty">
        <el-form-item label="输入值不能为空" label-width="66%" prop="nonono">
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="Noemptyy = false">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {
  getCustomerRule,
  updateCustomer,
  getCustomerByStoreId,
  insertCustomer,
  getAllRule,
  updateCustomerByStoreId,
} from "@/api";

export default {
  inject: ["reload"],
  name: "CustomerShop",
  data() {
    return {
      rulenamee: "",
      rulevaluee: "",
      label111: -1,
      //修改通用规则则为0，用通用规则但是分店则为1
      label000: 0,
      lengthh: 0,

      tableData: [],

      tableDatas: {
        ruleTitle: "",
        ruleValue: "",
      },

      addDialogVisible: false,
      NoDelete: false,
      NoDelete1: false,
      Noemptyy: false,
      addForm: {
        ruleName: "",
        ruleContent: "",
      },
    };
  },

  mounted() {
    //获取通用规则
    this.label111 = sessionStorage.getItem("label111");
    //不是总门店
    if (this.label111 != -1) {
      getCustomerByStoreId(this.label111).then((datas) => {
        //没有自己的规则就用通用规则
        if (datas.data.length == 0) {
          getCustomerRule().then((datas) => {
            this.tableData = datas.data;
            let json = JSON.parse(this.tableData[0].ruleValue);
            this.tableData[0].ruleValue = json.minNeed;
            this.tableData[0].ruleTitle = "最少所需人数";
            const rules = {
              ruleTitle: "客流计算公式",
              ruleValue: json.formula,
            };
            this.tableData.unshift(rules);
            console.log(datas.data);
            this.label000 = 1;
          });
        } else if (
          datas.data[0].ruleValue == '{"formula": "", "minNeed": ""}'
        ) {
          getCustomerRule().then((datas) => {
            this.tableData = datas.data;
            let json = JSON.parse(this.tableData[0].ruleValue);
            this.tableData[0].ruleValue = json.minNeed;
            this.tableData[0].ruleTitle = "最少所需人数";
            const rules = {
              ruleTitle: "客流计算公式",
              ruleValue: json.formula,
            };
            this.tableData.unshift(rules);
            console.log(datas.data);
          });
        } else {
          this.tableData = datas.data;
          let json = JSON.parse(this.tableData[0].ruleValue);
          this.tableData[0].ruleValue = json.minNeed;
          this.tableData[0].ruleTitle = "最少所需人数";
          const rules = { ruleTitle: "客流计算公式", ruleValue: json.formula };
          this.tableData.unshift(rules);
          console.log(datas.data);
        }
      });
    } else if (this.label111 == -1) {
      getCustomerRule().then((datas) => {
        this.tableData = datas.data;
        let json = JSON.parse(this.tableData[0].ruleValue);
        this.tableData[0].ruleValue = json.minNeed;
        this.tableData[0].ruleTitle = "最少所需人数";
        const rules = { ruleTitle: "客流计算公式", ruleValue: json.formula };
        this.tableData.unshift(rules);
        console.log(datas.data);
      });
    }
  },

  methods: {
    handleEdit(index, row) {
      getAllRule().then((datas) => {
        // console.log(datas.data.length);
        console.log("label000:" + this.label000);
        console.log("label111:" + this.label111);
      });
      this.addDialogVisible = true;
      this.rulenamee = this.tableData[index].ruleTitle;
      // console.log(this.rulenamee);
      this.rulevaluee = this.tableData[index].ruleValue;
      // console.log(this.rulevaluee);
    },

    //删除规则
    async handleDelete(index, row) {
      this.rulenamee = this.tableData[index].ruleTitle;
      if (this.label111 != -1 && this.label000 == 0) {
        if (this.rulenamee == "客流计算公式") {
          this.sstr =
            '{"formula": "", "minNeed": "' + this.tableData[1].ruleValue + '"}';
          console.log(this.sstr);
        } else {
          this.sstr =
            '{"formula": "' + this.tableData[0].ruleValue + '", "minNeed": ""}';
          console.log(this.sstr);
        }
        console.log(this.rulevaluee);
        await updateCustomerByStoreId(this.label111, this.sstr);
        this.addDialogVisible = false;
        await getCustomerByStoreId(this.label111).then((datass) => {
          this.tableData = datass.data;
          let json = JSON.parse(this.tableData[0].ruleValue);
          this.tableData[0].ruleValue = json.minNeed;
          this.tableData[0].ruleTitle = "最少所需人数";
          const rules = {
            ruleTitle: "客流计算公式",
            ruleValue: json.formula,
          };
          this.tableData.unshift(rules);
          console.log(datass.data);
        });
      }else if (this.label111 == -1) {
        this.NoDelete = true;
      } else {
        this.NoDelete1 = true;
      }
    },


    //点击确认编辑button确认编辑规则
    async handelUpdate() {
      if (this.rulevaluee == "") {
        this.Noemptyy = true;
      } else {
      //是分店
      if (this.label111 != -1) {
        //分店无独立规则，插入一条新规则
        if (this.label000 == 1) {
          if (this.rulenamee == "客流计算公式") {
            this.sstr =
              '{"formula": "' +
              this.rulevaluee +
              '", "minNeed": "' +
              this.tableData[1].ruleValue +
              '"}';
            console.log(this.sstr);
          } else {
            this.sstr =
              '{"formula": "' +
              this.tableData[0].ruleValue +
              '", "minNeed": "' +
              this.rulevaluee +
              '"}';
            console.log(this.sstr);
          }
          getAllRule().then(async (datas) => {
            this.lengthh = datas.data.length + 1;
            this.lengthh = this.lengthh.toString();
            console.log(this.lengthh);
            console.log(this.label111);
            await insertCustomer(this.lengthh, this.label111, this.sstr);
            this.addDialogVisible = false;
            // this.sleep(10000);
            await getCustomerByStoreId(this.label111).then((datass) => {
              this.tableData = datass.data;
              let json = JSON.parse(this.tableData[0].ruleValue);
              console.log(
                "minNeed:" + json.minNeed + "formula:" + json.formula
              );
              this.tableData[0].ruleValue = json.minNeed;
              this.tableData[0].ruleTitle = "最少所需人数";
              const rules = {
                ruleTitle: "客流计算公式",
                ruleValue: json.formula,
              };
              this.tableData.unshift(rules);
              console.log(datass.data);
            });
            // this.reload();
          });
        }
        //分店有独立规则则进行编辑（update
        else {
          if (this.rulenamee == "客流计算公式") {
            this.sstr =
              '{"formula": "' +
              this.rulevaluee +
              '", "minNeed": "' +
              this.tableData[1].ruleValue +
              '"}';
            console.log(this.sstr);
          } else {
            this.sstr =
              '{"formula": "' +
              this.tableData[0].ruleValue +
              '", "minNeed": "' +
              this.rulevaluee +
              '"}';
            console.log(this.sstr);
          }
          console.log(this.rulevaluee);
          await updateCustomerByStoreId(this.label111, this.sstr);
          this.addDialogVisible = false;
          await getCustomerByStoreId(this.label111).then((datass) => {
            this.tableData = datass.data;
            let json = JSON.parse(this.tableData[0].ruleValue);
            this.tableData[0].ruleValue = json.minNeed;
            this.tableData[0].ruleTitle = "最少所需人数";
            const rules = {
              ruleTitle: "客流计算公式",
              ruleValue: json.formula,
            };
            this.tableData.unshift(rules);
            console.log(datass.data);
          });
          // this.reload();
        }
      }
      //不是分店
      else {
        if (this.rulenamee == "客流计算公式") {
          this.sstr =
            '{"formula": "' +
            this.rulevaluee +
            '", "minNeed": "' +
            this.tableData[1].ruleValue +
            '"}';
          console.log(this.sstr);
        } else {
          this.sstr =
            '{"formula": "' +
            this.tableData[0].ruleValue +
            '", "minNeed": "' +
            this.rulevaluee +
            '"}';
          console.log(this.sstr);
        }
        console.log(this.rulevaluee);
        await updateCustomer(this.sstr);
        this.addDialogVisible = false;
        await getCustomerRule().then((datas) => {
          this.tableData = datas.data;
          let json = JSON.parse(this.tableData[0].ruleValue);
          this.tableData[0].ruleValue = json.minNeed;
          this.tableData[0].ruleTitle = "最少所需人数";
          const rules = { ruleTitle: "客流计算公式", ruleValue: json.formula };
          this.tableData.unshift(rules);
          console.log(datas.data);
        });
        // this.reload();
      }
    }
    },

    getCustomerRules() {
      getCustomerRule().then((datas) => {
        this.tableData.ruleTitle = datas.data[0].ruleTitle;
        this.tableData.rulevaluee = datas.data[0].ruleValue;
        console.log(datas.data);
      });
    },
  },
};
</script>

<style lang="less" scoped>
.el-link {
  margin-left: 17px;
}
.rules {
  .title {
    display: flex;
    justify-content: center;
  }
  .rulesList {
    margin-top: 20px;
  }
}
</style>
