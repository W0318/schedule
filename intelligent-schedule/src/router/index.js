import Home from '../views/Home.vue'
import User from '../views/User.vue'
import Main from '../views/Main.vue'
import ShiftSchedule from '../views/ShiftSchedule.vue'
import StoreManagement from '../views/StoreManagement.vue'
import ShiftManagement from '../views/ShiftManagement.vue'
import ShiftSetting from '../views/ShiftSetting.vue'
import Setting from '../views/Setting.vue'
// // Vue.use(VueRouter)
// //1.创建路由组件
// //2. 将路由与组件进行映射
// //3. 创建router实例

import { useRouter ,createWebHistory, createRouter } from "vue-router";

const routes = [
    {
        path: "/",
        name: "Main",
        component: Main,
        redirect:'/home',
        children:[
            {
                path: "/home",//首页
                name: "Home",
                component: Home,
            },
            {
                path: "/user",
                name: "User",
                component: User,
            },
            {
                path: "/shiftschedule",
                name: "ShiftSchedule",
                component: ShiftSchedule,
            },
            {
                path: "/storemanagement",
                name: "StoreManagement",
                component: StoreManagement,
            },
            {
                path: "/shiftmanagement",
                name: "ShiftManagement",
                component: ShiftManagement,
            },
            {
                path: "/shiftsetting",
                name: "ShiftSetting",
                component: ShiftSetting,
            },
            {
                path: "/setting",
                name: "Setting",
                component: Setting,
            },
            
        ]
    },
    
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;