# 1.0.5 侧边栏展开收起，路由跳转，
# 1.0.6 对侧边栏进行了优化
# 1.0.7 安装了moment库【yarn add moment】,排班表编写
# 1.0.8 安装了vuedraggable@next,排班表编写
# 1.0.9 解决了header栏的bug以及主界面压header的问题,登录界面简单设计
# 1.0.10 卸载了vuedraggable,实现排班表拖动,修改了Main.vue以及排班表页面布局上的问题
# 1.0.11 排班表第n版,把vuedraggable@next下回来了,排班表拖拽新脑洞。更改了代码编写布局，添加了注释
# 1.0.12 排班表按周查看基本完成
# 1.0.13 排班表按日查看差一个日期切换
# 1.0.14 ).登录和登出的实现，
2).实现权限登录（管理员用户账号:admin,密码:admin 员工用户账号:123,密码:123);
不同权限用户登录看到的内容不一样，员工用户仅可以看到首页以及排班表，管理员用户可以看到全部功能;
3).解决侧栏数据持久化问题;
4).未登录前不可通过路由进入内容页面，同理登录后无法通过路由返回登录界面（通过点击退出可以回到登录界面）;
5).安装的依赖 npm install js-cookie --save  ; npm install mockjs ; npm install axios
