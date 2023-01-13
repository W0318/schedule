<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px">
        <common-aside />
      </el-aside>
      <el-container>
        <el-header>
          <common-header />
        </el-header>
        <el-main>
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
<style scoped>
.el-header {
  padding: 0;
  height: auto;
}

.el-main {
  background-color: #e3e3e3;
  border: 1px solid black;
}
</style>