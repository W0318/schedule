import { createApp } from 'vue'
import App from './App.vue'
// import LogIn from './components/LogIn.vue'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import 'element-plus/dist/index.css'
import router from './router'
// 统一导入 element 图标
import * as icons from '@element-plus/icons-vue'
import store from './store'
import Cookie from 'js-cookie'
// import api from './api'
import './api/mock.js'

const app = createApp(App)
// 注册全局 element-icons 组件
Object.keys(icons).forEach(key => {
    app.component(key, icons[key])
})
//添加全局前置守为
router.beforeEach((to, from, next) => {
    // token不存在，说明用户未存在，应该调到登录界面
    const token = Cookie.get('token')
    if (!token && to.name !== 'Login') {
        next({ name: 'Login' })
    } else if (token && to.name === 'Login') {
        next({ name: 'Home' })
    } else {
        next()
    }
})
// app.config.globalProperties.$api = api
app.use(ElementPlus, {
    locale: zhCn,
})
    .use(router)
    .use(store)
    .mount('#app')