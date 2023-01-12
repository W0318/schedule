<template>
    <el-menu default-active="2" class="el-menu-vertical-demo" :collapse="isCollapse" @open="handleOpen"
        @close="handleClose" text-color="" active-text-color="#ffd04b" background-color="#545c64">
        <h3>智能排班系统</h3>
        <el-menu-item v-on:click="clickMenu(item)" v-for="item in noChildren" :key="item.name" :index="item.name">
            <el-icon>
                <component :is="item.icon"></component>
            </el-icon>
            <template #title>
                <span>{{ item.lable }}</span>
            </template>
        </el-menu-item>
        <el-sub-menu v-for="item in hasChildren" :key="item.name" :index="item.name">
            <template #title>
                <el-icon>
                    <component :is="item.icon"></component>
                </el-icon>
                <span>{{ item.lable }}</span>
            </template>
            <el-menu-item-group v-for="sunitem in item.children" :key="sunitem.name">

                <el-menu-item v-on:click="clickMenu(sunitem)" :index="sunitem.name">
                    <!-- <el-icon>
                        <component :is="sunitem.icon"></component>
                    </el-icon> -->
                    {{ sunitem.lable }}
                </el-menu-item>
            </el-menu-item-group>

        </el-sub-menu>
    </el-menu>
</template>
  
<script setup>
import store from '@/store';
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
// const isCollapse = ref(false)
const menuData = [
    {
        path: '/',
        name: 'home',
        lable: "首页",
        icon: "HomeFilled",
        url: 'Home/Home'
    },
    {
        path: '/shiftschedule',
        name: 'ShiftSchedule',
        lable: "排班表",
        icon: "Calendar",
        url: 'ShiftSchedule/ShiftSchedule'
    },
    {
        path: '/storemanagement',
        name: 'StoreManagement',
        lable: "门店管理",
        icon: "Shop",
        // url: 'StoreManagement/StoreManagement'
        children: [
            {
                path: '/mystore',
                name: 'MyStore',
                lable: "零售门店管理",
                icon: 'setting',
                url: 'StoreManagement/MyStore'
            },
            {
                path: '/employee',
                name: 'Employee',
                lable: "员工管理",
                icon: 'setting',
                url: 'StoreManagement/Employee'
            },
        ]
    },
    {
        path: '/shiftmanagement',
        name: 'ShiftManagement',
        lable: "排班管理",
        icon: "CreditCard",
        // url: 'ShiftManagement/ShiftManagement'
        children: [
            {
                path: '/personalizationrules',
                name: 'PersonalizationRules',
                lable: "零售门店管理",
                icon: 'setting',
                url: 'ShiftManagement/PersonalizationRules'
            },
            {
                path: '/schedulingcalendar',
                name: 'SchedulingCalendar',
                lable: "员工管理",
                icon: 'setting',
                url: 'ShiftManagement/SchedulingCalendar'
            },
        ]

    },
    {
        path: '/schedulesetting',
        name: 'ScheduleSetting',
        lable: "排班基础设置",
        icon: "Operation",
        children: [
            {
                path: '/shiftmanagement',
                name: 'ShiftManagement',
                lable: "日程管理",
                icon: 'setting',
                url: 'ScheduleSetting/ShiftManagement'
            },
        ]
    },
    {
        path: '/setting',
        name: 'Setting',
        lable: "系统设置",
        icon: "Setting",
        // url: 'Setting/Setting'
        children: [
            {

                path: '/user',
                name: 'User',
                lable: "角色管理",
                icon: 'setting',
                url: 'Setting/User'
            },
            {

                path: '/menumanagement',
                name: 'MenuManagement',
                lable: "菜单管理",
                icon: 'setting',
                url: 'Setting/MenuManagement'
            },
        ]
    },
]

const handleOpen = (key, keyPath) => {
    console.log(key, keyPath)
}
const handleClose = (key, keyPath) => {
    console.log(key, keyPath)
}
const router = useRouter();
const clickMenu=(item)=>{
    console.log(item)
    router.push({path:`${item.path}`})
}

//没有子菜单
const noChildren = computed(() => {
    return menuData.filter(item => !item.children)
})


// //有子菜单
const hasChildren = computed(() => {
    return menuData.filter(item => item.children)
})
const isCollapse = computed(() => {
   return store.state.isCollapse
})

</script>
  
<style lang="less" scoped >
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 201px;
    min-height: 400px;
}
.el-menu{
    height: 100vh;
    h3 {
        color: #fff;
        text-align: center;
        line-height: 48px;
        font-size: 16px;
        font-weight: 400;
    }
}
</style>

<!-- <script >
import {
    Document,
    Menu as IconMenu,
    Location,
    Setting,
} from '@element-plus/icons-vue'
import { computed } from 'vue';
export default {
    data() {
        return {
            isCollapse: false,
            menuData: [
                {
                    path: '/',
                    name: 'home',
                    lable: "首页",
                    icon: 's-home',
                    url: 'Home/Home'
                },
                {
                    path: '/',
                    name: 'home',
                    lable: "其他",
                    icon: 'location',
                    children: [
                        {

                            path: '/page1',
                            name: 'page1',
                            lable: "页面1",
                            icon: 'setting',
                            url: 'Other/PageOne'
                        },
                        {

                            path: '/page2',
                            name: 'page2',
                            lable: "页面2",
                            icon: 'setting',
                            url: 'Other/PageTwo'
                        },
                    ]

                },
            ]
        };
    },
    methods: {
        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        }
    },
    computed: {
        //没有子菜单
        noChildren() {
            return this.menuData.filter(item => !item.children)
        },
        //有子菜单
        hasChildren() {
            return this.menuData.filter(item => item.children)
        }
    }
}
</script> -->