import { createStore } from 'vuex'
// import tab from './tab'

// 创建一个新的 store 实例

const store = createStore({
  namespaced: true,  //开启命名空间 建议开启
    // 创建一个新的 store 实例
    state: {
        isCollapse: false//控制菜单的展开还是收起
    },
    mutations: {
        //菜单收起的方法
        collapseMenu (state) {
            state.isCollapse = !state.isCollapse
        }
    }
})
export default store