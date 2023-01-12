<template>
    <div class="header-container">
        <div class="l-content">
            <el-button v-on:click="handleMenu" :icon="Menu" />
            <!-- 面包屑 -->
            <span class="text">首页</span>
        </div>
        <div class="r-content">
            <el-dropdown>
                <span class="el-dropdown-link">
                    <img class="user" src="../assets/logo.png" alt="">
                </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item>个人中心</el-dropdown-item>
                        <el-dropdown-item>退出</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>

        </div>
    </div>
</template>
<script setup>
import { Menu } from '@element-plus/icons-vue';
import store from '@/store';
const handleMenu=()=>{
    store.commit('collapseMenu')
}

</script>
<style lang="less" scoped>
.header-container {
    padding: 20px;
    background-color: #333;
    height: 60px;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .text {
        color: #fff;
        font-size: 14px;
        margin-left: 10px;
    }
    .r-content{
        .user{
            width: 40px;
            height: 40px;
            border-radius: 50%;
        }
    }

}
</style>