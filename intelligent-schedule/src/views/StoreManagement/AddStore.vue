<template>
  <el-card>
    <el-form ref="addFormRef" :model="addForm" :rules="addFormRules">
      <h4>基础信息</h4>
      <el-row>
        <el-col :span="13">
          <el-form-item label="门店序号：" prop="storeId">
            <el-input v-model="addForm.storeId" placeholder="请输入门店序号" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="13">
          <el-form-item label="门店名称：" prop="storeName">
            <el-input
              v-model="addForm.storeName"
              placeholder="请输入门店名称"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="门店类型：" prop="storeType">
            <el-select
              v-model="addForm.storeType"
              placeholder="请选择门店类型"
              clearable
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
      </el-row>
      <el-row>
        <el-col :span="13">
          <el-form-item label="门店地址：" prop="address">
            <el-input
              v-model="addForm.address"
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 4 }"
              placeholder="请输入门店地址"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="13">
          <el-form-item label="门店面积：" prop="size">
            <el-input v-model="addForm.size" placeholder="请输入门店面积" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="13">
          <el-form-item label="门店人数：" prop="workers">
            <el-input v-model="addForm.workers" placeholder="请输入门店人数" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="13">
          <el-form-item label="门店管理员：" prop="manger">
            <el-input v-model="addForm.manger" placeholder="请输入管理员" />
          </el-form-item>
        </el-col>
      </el-row>
      <div class="btns">
        <el-button @click="cancelMyStore">取消</el-button>
        <el-button type="primary" @click="myStore">保存</el-button>
      </div>
    </el-form>
  </el-card>
</template>

<script>
import { insertStore, getFindIfExistStore } from "@/api";
import { ElMessage, ElMessageBox } from "element-plus";
export default {
  data() {
    return {
      radio: "1",
      chosedDate: "",
      value: "",
      addForm: {
        storeId: "",
        storeName: "",
        storeType: "",
        workers: "",
        size: "",
        address: "",
        manger: "",
      },
      addFormRules: {
        storeId: [
          { required: true, message: "请输入门店序号", trigger: "blur" },
        ],
        storeName: [
          { required: true, message: "请输入门店名称", trigger: "blur" },
        ],
        storeType: [
          { required: true, message: "请选择门店类型", trigger: 'change' },
        ],
        workers: [
          { required: true, message: "请输入门店人数", trigger: "blur" },
        ],
        size: [{ required: true, message: "请输入门店面积", trigger: "blur" }],
        address: [
          { required: true, message: "请输入门店地址", trigger: "blur" },
        ],
        manger: [
          { required: true, message: "请输入门店管理员", trigger: "blur" },
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
    };
  },
  methods: {
    cancelMyStore() {
      this.$router.push({
        name: "myStore",
      });
    },
    myStore() {
      getFindIfExistStore(this.addForm.storeId).then((datas) => {
        if (datas.data.length != 0) {
          ElMessage({
            type: "warning",
            message: "门店序号已存在",
          });
        } else {
          if (
            this.addForm.storeId === "" ||
            this.addForm.storeName === "" ||
            this.addForm.storeType === "" ||
            this.addForm.address === "" ||
            this.addForm.size === "" ||
            this.addForm.workers === "" ||
            this.addForm.manger === ""
          ) {
            ElMessage({
              type: "warning",
              message: "请完整填写门店信息",
            });
            return;
          }
          insertStore(
            this.addForm.storeId,
            this.addForm.storeName,
            this.addForm.storeType,
            this.addForm.address,
            this.addForm.size,
            this.addForm.manger,
            this.addForm.workers
          );
          ElMessage({
            type: "success",
            message: "新建门店成功",
          });
          this.$router.push({
            name: "myStore",
          });
        }
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