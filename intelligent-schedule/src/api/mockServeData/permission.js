import Mock from "mockjs";
export default {
    getMenu: config => {
        const { username, password } = JSON.parse(config.body)
        // 先判断用户是否存在
        // 判断账号和密码是否对应
        if (username === 'admin' && password === 'admin') {
            return {
                code: 20000,
                data: {
                    menu: [
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
                    ],
                    token: Mock.Random.guid(),
                    Message: '获取成功'
                }

            }
        } else if (username === '123' && password === '123') {
            return {
                code: 20000,
                data: {
                    menu: [
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
                            path: '/mySchedule',
                            name: 'MySchedule',
                            lable: "我的排班日历",
                            icon: "Timer",
                            url: 'MySchedule/MySchedule'
                        },
                    ],
                    token: Mock.Random.guid(),
                    Message: '获取成功'
                }
            }
        } else {
            return {
                code: -999,
                data: {
                    message: '密码错误'
                }
            }
        }
    }
}