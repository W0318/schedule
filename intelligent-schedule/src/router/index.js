import Home from '../views/Home.vue'
<<<<<<< HEAD
import User from '../views/Setting/User.vue'
import Main from '../views/Main.vue'
import ShiftSchedule from '../views/ShiftSchedule.vue'
import ShiftManagement from '../views/ScheduleSetting/ShiftManagement.vue'
import MenuManagement from '../views/Setting/MenuManagement.vue'
import MyStore from '../views/StoreManagement/MyStore.vue'
import Employee from '../views/StoreManagement/Employee.vue'
import SchedulingCalendar from '../views/ShiftSchedule/SchedulingCalendar.vue'
import PersonalizationRules from '../views/ShiftSchedule/PersonalizationRules.vue'
=======
import User from '../views/User.vue'
import Main from '../views/Main.vue'
import ShiftSchedule from '../views/ShiftSchedule.vue'
import StoreManagement from '../views/StoreManagement.vue'
import ShiftManagement from '../views/ShiftManagement.vue'
import ShiftSetting from '../views/ShiftSetting.vue'
import Setting from '../views/Setting.vue'
>>>>>>> 89b9224a072fc8a867713dd438a3de36b0247624
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
<<<<<<< HEAD
                path: "/shiftschedule",//排班表
                name: "ShiftSchedule",
                component: ShiftSchedule,
            },
            //门店管理
            {
                path: "/mystore",//零售门店管理
                name: "MyStore",
                component: MyStore,
            },
            {
                path: "/employee",//员工管理
                name: "Employee",
                component: Employee,
            },
            //排班管理
            {
                path: "/personalizationrules",//个性化规则管理
                name: "PersonalizationRules",
                component: PersonalizationRules,
            },
            {
                path: "/schedulingcalendar",//班表日历
                name: "SchedulingCalendar",
                component: SchedulingCalendar,
            },
            //基础排班设置
            {
=======
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
>>>>>>> 89b9224a072fc8a867713dd438a3de36b0247624
                path: "/shiftmanagement",
                name: "ShiftManagement",
                component: ShiftManagement,
            },
<<<<<<< HEAD
            // {
            //     path: "/shiftsetting",
            //     name: "ShiftSetting",
            //     component: ShiftSetting,
            // },
            //系统设置
            {
                path: "/user",//用户管理
                name: "User",
                component: User,
            },
            {
                path: "/menumanagement",
                name: "MenuManagement",
                component: MenuManagement,
=======
            {
                path: "/shiftsetting",
                name: "ShiftSetting",
                component: ShiftSetting,
            },
            {
                path: "/setting",
                name: "Setting",
                component: Setting,
>>>>>>> 89b9224a072fc8a867713dd438a3de36b0247624
            },
            
        ]
    },
    
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;