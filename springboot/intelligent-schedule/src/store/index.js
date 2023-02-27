import { createStore } from 'vuex'
// import tab from './tab'

// 创建一个新的 store 实例

const store = createStore({
    namespaced: true,  //开启命名空间 建议开启
    // 创建一个新的 store 实例
    state: {
        isCollapse: false,//控制菜单的展开还是收起
        tabsList: [
            {
                path: '/',
                name: 'home',
                lable: "首页",
                icon: "HomeFilled",
                url: 'Home/Home'
            },
        ],
        menu: []
    },
    mutations: {
        //菜单收起的方法
        collapseMenu(state) {
            state.isCollapse = !state.isCollapse
        },
        //更新面包屑
        selectMenu(state, val) {
            // console.log(val,'val')
            state.tabsList.splice(0, 1, val)
            console.log(state.tabsList, 'val')
        },
        setMenu(state, val) {
            state.menu = val
            localStorage.setItem('menu', JSON.stringify(val))
        },
        addMenu(state) {
            if (!localStorage.getItem('menu')) {
                return
            }
            const menu = JSON.parse(localStorage.getItem('menu'))
            state.menu = menu
        }
    }
})
export default store