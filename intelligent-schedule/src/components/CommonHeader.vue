<template>
  <div class="header-container">
    <div class="l-content">
      <el-button v-on:click="handleMenu" type="info">
        <el-icon color="#333">
          <Menu />
        </el-icon>
      </el-button>

      <!-- 面包屑 -->
      <!-- <span class="text">{{首页}}</span> -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item
          v-for="item in { ...store.state.tabsList }"
          :key="item.path"
          :to="{ path: item.path }"
          >{{ item.lable }}</el-breadcrumb-item
        >
      </el-breadcrumb>
    </div>
    <div class="r-content">
      <el-dropdown>
        <span class="el-dropdown-link">
          <img class="user" src="../assets/head1.png" alt />
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="personal">个人中心</el-dropdown-item>
            <el-dropdown-item @click="logout">退出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>
<script setup>
import { Menu } from "@element-plus/icons-vue";
import store from "@/store";
import Cookie from "js-cookie";
import { useRouter } from "vue-router";
// const headerName = {...store.state.tabsList}
const handleMenu = () => {
  store.commit("collapseMenu");
  // console.log(headerName,'headerName')
};

const router = useRouter();
const logout = () => {
  Cookie.remove("token");
  //跳转回登录页面
  router.push({ path: "login" });
};
const personal = () => {
  router.push({ name: "personal" });
};
</script>
<style lang="less" scoped>
.header-container {
  padding: 14px;
  background-color: #fff;

  display: flex;
  justify-content: space-between;
  align-items: center;

  .text {
    color: #fff;
    font-size: 14px;
    margin-left: 10px;
  }

  .r-content {
    .user {
      width: 40px;
      height: 40px;
      border-radius: 50%;
      // border: 1px solid #000000;
    }
  }

  .l-content {
    display: flex;
    align-items: center;

    /deep/.el-breadcrumb__item {
      .el-breadcrumb__inner {
        font-weight: bold;
        margin-left: 10px;
        font-size: 16px;

        &.is-link {
          color: rgb(58, 58, 58);
        }
      }
    }
  }
}
</style>