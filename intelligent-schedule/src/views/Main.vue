<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="auto">
        <common-aside />
      </el-aside>
      <el-container>
        <el-header>
          <common-header />
        </el-header>
        <el-main id="ground">

          <!-- 路由出口 -->
          <!-- 路由匹配到的组件渲染到这里 -->
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import CommonAside from '../components/CommonAside.vue'
import CommonHeader from '../components/CommonHeader.vue'
export default {
  data() {
    return {}
  },
  components: {
    CommonAside,
    CommonHeader
  }
}
</script>
<style lang="less" scoped>
.el-header {
  padding: 0;
  height: max-content;
}

.el-main {
  background-color: #e3e3e3;
  padding: 5px 10px;
}

#ground {
  background: url("../assets/222.jpg");
  width: 100%;
  height: 100%;
  // position: static;
  background-size: 100% 100%;
  // display: flex;
  // filter: brightness(90%);
}
</style>