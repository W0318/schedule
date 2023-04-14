import Home from '../views/Home.vue'
import User from '../views/Setting/User.vue'
import Main from '../views/Main.vue'
import Personal from '../views/Personal.vue'
import ShiftSchedule from '../views/ShiftSchedule.vue'
import ShiftManagement from '../views/ScheduleSetting/ShiftManagement.vue'
import MenuManagement from '../views/Setting/MenuManagement.vue'
import MyStore from '../views/StoreManagement/MyStore.vue'
import Employee from '../views/StoreManagement/Employee.vue'
import SchedulingCalendar from '../views/ShiftSchedule/SchedulingCalendar.vue'
import PersonalizationRules from '../views/ShiftSchedule/PersonalizationRules.vue'
import MySchedule from '../views/MySchedule.vue'
import Person from '../views/Person.vue'

import Login from '../views/Login.vue'
// //1.创建路由组件
// //2. 将路由与组件进行映射
// //3. 创建router实例

import { createWebHistory, createRouter } from "vue-router";

const routes = [
    {
        path: "/",
        name: "Main",
        component: Main,
        redirect: '/home',
        children: [
            {
                path: "/home",//首页
                name: "Home",
                component: Home,
            },
            {
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
            // 基础排班设置
            {
                path: "/user",
                name: "User",
                component: User,
                path: "/Person",
                name: "Person",
                component: Person,
            },
            {
                path: "/shiftmanagement",
                name: "ShiftManagement",
                component: ShiftManagement,
            },
            {
                path: "/user",//用户管理
                name: "User",
                component: User,
            },
            {
                path: "/menumanagement",
                name: "MenuManagement",
                component: MenuManagement,
            },
            {
                path: "/mySchedule",
                name: "MySchedule",
                component: MySchedule,
            },
            {
                path: "/personalizationrules/addRules",
                name: "addRules",
                component: () => import('../views/ShiftSchedule/AddRules'),
            },
            {
                path: "/addStore/myStore",
                name: "myStore",
                component: () => import('../views/StoreManagement/MyStore'),
            },
            {
                path: "/myStore/addStore",
                name: "addStore",
                component: () => import('../views/StoreManagement/AddStore'),
            },
            {
                path: "/myStore/addEmployee",
                name: "addEmployee",
                component: () => import('../views/StoreManagement/AddEmployee'),
            },
            {
                path: "/myStore/employee",
                name: "employee",
                component: () => import('../views/StoreManagement/Employee'),
            },
            {
                path: "/commonHeader/personal",
                name: "personal",
                component: () => import('../views/Personal'),
            },
            {
                path: "/scheduleSetting/addShiftManagement",
                name: "addShiftManagement",
                component: () => import('../views/ScheduleSetting/AddShiftManagement'),
            },
            {
                path: "/scheduleSetting/shiftManagement",
                name: "shiftManagement",
                component: () => import('../views/ScheduleSetting/ShiftManagement'),
            }
        ]
    },
    {
        path: "/login",
        name: "Login",
        component: Login,
    }

];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;