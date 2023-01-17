import Mock  from "mockjs";
import  permission from './mockServeData/permission'
// import 

Mock.mock('/api/home/getData',function(){
    console.log('拦截到请求')
})
Mock.mock(/permission\/getMenu/,'post',permission.getMenu)
// Mock.mock('/api/permission/getMenu','post',permission.getMenu)