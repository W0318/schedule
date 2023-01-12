import{ createApp } from 'vue'
import App from './App.vue'
// import LogIn from './components/LogIn.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router'
// 统一导入 element 图标
import * as icons from '@element-plus/icons-vue'
import store from './store'
 
const app=createApp(App)
// 注册全局 element-icons 组件
Object.keys(icons).forEach(key => {
    app.component(key, icons[key])
})
app.use(ElementPlus)
    .use(router)
    .use(store)
    .mount('#app')